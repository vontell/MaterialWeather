package io.github.vontell.materialweather;

import android.content.Context;
import android.content.res.Resources;

/**
 * This class takes in a weather object, and decides which image to use
 * based on the weather code and other factors
 * @author Aaron Vontell
 * @version 0.1
 *
 */
public class PicturePicker {
	
	private Weather weather;
	private Context appContext;
	private int sunny;
	private int cloudy;
	private int mist;
	private int rainy;
	private int thunder;
	private int snow;
	private int sleet;
	
	public PicturePicker(Weather w, Context context){
		
		weather = w;
		appContext = context;
		
	}
	
	public int getImageId(){
		
		int code = weather.getCode();
		int cloudCover = weather.getCloudCover();
		int temp = weather.getTempF();
		int visibility = weather.getVisibility();
		int speed = weather.getWindSpeedMi();
		String time = weather.getTime();
		Resources res = appContext.getResources();
		
		//indicates that a question mark text should be displayed
		int result = 0;
		
		
		
		return result;
		
	}

}
