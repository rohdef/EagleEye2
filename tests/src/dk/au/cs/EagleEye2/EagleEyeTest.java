package dk.au.cs.EagleEye2;

import android.test.ActivityInstrumentationTestCase2;

/**
 * This is a simple framework for a test of an Application.  See
 * {@link android.test.ApplicationTestCase ApplicationTestCase} for more information on
 * how to write and extend Application tests.
 * <p/>
 * To run this test, you can type:
 * adb shell am instrument -w \
 * -e class dk.au.cs.EagleEye2.EagleEyeTest \
 * dk.au.cs.EagleEye2.tests/android.test.InstrumentationTestRunner
 */
public class EagleEyeTest extends ActivityInstrumentationTestCase2<EagleEye> {
  public EagleEyeTest() {
    super("dk.au.cs.EagleEye2", EagleEye.class);
  }
}
