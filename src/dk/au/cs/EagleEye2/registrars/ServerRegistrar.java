package dk.au.cs.EagleEye2.registrars;

import android.location.Location;
import android.os.AsyncTask;
import android.util.Log;
import dk.au.cs.EagleEye2.locationParsers.IParser;
import dk.au.cs.EagleEye2.triggers.Trigger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerRegistrar extends Registrar {
  private IParser parser;

  public ServerRegistrar(IParser parser, Trigger trigger) {
    super(trigger);
    this.parser = parser;
  }

  @Override
  public void fireTrigger(Location location) {
    String valueToStore = parser.parseLocation(location);

    new TcpAsyncTask().execute(valueToStore);
  }

  private class TcpAsyncTask extends AsyncTask<String, Void, String> {
    @Override
    protected String doInBackground(String... params) {
      Log.w("EagleEye", "Sending data to server");
      String valueToStore = params[0];
      valueToStore = valueToStore.replaceAll("(\\r|\\n)", "");
      Socket socket = null;
      try {
        socket = new Socket("rohdef.dk", 57005);

        OutputStream out = socket.getOutputStream();
        PrintWriter output = new PrintWriter(out, true);

        output.println(valueToStore);
        socket.close();
      } catch (IOException e) {
        e.printStackTrace();
      }

      return "success";
    }
  }
}
