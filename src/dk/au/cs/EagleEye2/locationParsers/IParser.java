package dk.au.cs.EagleEye2.locationParsers;

import android.location.Location;

public interface IParser {
  String parseLocation(Location location);
}
