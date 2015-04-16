package test;

import static org.junit.Assert.assertTrue;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

import org.json.JSONException;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import TvSchedule.Main;
import TvSchedule.Movie;
import TvSchedule.TVShow;
import TvSchedule.TvProgram;



public class TestMain {
	Main main;
	
	@Before
	public void setUp(){
		main = new Main();
	}
	
	@After
	public void tearDown(){
		main = null;
	}
	
	@Test
	public void testTvProgramCounter() throws UnsupportedEncodingException, JSONException{
		ArrayList<TvProgram> schedule = main.getTvScheduleForChannel("stod2");
		TvProgram program = schedule.get(0);
		assertTrue(program.getCounter() == 0);
	}
	
	@Test
	public void testTvProgramDuration() throws UnsupportedEncodingException, JSONException{
		ArrayList<TvProgram> schedule = main.getTvScheduleForChannel("stod2");
		TvProgram program = schedule.get(0);
		assertTrue(program.getDuration() instanceof String);
	}
	
	@Test
	public void testIfTVShow() throws UnsupportedEncodingException, JSONException{
		ArrayList<TvProgram> schedule = main.getTvScheduleForChannel("stod2");
		TvProgram program = schedule.get(0);
		int durationInMinutes = Main.durationInMinutes(program.getDuration());
		if(durationInMinutes < 90)
			assertTrue(program instanceof TVShow);
	}
	
	@Test
	public void testIfMovie() throws UnsupportedEncodingException, JSONException{
		ArrayList<TvProgram> schedule = main.getTvScheduleForChannel("stod2");
		TvProgram program = schedule.get(0);
		int durationInMinutes = Main.durationInMinutes(program.getDuration());
		if(durationInMinutes >= 90)
			assertTrue(program instanceof Movie);
	}
	
	
	@Test
	public void testTvProgramTitle() throws UnsupportedEncodingException, JSONException{
		ArrayList<TvProgram> schedule = main.getTvScheduleForChannel("stod2");
		TvProgram program = schedule.get(0);
		assertTrue(program.getTitle() instanceof String);
	}
	
	@Test
	public void testStod2() throws UnsupportedEncodingException, JSONException {
		ArrayList<TvProgram> schedule = main.getTvScheduleForChannel("stod2");
		assertTrue(!schedule.isEmpty());
	}
	
	@Test
	public void testRuv() throws UnsupportedEncodingException, JSONException{
		ArrayList<TvProgram> schedule = main.getTvScheduleForChannel("ruv");
		assertTrue(!schedule.isEmpty());
	}
	
	@Test
	public void testStod2Bio() throws UnsupportedEncodingException, JSONException{
		ArrayList<TvProgram> schedule = main.getTvScheduleForChannel("stod2Bio");
		assertTrue(!schedule.isEmpty());
	}
	
	@Test
	public void testStod3() throws UnsupportedEncodingException, JSONException{
		ArrayList<TvProgram> schedule = main.getTvScheduleForChannel("stod3");
		assertTrue(!schedule.isEmpty());
	}
	
	@Test
	public void testInstanceOf() throws UnsupportedEncodingException, JSONException{
		ArrayList<TvProgram> schedule = main.getTvScheduleForChannel("ruv");
		assertTrue(schedule.get(0) instanceof TvProgram);
	}

}
