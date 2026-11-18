# VoluNext (experimental Android prototype)

Maps volume-key long presses to media navigation and short presses to system volume where Android/OEM event delivery permits.

## Build an APK using a phone (free GitHub Actions route)
1. Create a free GitHub account (if you do not already have one).
2. In your browser, create a **public** repository named `VoluNext`.
3. Upload the contents of this folder to the repository root (upload the files inside `VoluNext`, not the outer folder). Make sure `.github/workflows/build-apk.yml` is included.
4. Open the repository's **Actions** tab and select **Build VoluNext APK**. Tap **Run workflow** if it has not started automatically.
5. When the run finishes, open it and download the `VoluNext-debug-apk` artifact. Extract it on your phone to get `app-debug.apk`.
6. Install the APK, open VoluNext, and enable its Accessibility service when prompted.

The workflow builds a debug APK; it does not publish to Play Store.

## Important limitations
This is an experimental prototype, not a verified working app on every device. Android/OxygenOS may reserve volume keys for system volume and may not deliver key events to an AccessibilityService. Screen-off delivery is especially device-dependent. Spotify media commands use Android media-session controls; behaviour depends on Spotify being active and Android allowing the command.
