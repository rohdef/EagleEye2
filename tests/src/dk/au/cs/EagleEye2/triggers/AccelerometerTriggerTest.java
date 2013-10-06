package dk.au.cs.EagleEye2.triggers;

import android.hardware.SensorEvent;
import android.test.AndroidTestCase;

public class AccelerometerTriggerTest extends AndroidTestCase {
  private AccelerometerTrigger accelerometerTrigger;
  private TestTriggerListener accelerometerTriggerListener;
  private float gravity = 9.81f;

  public AccelerometerTriggerTest() {

  }

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    accelerometerTriggerListener = new TestTriggerListener();
    accelerometerTrigger = new AccelerometerTrigger(0.1f, 2, 50, this.getContext());
    accelerometerTrigger.addListener(accelerometerTriggerListener);
  }

  /*
   * Should activate
   */
  public void testShouldActivateOnMovementOnXAxis() {
    float[] accelerometerValues = {0.1f + gravity, 0, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(true, accelerometerTrigger.isRunning());
  }

  public void testShouldActivateOnMovementOnYAxis() {
    float[] accelerometerValues = {0, 0.1f + gravity, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(true, accelerometerTrigger.isRunning());
  }

  public void testShouldActivateOnMovementOnZAxis() {
    float[] accelerometerValues = {0, 0, 0.1f + gravity};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(true, accelerometerTrigger.isRunning());
  }

  /*
   * Should not activate
   */

  public void testShouldNotActivateOnSmallMovementOnXAxis() {
    float[] accelerometerValues = {0.09f, 0, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(false, accelerometerTrigger.isRunning());
  }

  public void testShouldNotActivateOnSmallMovementOnYAxis() {
    float[] accelerometerValues = {0, 0.09f, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(false, accelerometerTrigger.isRunning());
  }

  public void testShouldNotActivateOnSmallMovementOnZAxis() {
    float[] accelerometerValues = {0, 0, 0.09f};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(false, accelerometerTrigger.isRunning());
  }

  /*
   * No movement
   */

  public void testShouldNotActivateOnNoMovement() {
    float[] accelerometerValues = {0, 0, 0};
    accelerometerTrigger.onSensorChanged(null, 0, 0, accelerometerValues);

    assertEquals(false, accelerometerTrigger.isRunning());
  }
}
