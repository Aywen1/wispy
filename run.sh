
clear

# build the game and rename the executable
./gradlew desktop:dist

if [ $? -ne 0 ]; then
  exit 1
fi

mv desktop/build/libs/desktop-1.0.jar desktop/build/libs/SuperWispy.jar

# backup the save file
cp out-linux/SaveData/save.json ./

# delete previous build
rm -rf out-linux

# build the linux release
java -jar build_rescources/packr-all-4.0.0.jar build_rescources/linux.json

# apply my old save
mkdir out-linux/SaveData
mv ./save.json out-linux/SaveData/save.json

# launch the game
steam-run ./out-linux/GameLauncher
