package TvSchedule;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Scanner;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Client {
	
	public JSONArray getFromApis(String channel)  throws JSONException{
		URL url;
		try {
			url = new URL("http://apis.is/tv/" + channel);

			try {
				// read from the URL
				Scanner scan;
				scan = new Scanner(url.openStream());

				String str = "";
				while (scan.hasNext())
					str += scan.nextLine();
				scan.close();

				// build a JSON object
				JSONObject obj = new JSONObject(str); //err

				// get the first result
				return obj.getJSONArray("results"); //err
			} catch(IOException ex) {
				// there was some connection problem, or the file did not exist on the server,
				// or your URL was not in the right format.
				// think about what to do now, and put it here.
				ex.printStackTrace(); // for now, simply output it.
				}
	} catch (MalformedURLException e) {
		e.printStackTrace();
		}
		return null;
	}
	
	public JSONObject getEverythingFromApis() throws JSONException {
		JSONObject returnObj = new JSONObject();
		
		URL url;
		try {
			url = new URL("http://apis.is/tv/");
			try {
				Scanner scan;
				scan = new Scanner(url.openStream());
					String str = "";
					while (scan.hasNext())
						 str += scan.nextLine();
					scan.close();

					// build a JSON object
					JSONObject obj = new JSONObject(str); //err

					// get the first result
					JSONArray res = obj.getJSONArray("results");
					JSONObject channels = res.getJSONObject(0).getJSONObject("endpoints"); //err


					Iterator<?> keys = channels.keys();

					while( keys.hasNext() ){
						String key = (String)keys.next();
					   returnObj.append(key, this.getFromApis(key)); //err

					}
				
				return returnObj;
			} catch(IOException ex) {
				// there was some connection problem, or the file did not exist on the server,
				// or your URL was not in the right format.
				// think about what to do now, and put it here.
				ex.printStackTrace(); // for now, simply output it.
			}

	} catch (MalformedURLException e) {
		e.printStackTrace();
		}
		return null;
	}

	//Fyrir test
	/*public static void main(String args[]){
		Client client = new Client();
		//JSONArray arr = client.getFromApis("ruv");
		System.out.println(client.getEverythingFromApis());
	}*/
}
