package TvSchedule;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

public class Database {
	
	public Database(){
		Connection c = null;
	    Statement stmt = null;
	    try {
	      Class.forName("org.sqlite.JDBC");
	      c = DriverManager.getConnection("jdbc:sqlite:db.db");
	      
	      
	      stmt = c.createStatement();
	      
		 String sql = "CREATE TABLE IF NOT EXISTS Bookings " +
		              "(Title INT," +
		              " Duration TEXT, " + 
		              " Description TEXT, " + 
		              " StartTime TEXT, " + 
		              " counter INT," +
		              " series TEXT," +
		              " episode TEXT)"; 
		  stmt.executeUpdate(sql);
	      stmt.close();
	    } catch ( Exception e ) {
	      System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	      System.exit(0);
	    }
	}
	
	
	public boolean insertTvProgram(TvProgram program){
		Connection c = null;
	    Statement stmt = null;
	    try {
	    	Class.forName("org.sqlite.JDBC");
		    c = DriverManager.getConnection("jdbc:sqlite:db.db");
		    
		    stmt = c.createStatement();
		    String title = program.getTitle();
		    String duration = program.getDuration();
		    String description = program.getDescription();
		    String startTime = program.getStartTime();
		    int counter = program.getCounter();
		    String series ="";
		    String episode = "";
		    if(program instanceof TVShow){
		    	TVShow temp = (TVShow)program;
		    	series= temp.getSeries();
		    	episode = temp.getEpisode();
		    }
		    
		    String sql = "INSERT INTO Bookings (Title, Duration, Description, StartTime, counter, series, episode) " +
		                   "VALUES (\"" + title + "\", \"" + duration + "\" , \"" + description + "\", \"" + startTime 
		                   + "\", \"" + counter + "\", \"" + series + "\", \"" + episode + "\");";
		    stmt.executeUpdate(sql);
		    stmt.close();
		    c.close();
		    
		    return true;
	    }
	    catch(Exception e){
	    	return false;
	    }
	    
	}
	
	public boolean updateCounter(TvProgram program){
		
		Connection c = null;
	    Statement stmt = null;
		
		
	    try {
	    	Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db.db");
			stmt = c.createStatement();
			
			String title = program.getTitle();
			
			String sql = "UPDATE Bookings set counter =  counter+1 WHERE Title = '" + title + "';";
			stmt.executeUpdate(sql);
			
		
			stmt.close();
			c.close();
			
			return true;
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			return false;
		}
	}
	
	
	public ArrayList<TvProgram> mostBooked() throws UnsupportedEncodingException{
		ArrayList<TvProgram> mostBooked = new ArrayList<TvProgram>();
		Connection c = null;
	    Statement stmt = null;
		
	    try {
	    	Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db.db");
			stmt = c.createStatement();
			
			String sql = "SELECT * FROM Bookings ORDER BY counter DESC LIMIT 3;";
			ResultSet results = stmt.executeQuery(sql);
			int i = 0;
			while(results.next()){
				String title = results.getString("Title");
				String duration = results.getString("Duration");
				String description = results.getString("Description");
				String startTime = results.getString("StartTime");
				int counter = results.getInt("counter");
				String series = results.getString("series");
				String episode = results.getString("episode");
				
				int length = Main.durationInMinutes(duration);
				if(length < 90)
					mostBooked.add(new TVShow(title, duration, description, startTime, "unknown", counter, series, episode));
				else
					mostBooked.add(new Movie(title, duration, description, startTime, "unknown", counter));
				i++;
			}
		
			stmt.close();
			c.close();
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return mostBooked;
	}
	
	
	
	
	public boolean programExists(TvProgram program){
		Connection c = null;
	    Statement stmt = null;
	    boolean bool = false;
		
	    try {
	    	Class.forName("org.sqlite.JDBC");
			c = DriverManager.getConnection("jdbc:sqlite:db.db");
			stmt = c.createStatement();
			
			String title = program.getTitle();
			//String duration = program.getDuration();
			String sql = "SELECT * FROM Bookings WHERE Title = '" + title + "';";
			//String sql = "SELECT * FROM Bookings WHERE Title = '" +title + "' AND Duration = '" + duration + "';";
			
			ResultSet results = stmt.executeQuery(sql);
			bool = results.next();
			
			stmt.close();
			c.close();
			return bool;
		} catch (SQLException | ClassNotFoundException e) {
			//e.printStackTrace();
			return false;
		}
	}
	
	
	
	public static void main(String args[]) throws UnsupportedEncodingException, JSONException{
		Database db = new Database();
		JSONObject stundinOkkarJSON = new JSONObject("{\"title\":\"Stundin okkar\",\"originalTitle\":\"\",\"duration\":\"00:23:38\",\"description\":\"Goi og Gloria taka a moti godum gestum i allan vetur. Leikhusrotturnar lata sig ekki vanta og aevintyrin eru aldrei langt undan. Umsjonarmadur er Gudjon David Karlsson. Dagskrargerd: Bragi tor Hinriksson.\",\"shortDescription\":\"\",\"live\":false,\"premier\":true,\"startTime\":\"2015-03-08 18:00:00\",\"aspectRatio\":\"16:9\",\"series\":{\"episode\":\"1\",\"series\":\"1\"}}");

		TVShow show = new TVShow(stundinOkkarJSON, "unknown", 1);
		db.insertTvProgram(show);
		db.updateCounter(show);
		//db.insertTvProgram(show);
		//System.out.println(show.getEpisode());
		//db.insertTvProgram(show);
		//db.programExists(show);
		/*ArrayList<TvProgram> mostBooked = db.mostBooked();
		for(int i = 0; i < mostBooked.size(); i++){
			TvProgram temp = mostBooked.get(i);
			System.out.println(temp.getTitle() + ": " + temp.getCounter());
		}*/
		//db.updateCounter(show);
		//db.insertTvProgram(show);
	}

}
