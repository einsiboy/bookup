package TvSchedule;

import java.io.UnsupportedEncodingException;

import org.json.JSONException;
import org.json.JSONObject;

public class TvProgram {
	private String title;
	private String duration;
	private String description;
	private String startTime;
	private String channel;
	private int counter;
	

	public TvProgram(JSONObject program, String channel, int counter) throws UnsupportedEncodingException, JSONException{
		this.title = new String(program.getString("title").getBytes(), "UTF-8");
		this.duration = program.getString("duration");
		this.description = program.getString("description");
		this.startTime = program.getString("startTime");
		this.channel = channel;
		this.counter = counter; //breytum kannski
	}
	
	public TvProgram(String title, String duration, String description, String startTime, String channel, int counter) throws UnsupportedEncodingException{
		this.title = new String(title.getBytes(), "UTF-8");
		this.duration = duration;
		this.description = description;
		this.startTime = startTime;
		this.channel = channel;
		this.counter = counter;
	}


	public String getTitle() {
		return title;
	}


	public String getDuration() {
		return duration;
	}


	public String getDescription() {
		return description;
	}


	public String getStartTime() {
		return startTime;
	}


	public int getCounter() {
		return counter;
	}
	
	public String getEpisode(){
		return "";
	}
	
	public String getChannel(){
		return channel;
	}
	
	public String getSeries(){
		return "";
	}
	
	
	
	
}
