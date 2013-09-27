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

    distanceTrigger = new DistanceTrigger(50);
    distanceTriggerListener = new TestTriggerListener();
  }

  public void testShouldActivateOn50MetersMovement() {
    distanceTrigger.locationUpdated(50, null, null);
    assertEquals(1, distanceTriggerListener.getCallCount());
  }

  public void testShouldActivateForMoreThan50MetersMovement() {

  }

  public void testShouldActivateExactlyFourTimesAfter249MetersMovementOrMore() {
    // Simulate with loop
  }

  public void testShouldNotActivateOnLessThan50MetersMovement() {
    // Test for 49 meters + 1-2 random values below 50.
    // If distance can return negative values, we should
    // test for either errors or make sure it turns it
    // into absolute values.
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
