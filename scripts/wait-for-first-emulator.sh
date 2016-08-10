WAIT_CMD="$ANDROID_SDK/platform-tools/adb -s emulator-5554 wait-for-device shell getprop init.svc.bootanim"

until $WAIT_CMD | grep -m 1 stopped; do
  echo "Waiting for first emulator..."
  sleep 1
done
