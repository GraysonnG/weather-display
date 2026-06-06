# Blanks Weather Display mono-repo
This is a mono repo dedicated to my Inky Impression 13.3 in. weather display. It features a notification ping that lets me know on my desktop when its online. As well as being able to show shared notes between people who are running the companion android app Violet Notes.

## Run/Use `companion`
- Download the latest Android Studio and open the `companion` directory in it, follow steps to run Android apps locally.
- Set the environment variable to the required key in order to send notes to the server.
- Build the app and run it on device.
  - there are multiple ways to achieve this so I leave it up to you.
- Add/Update/Remove notes within the UI. 
  - These notes will be displayed when the pi grabs the weather data.

## To Deploy `pi` **[Under Construction]**
- Ensure that your pi is online and ready for ssh.
- move the scripts into the raspberry pi.
  - `scp -r ./pi/scripts user@remote_host:/user/home/scripts`

## To Deploy `pi-listener`
- Download the latest IntelliJ Idea and open the `pi-listener` directory in it
- Build the app. `./gradlew buildFatJar`
- Test it with `java -jar ./build/libs/pi-listener.jar`
- Move it to your local bin `mv ./build/libs/pi-listener.jar ~/bin/pi-listener.jar`
- Create a systemd user service.
- Reboot.

## To Deploy/Develop web
- Install required node modules: `npm i --prefix ./web`
- Move into the web directory `cd web`
- Run dev with netlify: `netlify dev`
  - You may need to install the netlify-cli tool globally.
  - This is required for notes to work during development/
- This app is deployed to netlify whenever a change is made to remote main.
