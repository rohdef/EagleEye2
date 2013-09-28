package dk.au.cs.EagleEye2.triggers;

import android.test.AndroidTestCase;

/**
 * Makes sure the distance trigger only reacts on a
 * threshold of 50 meters or more as specified.
 */
public class DistanceTriggerTest extends AndroidTestCase {
  private DistanceTrigger distanceTrigger;
  private TestTriggerListener distanceTriggerListener;

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    distanceTriggerListener = new TestTriggerListener();
    distanceTrigger = new DistanceTrigger(50);
    distanceTrigger.addListener(distanceTriggerListener);
  }

  public void testShouldActivateOn50MetersMovement() {
    distanceTrigger.locationUpdated(50, null, null);
    assertEquals(1, distanceTriggerListener.getCallCount());
  }

  public void testShouldActivateForMoreThan50MetersMovement() {
    distanceTrigger.locationUpdated(60, null, null);
    assertEquals(1, distanceTriggerListener.getCallCount());
  }

  public void testShouldActivateExactlyFourTimesAfter249MetersMovementOrMore() {
    // 30 + 20 + 10 + 55 + 20 + 5 + 20 + 39 + 30 + 20 = 249
    distanceTrigger.locationUpdated(30, null, null);
    distanceTrigger.locationUpdated(20, null, null);
    distanceTrigger.locationUpdated(10, null, null);
    distanceTrigger.locationUpdated(55, null, null);
    distanceTrigger.locationUpdated(20, null, null);
    distanceTrigger.locationUpdated(5, null, null);
    distanceTrigger.locationUpdated(20, null, null);
    distanceTrigger.locationUpdated(39, null, null);
    distanceTrigger.locationUpdated(30, null, null);
    distanceTrigger.locationUpdated(20, null, null);
    assertEquals(4, distanceTriggerListener.getCallCount());
  }

  public void testShouldNotActivateOnLessThan50MetersMovement() {
    // Test for 49 meters + 1-2 random values below 50.
    // If distance can return negative values, we should
    // test for either errors or make sure it turns it
    // into absolute values.

    distanceTrigger.locationUpdated(49, null, null);
    assertEquals(0, distanceTriggerListener.getCallCount());
  }

  public void testShouldNotActivateOnLessThan50MetersMovement2() {
    distanceTrigger.locationUpdated(23, null, null);
    assertEquals(0, distanceTriggerListener.getCallCount());
  }

  public void testShouldNotActivateOnLessThan50MetersMovement3() {
    distanceTrigger.locationUpdated(1, null, null);
    assertEquals(0, distanceTriggerListener.getCallCount());
  }

  // Test configurability
  public void testShouldActivateAfter25MetersWhenConfiguredFor25Meters() {

  }

  public void testShouldNotActivateAfterLessThan25MetersWhenConfiguredFor25Meters() {

  }

  public void testShouldActivateAfter75MetersWhenConfiguredFor75Meters() {

  }

  public void testShouldNotActivateAfterLessThan75MetersWhenConfiguredFor75Meters() {

  }
}
