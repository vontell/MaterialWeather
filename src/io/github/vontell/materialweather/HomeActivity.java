package io.github.vontell.materialweather;

import java.io.IOException;

import org.json.JSONException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * The home page of the application, which will
 * show some basic weather information and an
 * icon.
 * @author Aaron Vontell
 * @version 0.1
 */
public class HomeActivity extends Activity {
	
	//The view to be manipulated
	TextView temperature;
	TextView locationView;
	
	//The weather object
	Weather weather;
	
	//The user's location in string form
	String location;
	
	//TODO: Save the location in shared prefs
	SharedPreferences prefs;
	SharedPreferences.Editor editor;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		
		StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
		StrictMode.setThreadPolicy(policy);
		
		prefs = getSharedPreferences(getResources().getString(R.string.PREFS_KEY),
									 Context.MODE_PRIVATE);
		editor = prefs.edit();
		
		//Set default location
		location = prefs.getString("location", "London");
		
		//Loads the views into memory
		temperature = (TextView) findViewById(R.id.degrees);
		locationView = (TextView) findViewById(R.id.location);
		
		refresh();
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.home, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		if (id == R.id.action_refresh){
			refresh();
			return true;
		}
		if (id == R.id.action_edit){
			editLocation();
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void editLocation(){
		
		AlertDialog.Builder locDialog = new AlertDialog.Builder(this);
		locDialog.setTitle(getResources().getString(R.string.edit_location_title));
		locDialog.setMessage(getResources().getString(R.string.edit_location_message));
		
		// Set an EditText view to get user input 
		final EditText input = new EditText(this);
		locDialog.setView(input);

		locDialog.setPositiveButton("Submit", new DialogInterface.OnClickListener() {
		public void onClick(DialogInterface dialog, int whichButton) {
		  String value = input.getText().toString();
		  if(value.length() == 0 || value.contains(" ")){
			  makeToast("Invalid Input");
		  }
		  else{
			  location = value;
			  editor.putString("location", value).commit();
			  refresh();
		  }
		  }
		});

		locDialog.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
		  public void onClick(DialogInterface dialog, int whichButton) {
		    // Canceled.
		  }
		});

		locDialog.show();
		
	}
	
	public void refresh(){
		
		//TODO: Stop dialog from being interrupted
		//TODO: Make it so no dialog appears; just have the sync button rotate
		//TODO: Make it asynchronous, do not run on main thread
		//TODO: Make a default weather object
		ProgressDialog pDialog = new ProgressDialog(this);
		pDialog.setTitle(R.string.weather_dialog_title);
		pDialog.setMessage(getResources().getString(R.string.weather_dialog_message));
		pDialog.show();
		
		weather = null;
		
		try {
			weather = new Weather(location, 1);
			editor.putString("weatherContent", weather.getContent()).commit();
			makeToast("Success!");
			populate();
		} catch (IOException e) {
			makeToast("Error getting weather, try again.");
		} catch (JSONException e) {
			makeToast("Error getting weather, try again.");
		}
		
		pDialog.dismiss();
		
		
	}
	
	public void populate(){
		temperature.setText("" + weather.getTempF() + getResources().getString(R.string.deg));
		locationView.setText("" + weather.getLocation());
	}
	
	public void makeToast(String s){
		Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
	}
}
