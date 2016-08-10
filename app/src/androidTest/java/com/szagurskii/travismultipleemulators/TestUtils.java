package com.szagurskii.travismultipleemulators;

import android.app.Application;
import android.support.annotation.NonNull;
import android.support.test.InstrumentationRegistry;

public class TestUtils {
  private TestUtils() {
  }

  @NonNull public static Application app() {
    return (Application) InstrumentationRegistry.getTargetContext().getApplicationContext();
  }
}
