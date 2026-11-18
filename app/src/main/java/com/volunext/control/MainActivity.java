package com.volunext.control;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.view.Gravity;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends Activity {
    @Override public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        LinearLayout root = new LinearLayout(this);
        root.setOrientation(LinearLayout.VERTICAL);
        root.setPadding(40, 48, 40, 40);
        root.setGravity(Gravity.CENTER_HORIZONTAL);

        TextView title = new TextView(this);
        title.setText("VoluNext"); title.setTextSize(30); title.setGravity(Gravity.CENTER);
        root.addView(title, new LinearLayout.LayoutParams(-1, -2));

        TextView info = new TextView(this);
        info.setText("Volume + short press: volume up\nVolume - short press: volume down\nVolume + long press: next track\nVolume - long press: previous track\n\nEnable the accessibility service below. Then test with Spotify playing.");
        info.setTextSize(17); info.setPadding(0, 32, 0, 32);
        root.addView(info, new LinearLayout.LayoutParams(-1, -2));

        Button settings = new Button(this);
        settings.setText("Open Accessibility Settings");
        settings.setOnClickListener(v -> startActivity(new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS)));
        root.addView(settings, new LinearLayout.LayoutParams(-1, -2));

        TextView warning = new TextView(this);
        warning.setText("Note: Android/OnePlus may block volume-key interception by accessibility services, especially with the screen off. This prototype needs testing on your device.");
        warning.setTextSize(14); warning.setPadding(0, 28, 0, 0);
        root.addView(warning, new LinearLayout.LayoutParams(-1, -2));
        setContentView(root);
    }
}
