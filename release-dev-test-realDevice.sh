#!/bin/bash

adb uninstall com.example.team7_app
./gradlew  clean assemble
adb install ./app/build/outputs/apk/release/app-release.apk