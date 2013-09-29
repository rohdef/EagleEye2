package dk.au.cs.EagleEye2.registrars;

import android.location.Location;
import dk.au.cs.EagleEye2.locationParsers.IParser;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;

public class ServerRegistrar implements IRegistrar {
  private IParser parser;

  public ServerRegistrar(IParser parser) {
    this.parser = parser;
  }

  @Override
  public void storeLocation(Location location) {
    String valueToStore = parser.parseLocation(location);

    Socket socket = null;
    try {
      socket = new Socket("rohdef.dk", 57005);

      OutputStream out = socket.getOutputStream();
      PrintWriter output = new PrintWriter(out);

      output.println(valueToStore);
      socket.close();
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
