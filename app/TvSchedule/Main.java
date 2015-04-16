package TvSchedule;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.TreeMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Main {
	
	Database db;
	Client client;
	
	public Main(){
		db = new Database();
		client = new Client();
	}
	
	public ArrayList<TvProgram> getMostBooked() throws UnsupportedEncodingException{
		return db.mostBooked();
	}
	
	/**
	 * Tekur a moti pontun
	 */
	public boolean bookTvProgram(TvProgram program){ //veit ekki hvort vid tokun inn JSONObject
		//TvProgram program = new TvProgram(obj, 1); //breyta kannski i tvshow /movie
		if(db.programExists(program)){
			return db.updateCounter(program);
		}
		else{
			return db.insertTvProgram(program);
		}
	}

	/**
	 * Notkun: schedule = getTvScheduleForChannel(channel);
	 * Fyrir: channel er stod sem apis hefur upplysingar um
	 * Eftir: schedule er ArrayList af TvProgram hlutum 
	 * @param channel stodin
	 * @return
	 * @throws JSONException 
	 * @throws UnsupportedEncodingException 
	 */
	public ArrayList<TvProgram> getTvScheduleForChannel(String channel) throws UnsupportedEncodingException, JSONException{
		ArrayList<TvProgram> returnArray = new ArrayList<TvProgram>();
		
		
		JSONArray schedule = client.getFromApis(channel);
		
		for(int i = 0; i < schedule.length(); i++){
			JSONObject obj = schedule.getJSONObject(i);
			int dur = durationInMinutes(obj.getString("duration"));
			TvProgram item = null;
			if(dur < 90){
				item = (TVShow) (new TVShow(obj, channel, 0));
				//TVShow item = new TVShow(obj, 0);
				
			} else{
				item = new Movie(obj, channel, 0);
				//Movie item = new Movie(obj, 0);
			}
			//TVShow tst = new TVShow(obj, 1);
			//returnArray.add(tst);
			//add each item from the schedule to the ArrayList as a TvProgram
			//returnArray.add(makeShowOrMovie(obj, 0));
			returnArray.add(item);
		}		
		return returnArray;
	}
	
	
	public TreeMap<String, ArrayList<TvProgram>> getTvScheduleForAllChannels() throws UnsupportedEncodingException, JSONException{
		JSONObject obj = client.getEverythingFromApis();
		//stod2 will map to an arraylist of programs shown on stod2 this day.
		TreeMap<String, ArrayList<TvProgram>> map = new TreeMap<String, ArrayList<TvProgram>>();
		
		//Find the name of the channels, to go through them one at a time
		JSONArray names = obj.names();
		
		for(int i = 0; i < names.length(); i++){
			ArrayList<TvProgram> arrList = new ArrayList<TvProgram>();
			map.put(names.getString(i), arrList); //puts current channel name as the key
			//structure of JSON object is : { stod2 = [0] = [array of json objects] = {json-object med title, durartion etc.} }
			JSONArray schedule = obj.getJSONArray(names.getString(i)).getJSONArray(0); 
			
			//Iterates over the JSON array and adds each item to the arrayList
			for(int j = 0; j<schedule.length(); j++){
				//System.out.println(names.getString(i));
				arrList.add(makeShowOrMovie(schedule.getJSONObject(j), names.getString(i), 0));
			}
		}
		
		return map;
	}
	
	/**
	 * 
	 * @param title
	 * @return TvProgram ef finnst, annars null
	 * @throws JSONException 
	 * @throws UnsupportedEncodingException 
	 */
	public ArrayList<TvProgram> searchForTvProgram(String title) throws UnsupportedEncodingException, JSONException{
		//String everything = new String(obj.toString().getBytes(), "UTF-8");
		//TODO : skilum arraylista af tv program hlutum, tomur ef ekkert finnst, geta verid fleiri hlutir ef margir finnast
		ArrayList<TvProgram> retArray = new ArrayList<TvProgram>();
		JSONObject obj = client.getEverythingFromApis();
		JSONArray channelNames = obj.names();
		
		for(int i = 0; i < channelNames.length(); i++){
			//same structure of json object as in getTvScheduleFor all channels
			JSONArray schedule = obj.getJSONArray(channelNames.getString(i)).getJSONArray(0);
			
			for(int j = 0; j<schedule.length(); j++){
				JSONObject tvProgram = schedule.getJSONObject(j);
				String tvProgramTitle = new String(tvProgram.getString("title").getBytes(), "UTF-8");
				
				if(tvProgramTitle.toLowerCase().indexOf(title.toLowerCase()) != -1){
					retArray.add(makeShowOrMovie(tvProgram, channelNames.getString(i), 0));
				}
				
			}
		}
		return retArray;
	}

	
	
	public static int durationInMinutes(String dur){
		int idx = dur.indexOf(":");
		
		int hours = Integer.parseInt(dur.substring(0, idx));
		int minutes = Integer.parseInt(dur.substring(idx+1, idx +3));
		
		return hours*60 + minutes;
	}
	
	public static TvProgram makeShowOrMovie(JSONObject obj, String channel, int counter) throws UnsupportedEncodingException, JSONException{
		//TODO: call this method from e.g. database instead
		// since this is copy paste code from there (og einvherstadar annarstadar?)
		int dur = durationInMinutes(obj.getString("duration"));
		TvProgram item = null;
		if(dur < 90){
			item = new TVShow(obj, channel, counter);
		} else{
			item = new Movie(obj, channel, counter);
		}
		return item;
	}
	
	
	public static void main(String args[]) throws UnsupportedEncodingException, JSONException, ClassNotFoundException{
		Main main = new Main();
		//TreeMap<String, ArrayList<TvProgram>> map = main.getTvScheduleForAllChannels();
		ArrayList<TvProgram> list = main.searchForTvProgram("modern");
		for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i).getChannel() + ", " + list.get(i).getTitle() + ", " + list.get(i).getStartTime());
		}
		/*JSONObject stundinOkkarJSON =  new JSONObject("{\"title\":\"Stundin okkar\",\"originalTitle\":\"\",\"duration\":\"00:23:38\",\"description\":\"Goi og Gloria taka a moti godum gestum i allan vetur. Leikhusrotturnar lata sig ekki vanta og aevintyrin eru aldrei langt undan. Umsjonarmadur er Gudjon David Karlsson. Dagskrargerd: Bragi tor Hinriksson.\",\"shortDescription\":\"\",\"live\":false,\"premier\":true,\"startTime\":\"2015-03-08 18:00:00\",\"aspectRatio\":\"16:9\",\"series\":{\"episode\":\"1\",\"series\":\"1\"}}");
		TvProgram show = Main.makeShowOrMovie(stundinOkkarJSON, "e", 1);
		System.out.println(main.bookTvProgram(show));*/
		
		/*for(int i = 0; i < list.size(); i++){
			TVShow item = (TVShow) list.get(i);
			System.out.println(item.getSeries());
		}*/
		
		//System.out.println(list.toString());
		
		/*for(int i = 0; i < list.size(); i++){
			System.out.println(list.get(i).getClass().getName());
		}
		System.out.println(program.getTitle());
		System.out.println(program.getDuration());*/
		
		//TvProgram program = main.searchForTvProgram("Modern Family");
		//System.out.println(program.getTitle());
		
		/*TreeMap<String, ArrayList<TvProgram>> map = main.getTvScheduleForAllChannels();
		ArrayList<TvProgram> mapList = map.get("stod2");
		for(int i = 0; i < mapList.size(); i++){
			System.out.println(mapList.get(i).getTitle());
		}*/
		
	}
	
	
}
