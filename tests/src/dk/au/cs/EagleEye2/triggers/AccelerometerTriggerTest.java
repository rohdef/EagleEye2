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
    accelerometerTrigger = new AccelerometerTrigger(0.1f);
    accelerometerTrigger.addListener(accelerometerTriggerListener);
  }

  /*
   * Should activate
   */

  public void testShouldActivateOnMovementOnXAxis() {
    float[] accelerometerValues = {0.1f, 0, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(1, accelerometerTriggerListener.getCallCount());
    //assertNotNull(accelerometerTriggerListener.getLastGeoPosition());
  }

  public void testShouldActivateOnMovementOnYAxis() {
    float[] accelerometerValues = {0, 0.1f, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(1, accelerometerTriggerListener.getCallCount());
    //assertNotNull(accelerometerTriggerListener.getLastGeoPosition());
  }

  public void testShouldActivateOnMovementOnZAxis() {
    float[] accelerometerValues = {0, 0, 0.1f};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(1, accelerometerTriggerListener.getCallCount());
    //assertNotNull(accelerometerTriggerListener.getLastGeoPosition());
  }

  /*
   * Should not activate
   */

  public void testShouldNotActivateOnSmallMovementOnXAxis() {
    float[] accelerometerValues = {0.09f, 0, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(0, accelerometerTriggerListener.getCallCount());
    assertNull(accelerometerTriggerListener.getLastGeoPosition());
  }

  public void testShouldNotActivateOnSmallMovementOnYAxis() {
    float[] accelerometerValues = {0, 0.09f, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(0, accelerometerTriggerListener.getCallCount());
    assertNull(accelerometerTriggerListener.getLastGeoPosition());
  }

  public void testShouldNotActivateOnSmallMovementOnZAxis() {
    float[] accelerometerValues = {0, 0, 0.09f};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(0, accelerometerTriggerListener.getCallCount());
    assertNull(accelerometerTriggerListener.getLastGeoPosition());
  }

  /*
   * No movement
   */

  public void testShouldNotActivateOnNoMovement() {
    float[] accelerometerValues = {0, 0, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(0, accelerometerTriggerListener.getCallCount());
    assertNull(accelerometerTriggerListener.getLastGeoPosition());
  }
}
