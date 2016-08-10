package com.szagurskii.travismultipleemulators;

import android.annotation.SuppressLint;
import android.app.Application;
import android.app.Instrumentation;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.PowerManager;
import android.support.annotation.NonNull;
import android.support.test.runner.AndroidJUnitRunner;

import static android.content.Context.KEYGUARD_SERVICE;
import static android.content.Context.POWER_SERVICE;
import static android.os.PowerManager.ACQUIRE_CAUSES_WAKEUP;
import static android.os.PowerManager.FULL_WAKE_LOCK;
import static android.os.PowerManager.ON_AFTER_RELEASE;

public final class CustomTestRunner extends AndroidJUnitRunner {
  @Override
  public Application newApplication(@NonNull ClassLoader cl, @NonNull String className, @NonNull Context context)
      throws InstantiationException, IllegalAccessException, ClassNotFoundException {
    return Instrumentation.newApplication(Application.class, context);
  }

  private PowerManager.WakeLock wakeLock;

  @SuppressWarnings("deprecation") @SuppressLint("MissingPermission")
  @Override public void callApplicationOnCreate(Application app) {
    // Unlock the screen
    KeyguardManager keyguard = (KeyguardManager) app.getSystemService(KEYGUARD_SERVICE);
    keyguard.newKeyguardLock(getClass().getSimpleName()).disableKeyguard();

    // Start a wake lock
    PowerManager power = (PowerManager) app.getSystemService(POWER_SERVICE);
    wakeLock = power.newWakeLock(FULL_WAKE_LOCK | ACQUIRE_CAUSES_WAKEUP | ON_AFTER_RELEASE, getClass().getSimpleName());
    wakeLock.acquire();

    super.callApplicationOnCreate(app);
  }

  @Override public void onDestroy() {
    super.onDestroy();

    wakeLock.release();
  }
}
