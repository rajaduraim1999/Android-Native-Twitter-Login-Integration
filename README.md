

# Android Native Google Login Integration
This is a sample Android project that demonstrates how to integrate Google sign-in into an Android app using the Google Sign-In API. The app includes examples of using both the default Google Sign-In button and a custom button for sign-in.

## Features
* Sign in with Google account
* Using the default Google Sign-In button
* Using a custom sign-in button

## Requirements
* Android Studio 4.1 or newer
* Android SDK with API level 21 or newer

## Setup
1. Clone or download the repository
2. Open the project in **Android Studio**
3. Create a new project in the **[Google Developer Console]**
4. In the project dashboard, go to **APIs & Services** > **Credentials**
5. Click create **Credentials** > **OAuth client ID**
6. Select **Android** as the application type and fill out the required information
7. Add your app's package name and **SHA-1** signing certificate fingerprint to the Authorized Android apps section
8. Save your changes and copy the **Client ID**
9. Open the 'app/src/main/res/values/strings.xml' file and replace the 'google_signin_client_id' value with your copied **Client ID**
10. Run the app on an emulator or physical device

## Usage
### Using the Default Google Sign-In Button
1. Open the app
2. Click the Sign in with Google button
3. Select a Google account to sign in with
4. Grant the required permissions
5. The app will show the signed-in user's email address and profile picture
### Using a Custom Sign-In Button
1. Open the app
2. Enter your email address and password in the fields
3. Click the Sign In button
4. The app will show the signed-in user's email address and profile picture

## License
This project is licensed under the MIT License - see the LICENSE file for details.

## Notes
* This app is implemented in both Java and Kotlin for convenience.
* This project uses the latest version of the Google Play Services library.
* The google-services.json file contains your API keys and other configuration details, so be sure to keep it private and secure.

## Credits
This app was created by **[rajaduraim1999]**. If you have any questions or suggestions, please feel free to contact me.

## Acknowledgements
This project uses the following third-party libraries:

* Google Sign-In API




