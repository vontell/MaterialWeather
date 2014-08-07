package io.github.vontell.materialweather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * An object that represents weather information, such
 * as the temperature, humidity, time, location, etc...
 * Uses the World Weather Online API
 * @author Aaron Vontell
 * @version 0.1
 */
public class Weather {

	//The general attributes of weather
	private int tempF; 				//degrees in Fahrenheit
	private int tempC; 				//degrees in Celsius
	private int humidity; 			//Percentage Humidity
	private int cloudCover;			//Cloudcover (units?)
	private double precip;			//Precipitation in mm
	private int pressure;			//Pressure in millibars
	private int visibility;			//Visibility in km
	private int code;				//Weather code used by the API
	private String description;		//Human description of the weather
	private String windDirString;	//Direction in cardinal terms
	private int windDirDeg;			//Direction in degrees
	private int windSpeedKm;		//Wind speed in km/h
	private int windSpeedMi;		//Wind speed in mi/h
	private String location;		//Location (city, zip, long/lat, etc...)
	private String time;			//Time the weather was retrieved
	private String date;			//Date the weather was retrieved
	private String url;				//The constructed URL
	//TODO
	private int picID;				//The android drawable to be used for the image
	private String content;			//The JSON contents from the weather api
	
	public Weather(String loc, int days) throws IOException, JSONException {
		
		createUrl(loc, days);
		content = retrieveData();
		populateInfo();
		
		location = loc;
		
	}
	
	/**
	 * Creates the URL of that will be called to the API Web Service
	 * @param loc The current or requested location of the user
	 * @param days The number of days to look ahead
	 */
	private void createUrl(String loc, int days){
		
		url = "http://api.worldweatheronline.com/free/v1/weather.ashx?";
		url += "q=" + loc;
		url += "&format=json";
		url += "&num_of_days=" + days;
		url += "&key=41b3e0d0e925d9d0c03021be9ff7d2a2a1f56054";
		
	}
	
	/**
	 * Method that opens a connection to the URL, and
	 * reads the data from the API
	 * @return The JSON content received from the server
	 * @throws IOException
	 */
	private String retrieveData() throws IOException{
		
		String content = "";
		
		URL wUrl = new URL(url);
		URLConnection conn = wUrl.openConnection();
		BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
		
		String line;
		while ((line = bufferedReader.readLine()) != null){
			content += line + "\n";
		}
		bufferedReader.close();
		
		return content;
		
	}
	
	/**
	 * Populates the info of the weather object
	 * by parsing the JSON string
	 * @throws JSONException
	 */
	private void populateInfo() throws JSONException{
		
		JSONObject jObject = new JSONObject(content);
		jObject = jObject.getJSONObject("data");
		JSONArray jArray = jObject.getJSONArray("current_condition");
		jObject = jArray.getJSONObject(0);
		
		cloudCover = jObject.getInt("cloudcover");
		humidity = jObject.getInt("humidity");
		time = jObject.getString("observation_time");
		precip = jObject.getDouble("precipMM");
		pressure = jObject.getInt("pressure");
		tempC = jObject.getInt("temp_C");
		tempF = jObject.getInt("temp_F");
		visibility = jObject.getInt("visibility");
		code = jObject.getInt("weatherCode");
		description = jObject.getJSONArray("weatherDesc").getJSONObject(0).getString("value");
		windDirString = jObject.getString("winddir16Point");
		windDirDeg = jObject.getInt("winddirDegree");
		windSpeedKm = jObject.getInt("windspeedKmph");
		windSpeedMi = jObject.getInt("windspeedMiles");
		
	}
	
	//BELOW ARE ECLIPSE-GENERATED SETTERS AND GETTERS

	/**
	 * @return the tempF
	 */
	public int getTempF() {
		return tempF;
	}

	/**
	 * @return the tempC
	 */
	public int getTempC() {
		return tempC;
	}

	/**
	 * @return the humidity
	 */
	public int getHumidity() {
		return humidity;
	}

	/**
	 * @return the cloudCover
	 */
	public int getCloudCover() {
		return cloudCover;
	}

	/**
	 * @return the precip
	 */
	public double getPrecip() {
		return precip;
	}

	/**
	 * @return the pressure
	 */
	public int getPressure() {
		return pressure;
	}

	/**
	 * @return the visibility
	 */
	public int getVisibility() {
		return visibility;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @return the windDirString
	 */
	public String getWindDirString() {
		return windDirString;
	}

	/**
	 * @return the windDirDeg
	 */
	public int getWindDirDeg() {
		return windDirDeg;
	}

	/**
	 * @return the windSpeedKm
	 */
	public int getWindSpeedKm() {
		return windSpeedKm;
	}

	/**
	 * @return the windSpeedMi
	 */
	public int getWindSpeedMi() {
		return windSpeedMi;
	}

	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}

	/**
	 * @return the time
	 */
	public String getTime() {
		return time;
	}

	/**
	 * @return the date
	 */
	public String getDate() {
		return date;
	}

	/**
	 * @return the picID
	 */
	public int getPicID() {
		return picID;
	}

	/**
	 * @param tempF the tempF to set
	 */
	public void setTempF(int tempF) {
		this.tempF = tempF;
	}

	/**
	 * @param tempC the tempC to set
	 */
	public void setTempC(int tempC) {
		this.tempC = tempC;
	}

	/**
	 * @param humidity the humidity to set
	 */
	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	/**
	 * @param cloudCover the cloudCover to set
	 */
	public void setCloudCover(int cloudCover) {
		this.cloudCover = cloudCover;
	}

	/**
	 * @param precip the precip to set
	 */
	public void setPrecip(double precip) {
		this.precip = precip;
	}

	/**
	 * @param pressure the pressure to set
	 */
	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	/**
	 * @param visibility the visibility to set
	 */
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @param windDirString the windDirString to set
	 */
	public void setWindDirString(String windDirString) {
		this.windDirString = windDirString;
	}

	/**
	 * @param windDirDeg the windDirDeg to set
	 */
	public void setWindDirDeg(int windDirDeg) {
		this.windDirDeg = windDirDeg;
	}

	/**
	 * @param windSpeedKm the windSpeedKm to set
	 */
	public void setWindSpeedKm(int windSpeedKm) {
		this.windSpeedKm = windSpeedKm;
	}

	/**
	 * @param windSpeedMi the windSpeedMi to set
	 */
	public void setWindSpeedMi(int windSpeedMi) {
		this.windSpeedMi = windSpeedMi;
	}

	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(String time) {
		this.time = time;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(String date) {
		this.date = date;
	}

	/**
	 * @param picID the picID to set
	 */
	public void setPicID(int picID) {
		this.picID = picID;
	}

}
