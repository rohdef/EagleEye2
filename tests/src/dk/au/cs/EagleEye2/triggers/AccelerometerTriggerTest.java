package dk.au.cs.EagleEye2.triggers;

import android.hardware.SensorEvent;
import android.test.AndroidTestCase;

public class AccelerometerTriggerTest extends AndroidTestCase {
  private AccelerometerTrigger accelerometerTrigger;
  private TestTriggerListener accelerometerTriggerListener;

  public AccelerometerTriggerTest() {

  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    accelerometerTriggerListener = new TestTriggerListener();
    accelerometerTrigger = new AccelerometerTrigger();
    accelerometerTrigger.addListener(accelerometerTriggerListener);
  }

  public void testShouldActivateOnMovement() {
    // Rather abstract, does not specify is a
    // threshold is needed and if so what it
    // would be. If threshold is needed it should
    // probably result in multiple tests.


    assertEquals(1, accelerometerTriggerListener.getCallCount());
  }

  public void testShouldNotActivateOnNoMovement() {
    // send movement 0
    assertEquals(0, accelerometerTriggerListener.getCallCount());
  }

}
