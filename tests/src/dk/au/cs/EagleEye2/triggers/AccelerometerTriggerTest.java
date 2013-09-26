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

  public void testShouldActivateOnMovementOnXAxis() {
    // Rather abstract, does not specify is a
    // threshold is needed and if so what it
    // would be. If threshold is needed it should
    // probably result in multiple tests.
    float[] accelerometerValues = {0.1f, 0, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(1, accelerometerTriggerListener.getCallCount());
    assertNotNull(accelerometerTriggerListener.getLastGeoPosition());
  }

  public void testShouldActivateOnMovementOnYAxis() {
    float[] accelerometerValues = {0, 0.1f, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(1, accelerometerTriggerListener.getCallCount());
    assertNotNull(accelerometerTriggerListener.getLastGeoPosition());
  }

  public void testShouldActivateOnMovementOnZAxis() {
    float[] accelerometerValues = {0, 0, 0.1f};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(1, accelerometerTriggerListener.getCallCount());
    assertNotNull(accelerometerTriggerListener.getLastGeoPosition());
  }

  public void testShouldNotActivateOnNoMovement() {
    float[] accelerometerValues = {0, 0, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(0, accelerometerTriggerListener.getCallCount());
  }
}
