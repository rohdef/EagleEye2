package dk.au.cs.EagleEye2.triggers;

import android.hardware.Sensor;

import java.util.Timer;
import java.util.TimerTask;

public class TimerTrigger extends Trigger {
  public TimerTrigger() {

  }

  public void startTimer() {
    Timer timer = new Timer();

    // This has to be done with an anonymous class, since it's an abstract class
    // and we're already extending a class. Just distribute the event to a method
    // which is testable.
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        timerTick();
      }
    }, 0, 0);
  }

  public void timerTick() {

  }

}
