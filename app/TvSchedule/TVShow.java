package TvSchedule;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class TVShow extends TvProgram {
	private String series;
	private String episode;
	
	
	public TVShow(JSONObject show, String channel, int counter) throws UnsupportedEncodingException, JSONException{
		super(show, channel, counter);
		JSONObject obj = show.getJSONObject("series");
		
		this.series = obj.getString("series");
		this.episode = obj.getString("episode");
	}
	
	public TVShow(String title, String duration, String description, String startTime, String channel, int counter, String series, String episode) throws UnsupportedEncodingException{
		super(title, duration,description,startTime, channel, counter);
		this.series = series;
		this.episode = episode;
	}
	
	public String getSeries(){
		return this.series;
	}
	
	public String getEpisode(){
		return this.episode;
	}
	
}
