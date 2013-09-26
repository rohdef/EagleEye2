package dk.au.cs.EagleEye2.triggers;

import android.test.AndroidTestCase;

/**
 * The used maximum speed for the user is 2 m/s as specified
 */
public class DistanceWithSpeedTest extends AndroidTestCase {
  public void testShouldActivateAfter25SecondsOnA50MeterDistance() {
  }

  /**
   * Limits the amount of GPS fixes when the user either stands
   * still or moves close to the perimeter.
   */
  public void testShouldNotCheckLocationMoreThanOncePerTwoSeconds() {

  }

  public void testShouldTestMoreFrequentlyAsWeGetCloserToThePerimeterToAMinimumOfTwoSecs() {

  }

  public void testShouldActivateAndUseOneGpsFixIfWeHaveMoved50MetersOrMoreWithin25Seconds() {

  }

  public void testShouldActivateAndUseTwoGpsFixesIfWeMove36MetersIn25SecsThen15MetersIn7Secs() {

  }
}
