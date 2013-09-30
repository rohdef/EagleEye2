package dk.au.cs.EagleEye2.locationParsers;

import android.location.Location;
import com.google.gson.Gson;

public class JsonParser implements IParser {
  @Override
  public String parseLocation(Location location) {
    Gson gson = new Gson();
    return gson.toJson(location);
  }
}
