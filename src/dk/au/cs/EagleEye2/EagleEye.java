package dk.au.cs.EagleEye2;

import android.app.Activity;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.*;
import dk.au.cs.EagleEye2.locationParsers.JsonParser;
import dk.au.cs.EagleEye2.registrars.FileRegistrar;
import dk.au.cs.EagleEye2.registrars.IRegistrar;
import dk.au.cs.EagleEye2.registrars.ServerRegistrar;
import dk.au.cs.EagleEye2.triggers.DistanceTrigger;
import dk.au.cs.EagleEye2.triggers.ITriggerListener;

public class EagleEye extends Activity implements ITriggerListener {
  private boolean running;

  /**
   * Called when the activity is first created.
   */

  @Override
  public void onCreate(Bundle savedInstanceState) {
    Log.w("EagleEye", "This is a test");
    super.onCreate(savedInstanceState);
    setContentView(R.layout.main);
    normalizeUI();

    DistanceTrigger dt = new DistanceTrigger(50, this.getBaseContext());
    dt.addListener(this);
    dt.startRegistering();
  }

  private void normalizeUI(){
    startStopNormalize();
    strategyNormalize();

    // Running label
    LinearLayout runningLabel = (LinearLayout) findViewById(R.id.runningLabel);
    runningLabel.setVisibility(running ? View.VISIBLE : View.INVISIBLE);
  }

  @Override
  public void fireTrigger(Location geoPosition) {
    Log.w("EagleEye", "We passed 50 meters");
    IRegistrar registrar = new FileRegistrar(new JsonParser(), this.getBaseContext());
    registrar.storeLocation(geoPosition);
  }

  public void onStrategyChosen(View v){
    Log.w("EagleEye", "Strategy chosen");

    strategyNormalize();
  }

  private void strategyNormalize(){
    RadioGroup reportingStrategy = (RadioGroup) findViewById(R.id.reportingStrategy);

    reportingStrategy.setVisibility(running ? View.INVISIBLE : View.VISIBLE);

    // --

    RadioButton prs = (RadioButton) findViewById(R.id.periodicReportingStrategy);
    RadioButton dbrs = (RadioButton) findViewById(R.id.distanceBasedReportingStrategy);
    RadioButton dbrsMaxSpeed = (RadioButton) findViewById(R.id.distanceBasedReportingStrategyMaxSpeed);
    RadioButton dbrsAccelerometer = (RadioButton) findViewById(R.id.distanceBasedReportingStrategyAccelerometer);

    TableLayout prsSettings = (TableLayout) findViewById(R.id.periodicReportingStrategySettings);
    TableLayout dbrsSettings = (TableLayout) findViewById(R.id.distanceBasedRaportingStrategySettings);
    TableLayout dbrsMaxSpeedSettings = (TableLayout) findViewById(R.id.dbrsMaxSpeedSettings);

    prsSettings.setVisibility(!running && prs.isChecked() ? View.VISIBLE : View.INVISIBLE);
    dbrsSettings.setVisibility(!running && (dbrs.isChecked() || dbrsMaxSpeed.isChecked()) || dbrsAccelerometer.isChecked() ? View.VISIBLE : View.INVISIBLE);
    dbrsMaxSpeedSettings.setVisibility(!running && dbrsMaxSpeed.isChecked() ? View.VISIBLE : View.INVISIBLE);
  }

  /*
   * Start / Stop
   */

  // Start

  public void onStart(View v){
    if(running){
      Log.w("EagleEye", "Already running");
      return;
    }

    onStartBase();
  }

  private void onStartBase(){
    Log.w("EagleEye", "Start running");
    running = true;
    normalizeUI();

    onStartForward();
  }

  private void onStartForward(){
    // ToDo: Call some logic
    EditText timePeriodView = (EditText) findViewById(R.id.timePeriod);
    EditText distanceView = (EditText) findViewById(R.id.distance);
    EditText maxSpeedView = (EditText) findViewById(R.id.maxSpeed);

    int timePeriod = Integer.parseInt(timePeriodView.getText().toString());
    int distance = Integer.parseInt(distanceView.getText().toString());
    int maxSpeed = Integer.parseInt(maxSpeedView.getText().toString());

    RadioGroup reportingStrategy = (RadioGroup) findViewById(R.id.reportingStrategy);

    switch(reportingStrategy.getCheckedRadioButtonId()){
      case R.id.periodicReportingStrategy:
        Log.w("EagleEye", "[Start] Periodic Reporting Strategy (Forward) (Time period: "+timePeriod+")");
        break;
      case R.id.distanceBasedReportingStrategy:
        Log.w("EagleEye", "[Start] Distance-Based Reporting Strategy (Forward) (Distance: "+distance+")");
        break;
      case R.id.distanceBasedReportingStrategyMaxSpeed:
        Log.w("EagleEye", "[Start] Distance-Based Reporting Strategy - Maximum Speed (Forward) (Distance: "+distance+", Max speed: "+maxSpeed+")");
        break;
      case R.id.distanceBasedReportingStrategyAccelerometer:
        Log.w("EagleEye", "[Start] Distance-Based Reporting Strategy - Accelerometer (Forward) (Distance: "+distance+")");
        break;
    }
  }

  // Stop

  public void onStop(View v){
    if(!running){
      Log.w("EagleEye", "Not running");
      return;
    }

    onStopBase();
  }

  private void onStopBase(){
    Log.w("EagleEye", "Stop running");
    running = false;
    normalizeUI();

    onStopForward();
  }

  private void onStopForward(){
    // ToDo: Call some logic
    RadioGroup reportingStrategy = (RadioGroup) findViewById(R.id.reportingStrategy);

    switch(reportingStrategy.getCheckedRadioButtonId()){
      case R.id.periodicReportingStrategy:
        Log.w("EagleEye", "[Stop] Periodic Reporting Strategy (Forward)");
        break;
      case R.id.distanceBasedReportingStrategy:
        Log.w("EagleEye", "[Stop] Distance-Based Reporting Strategy (Forward)");
        break;
      case R.id.distanceBasedReportingStrategyMaxSpeed:
        Log.w("EagleEye", "[Stop] Distance-Based Reporting Strategy - Maximum Speed (Forward)");
        break;
      case R.id.distanceBasedReportingStrategyAccelerometer:
        Log.w("EagleEye", "[Stop] Distance-Based Reporting Strategy - Accelerometer (Forward)");
        break;
    }
  }

  // Start / Stop common

  private void startStopNormalize(){
    Button start = (Button) findViewById(R.id.start);
    Button stop = (Button) findViewById(R.id.stop);

    start.setEnabled(!running);
    stop.setEnabled(running);
  }
}
