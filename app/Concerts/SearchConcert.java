package Concerts;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchConcert {
	private static ArrayList<Concert> apisData = new ArrayList<Concert>();
	private static JSONObject obj;
	

	public static ArrayList<Concert> getApisData() throws JSONException{
		//Lesa inn json hlut fra apis.is
		try {
			obj = readJsonFromUrl("http://apis.is/concerts");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*Buid ad lesa inn json hlutinn*/
		
		JSONArray results = obj.getJSONArray("results");
		for (int i=0; i<results.length(); i++) {
		    JSONObject item = results.getJSONObject(i);
		    String name = item.getString("eventDateName");
		    String timeDate = item.getString("dateOfShow");
		    String loc2 = item.getString("eventHallName");
		    String loc1 = item.getString("userGroupName");
		    
		    String[] splitTimeDate = timeDate.split("T");
		    Concert concert = new Concert();
		    
		    concert.setName(name);
		    concert.setDate(splitTimeDate[0]);
		    concert.setTime(splitTimeDate[1]);
		    concert.setLoc(loc1+", "+loc2);
		    
		    apisData.add(concert);
		}
		
		return apisData;
	}
	
	private static String readAll(Reader rd) throws IOException {
	    StringBuilder sb = new StringBuilder();
	    int cp;
	    while ((cp = rd.read()) != -1) {
	      sb.append((char) cp);
	    }
	    return sb.toString();
	}

	private static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
		InputStream is = new URL(url).openStream();
		try {
			InputStreamReader in = new InputStreamReader(is, Charset.forName("UTF-8"));
	        BufferedReader rd = new BufferedReader(in);
	        String jsonText = readAll(rd);
	        JSONObject json = new JSONObject(jsonText);
	        return json;
		} finally {
			is.close();
	    }
	  }
	
	private static ArrayList<Concert> filter(ArrayList<Concert> data, String inputType, String inputData){
		ArrayList<Concert> list = new ArrayList<Concert>();
		for(int i = 0; i < data.size(); i++ ){
			Concert temp = data.get(i);
			if(inputType.equals("name")  && temp.getName().contains(inputData)){
				list.add(temp);
			}
			if(inputType.equals("time") && temp.getTime().contains(inputData)){
				list.add(temp);
			}
			if(inputType.equals("loc") && temp.getLoc().contains(inputData)){
				list.add(temp);
			}
			if(inputType.equals("date") && temp.getDate().contains(inputData)){
				list.add(temp);
			}
		}
		return list;
	}
	
	public static ArrayList<Concert> getFilteredData(String name, String time, String loc, String date){
		try {
			apisData = getApisData();
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ArrayList<Concert> tempList = new ArrayList<>();
		if(!name.equals("")) {
			for(int i = 0; i < apisData.size(); i++){
				Concert temp = apisData.get(i);
				if(temp.getName().contains(name)){
					tempList = filter(apisData, "name", name);
					break;
				}
			}
		}
		if(!time.equals("")) {
			if(tempList.size() == 0){
				for(int i = 0; i < apisData.size(); i++){
					Concert temp = apisData.get(i);
					if(temp.getTime().contains(time)){
						tempList = filter(apisData, "time", time);
						break;
					}
				}
			}else{
				for(int i = 0; i < tempList.size(); i++){
					Concert temp = tempList.get(i);
					if(temp.getTime().contains(time)){
						tempList = filter(tempList, "time", time);
						break;
					}
				}
			}
		}
			
		if(!loc.equals("")) {
			if(tempList.size() == 0){
				for(int i = 0; i < apisData.size(); i++){
					Concert temp = apisData.get(i);
					if(temp.getLoc().contains(loc)){
						tempList = filter(apisData, "loc", loc);
						break;
					}
				}
			}else{
				for(int i = 0; i < tempList.size(); i++){
					Concert temp = tempList.get(i);
					if(temp.getLoc().contains(loc)){
						tempList = filter(tempList, "loc", loc);
					}
				}
			}
		}
			
		if(!date.equals("")) {
			if(tempList.size() == 0){
				for(int i = 0; i < apisData.size(); i++){
					Concert temp = apisData.get(i);
					if(temp.getDate().contains(date)){
						tempList = filter(apisData, "date", date);
						break;
					}
				}
			}else{
				for(int i = 0; i < tempList.size(); i++){
					Concert temp = tempList.get(i);
					if(temp.getDate().contains(date)){
						tempList = filter(tempList, "date", date);
						break;
					}
				}
			}
		}
		return tempList;
	}
	
	public static void main(String[]args) throws JSONException{
		//ArrayList<Concert> concerts = search.getApisData();
		/*ArrayList<Concert> filter = getFilteredData("Eddie","20","Harpa","");
		
		for(int i = 0; i < filter.size(); i++){
			System.out.println(filter.get(i).getLoc());
		}*/
	}
	
}
