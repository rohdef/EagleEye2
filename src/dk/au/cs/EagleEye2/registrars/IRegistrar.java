package dk.au.cs.EagleEye2.registrars;

import android.location.Location;

/**
 * Registering service to register GPS fixes to for instance files or similar.
 */
public interface IRegistrar {
  void storeLocation(Location location);
}
