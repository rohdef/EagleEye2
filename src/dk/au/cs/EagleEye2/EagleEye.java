package dk.au.cs.EagleEye2;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import dk.au.cs.EagleEye2.triggers.DistanceTrigger;

public class EagleEye extends Activity {
  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.w("EagleEye", "This is a test");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    DistanceTrigger dt = new DistanceTrigger(50);
    dt.startWatchingDistances(this.getBaseContext());
  }
}
