./gradlew desktop:dist

if [ $? -ne 0 ]; then
  exit 1
fi

mv desktop/build/libs/desktop-1.0.jar desktop/build/libs/SuperWispy.jar

rm -rf production_build
mkdir production_build

rm -rf out-linux
rm -rf out-win
rm -rf out-mac

java -jar build_rescources/packr-all-4.0.0.jar build_rescources/linux.json
java -jar build_rescources/packr-all-4.0.0.jar build_rescources/windows.json
java -jar build_rescources/packr-all-4.0.0.jar build_rescources/macos.json

zip -r production_build/linux.zip out-linux
zip -r production_build/windows.zip out-win
zip -r production_build/macos.zip out-mac
