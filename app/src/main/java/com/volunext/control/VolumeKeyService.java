package com.volunext.control;

import android.accessibilityservice.AccessibilityService;
import android.media.AudioManager;
import android.media.session.MediaSession;
import android.os.Handler;
import android.os.Looper;
import android.view.KeyEvent;
import android.view.accessibility.AccessibilityEvent;

public class VolumeKeyService extends AccessibilityService {
    private static final long LONG_PRESS_MS = 450;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private int pendingKey = KeyEvent.KEYCODE_UNKNOWN;
    private boolean longTriggered = false;
    private Runnable longPressTask;
    private AudioManager audio;

    @Override public void onServiceConnected() {
        super.onServiceConnected();
        audio = (AudioManager) getSystemService(AUDIO_SERVICE);
    }

    @Override public boolean onKeyEvent(KeyEvent event) {
        int code = event.getKeyCode();
        if (code != KeyEvent.KEYCODE_VOLUME_UP && code != KeyEvent.KEYCODE_VOLUME_DOWN) return super.onKeyEvent(event);

        if (event.getAction() == KeyEvent.ACTION_DOWN) {
            if (event.getRepeatCount() > 0) return true;
            pendingKey = code;
            longTriggered = false;
            if (longPressTask != null) handler.removeCallbacks(longPressTask);
            longPressTask = () -> {
                if (pendingKey == code) {
                    longTriggered = true;
                    dispatchMediaKey(code == KeyEvent.KEYCODE_VOLUME_UP
                            ? KeyEvent.KEYCODE_MEDIA_NEXT : KeyEvent.KEYCODE_MEDIA_PREVIOUS);
                }
            };
            handler.postDelayed(longPressTask, LONG_PRESS_MS);
            return true;
        }

        if (event.getAction() == KeyEvent.ACTION_UP && pendingKey == code) {
            if (longPressTask != null) handler.removeCallbacks(longPressTask);
            if (!longTriggered) adjustVolume(code == KeyEvent.KEYCODE_VOLUME_UP);
            pendingKey = KeyEvent.KEYCODE_UNKNOWN;
            return true;
        }
        return true;
    }

    private void adjustVolume(boolean up) {
        if (audio == null) audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audio != null) audio.adjustStreamVolume(AudioManager.STREAM_MUSIC,
                up ? AudioManager.ADJUST_RAISE : AudioManager.ADJUST_LOWER, AudioManager.FLAG_SHOW_UI);
    }

    private void dispatchMediaKey(int keyCode) {
        if (audio == null) audio = (AudioManager) getSystemService(AUDIO_SERVICE);
        if (audio == null) return;
        long now = System.currentTimeMillis();
        audio.dispatchMediaKeyEvent(new KeyEvent(now, now, KeyEvent.ACTION_DOWN, keyCode, 0));
        audio.dispatchMediaKeyEvent(new KeyEvent(now, now, KeyEvent.ACTION_UP, keyCode, 0));
    }

    @Override public void onAccessibilityEvent(AccessibilityEvent event) { }
    @Override public void onInterrupt() { }
    @Override public void onDestroy() {
        if (longPressTask != null) handler.removeCallbacks(longPressTask);
        super.onDestroy();
    }
}
