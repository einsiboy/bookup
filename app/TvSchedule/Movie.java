package TvSchedule;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie extends TvProgram {

	public Movie(JSONObject program, String channel, int counter) throws UnsupportedEncodingException, JSONException {
		super(program, channel, counter);
	}
	
	public Movie(String title, String duration, String description, String startTime, String channel, int counter) throws UnsupportedEncodingException{
		super(title, duration,description,startTime, channel, counter);
	}
	
	

}
