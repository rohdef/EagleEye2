package dk.au.cs.EagleEye2.registrars;

import android.content.Context;
import android.location.Location;
import android.os.Environment;
import android.util.Log;
import dk.au.cs.EagleEye2.locationParsers.IParser;

import java.io.*;

public class FileRegistrar implements IRegistrar {
  private IParser parser;
  private Context context;

  public FileRegistrar(IParser parser, Context context) {
    this.parser = parser;
  }

  @Override
  public void storeLocation(Location location) {
    String valueToStore = parser.parseLocation(location);

    // Check for storage permissions according to documentation
    boolean mExternalStorageAvailable = false;
    boolean mExternalStorageWriteable = false;
    String state = Environment.getExternalStorageState();

    if (Environment.MEDIA_MOUNTED.equals(state)) {
      // We can read and write the media
      mExternalStorageAvailable = mExternalStorageWriteable = true;
    } else if (Environment.MEDIA_MOUNTED_READ_ONLY.equals(state)) {
      // We can only read the media
      mExternalStorageAvailable = true;
      mExternalStorageWriteable = false;
    } else {
      // Something else is wrong. It may be one of many other states, but all we need
      //  to know is we can neither read nor write
      mExternalStorageAvailable = mExternalStorageWriteable = false;
    }

    if (mExternalStorageAvailable && mExternalStorageWriteable) {
      try {
        File root = Environment.getExternalStorageDirectory();
        File file = new File(root, "eagleEye.txt");

//      context.openFileOutput("eagleEye.txt", Context.MODE_WORLD_WRITEABLE)
        FileOutputStream fos = new FileOutputStream(file);
        fos.write(valueToStore.getBytes());
        fos.close();
      }
      catch (IOException e) {
        Log.e("Exception", "File write failed: " + e.toString());
      }
    } // Should there be some error message?
  }
}
