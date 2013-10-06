package dk.au.cs.EagleEye2.triggers;

import android.location.Location;
import android.test.AndroidTestCase;

/**
 * Makes sure the distance trigger only reacts on a
 * threshold of 50 meters or more as specified.
 */
public class DistanceTriggerTest extends AndroidTestCase {
  private Location startLocation;
  private Location location;
  private DistanceTrigger distanceTrigger;
  private TestTriggerListener distanceTriggerListener;

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    startLocation = new Location("Start");
    startLocation.setLongitude(10.0);
    startLocation.setLatitude(10.0);

    location = new Location("Test");
    location.setLongitude(20.0);
    location.setLatitude(20.0);

    distanceTriggerListener = new TestTriggerListener();
    distanceTrigger = new DistanceTrigger(50, this.getContext());
    distanceTrigger.locationUpdated(0, startLocation); // Set lastLocation to startLocation
    distanceTrigger.addListener(distanceTriggerListener);
  }

  public void testShouldActivateOn50MetersMovement() {
    distanceTrigger.locationUpdated(50, location);

    assertEquals(1, distanceTriggerListener.getCallCount());
    assertEquals(location, distanceTriggerListener.getLastGeoPosition());
  }

  public void testShouldActivateForMoreThan50MetersMovement() {
    distanceTrigger.locationUpdated(50, location);

    assertEquals(1, distanceTriggerListener.getCallCount());
    assertEquals(location, distanceTriggerListener.getLastGeoPosition());
  }

  public void testShouldActivateExactlyFourTimesAfter249MetersMovementOrMore() {
    // Tick 1
    distanceTrigger.locationUpdated(8, location);
    distanceTrigger.locationUpdated(32, location);
    distanceTrigger.locationUpdated(50, location);

    // Tick 2
    distanceTrigger.locationUpdated(40, location);
    distanceTrigger.locationUpdated(65, location);  // Sum: 65 + 50 = 115

    // Tick 3
    distanceTrigger.locationUpdated(10, location);
    distanceTrigger.locationUpdated(5, location);
    distanceTrigger.locationUpdated(20, location);
    distanceTrigger.locationUpdated(39, location);
    distanceTrigger.locationUpdated(30, location);
    distanceTrigger.locationUpdated(20, location);
    distanceTrigger.locationUpdated(70, location); // Sum 70 + 65 + 55 = 185

    // Tick 4
    distanceTrigger.locationUpdated(28, location);
    distanceTrigger.locationUpdated(33, location);
    distanceTrigger.locationUpdated(48, location);
    distanceTrigger.locationUpdated(55, location); // Sum 55 + 70 + 65 + 55 = 240

    // No last tick
    distanceTrigger.locationUpdated(9, location);

    assertEquals(4, distanceTriggerListener.getCallCount());
    assertEquals(location, distanceTriggerListener.getLastGeoPosition());
  }

  public void testShouldNotActivateOnLessThan50MetersMovement() {
    // Test for 49 meters + 1-2 random values below 50.
    // If distance can return negative values, we should
    // test for either errors or make sure it turns it
    // into absolute values.

    distanceTrigger.locationUpdated(49, location);

    assertEquals(0, distanceTriggerListener.getCallCount());
    assertEquals(startLocation, distanceTrigger.getLastLocation());
  }

  public void testShouldNotActivateOnLessThan50MetersMovement2() {
    distanceTrigger.locationUpdated(23, location);

    assertEquals(0, distanceTriggerListener.getCallCount());
    assertEquals(startLocation, distanceTrigger.getLastLocation());
  }

  public void testShouldNotActivateOnLessThan50MetersMovement3() {
    distanceTrigger.locationUpdated(1, location);

    assertEquals(0, distanceTriggerListener.getCallCount());
    assertEquals(startLocation, distanceTrigger.getLastLocation());
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
