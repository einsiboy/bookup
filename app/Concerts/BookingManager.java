package Concerts;

import java.sql.*;

public class BookingManager {
	private static final BookingManager INSTANCE = new BookingManager();
	private Statement stmt;
	private Connection conn; 
	
	// BookingManager creates the database - may only be called once!
	private BookingManager() {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			conn = DriverManager.getConnection("jdbc:sqlite:Booking.db");
			stmt = null;
			createDatabase();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void createDatabase() throws SQLException{
		stmt = conn.createStatement(); 
		stmt.executeUpdate("CREATE TABLE IF NOT EXISTS Concerts(name varchar(100), time varchar(8), loc varchar(50), date varchar(10), seats int);");
	}
	
	/*private void destroyTable() throws SQLException{
		stmt = conn.createStatement(); 
		stmt.executeUpdate("DROP TABLE Concerts");
	}*/
	
	private void addConcert(Concert concert) throws SQLException{
		stmt = conn.createStatement(); 	
		stmt.executeUpdate("INSERT INTO Concerts VALUES('"+concert.getName()+"','"+concert.getTime()+"','"+concert.getLoc()+"','"+concert.getDate()+"',100);");
	}
	
	private boolean checkIfConcertExists(Concert concert) throws SQLException{
		stmt = conn.createStatement(); 
		int count = 0;
		ResultSet rs = stmt.executeQuery("SELECT * FROM Concerts WHERE name='"+concert.getName()+"' AND time='"+concert.getTime()+"' AND date='"+concert.getDate()+"';");
		while(rs.next()){
			System.out.println(rs.getString("name")+" - eg er til");
			count++;
		}
		if(count>0) return true;
		return false;
	}

	public Boolean bookConcert(Concert concert) throws SQLException{
		if (!checkIfConcertExists(concert)) {
			System.out.println("eg er ekki til..");
			addConcert(concert);
		}
		
		int numberOfSeats = -1;
		ResultSet rs = stmt.executeQuery("SELECT seats FROM Concerts WHERE name='"+concert.getName()+"' AND time='"+concert.getTime()+"' AND date='"+concert.getDate()+"';");
		while(rs.next()){
			numberOfSeats = rs.getInt("seats");
		}
		
		if (numberOfSeats > 0) {
	
			numberOfSeats--;
			concert.setAvailableSeats(numberOfSeats);
			stmt = conn.createStatement(); 
			stmt.executeUpdate("UPDATE Concerts SET seats="+numberOfSeats+" WHERE name='"+concert.getName()+"' AND time='"+concert.getTime()+"';");
			return true;
		}
		 return false;
	}

	public static BookingManager getInstance() {
		return INSTANCE;
	}	
}
