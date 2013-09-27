package dk.au.cs.EagleEye2.triggers;

import android.test.AndroidTestCase;

/**
 * Timer interval is 1 second as specified.
 */
public class TimeTriggerTest extends AndroidTestCase {
  private TimerTrigger timerTrigger;
  private TestTriggerListener timerTriggerListener;

  @Override
  protected void setUp() throws Exception {
    super.setUp();

    timerTrigger = new TimerTrigger();
    timerTriggerListener = new TestTriggerListener();
  }

  public void testShouldActivate10TimesIn10Seconds() {
    for (int i=0; i<10; i++) {
      timerTrigger.timerTick();
    }

    assertEquals(10, timerTriggerListener.getCallCount());
  }
}
