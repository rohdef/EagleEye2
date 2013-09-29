package dk.au.cs.EagleEye2;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import dk.au.cs.EagleEye2.locationParsers.JsonParser;
import dk.au.cs.EagleEye2.registrars.FileRegistrar;
import dk.au.cs.EagleEye2.registrars.IRegistrar;
import dk.au.cs.EagleEye2.registrars.ServerRegistrar;
import dk.au.cs.EagleEye2.triggers.DistanceTrigger;
import dk.au.cs.EagleEye2.triggers.ITriggerListener;

public class EagleEye extends Activity implements ITriggerListener {
  /**
   * Called when the activity is first created.
   */
  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.w("EagleEye", "This is a test");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);

    DistanceTrigger dt = new DistanceTrigger(50, this.getBaseContext());
    dt.addListener(this);
    dt.startRegistering();
  }

  @Override
  public void fireTrigger(Location geoPosition) {
    Log.w("EagleEye", "We passed 50 meters");
    IRegistrar registrar = new FileRegistrar(new JsonParser(), this.getBaseContext());
    registrar.storeLocation(geoPosition);
  }
}
