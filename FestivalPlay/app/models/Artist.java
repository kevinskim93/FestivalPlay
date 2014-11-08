package models;

import java.util.ArrayList;

import play.db.ebean.Model;

public class Artist extends Model{
	public String id;
	
	public String name;
	
	//List of songs from Spotify
	public ArrayList<String> listOfSongs;
	
	/*//gets entire list of songs from Spotify
	public void getSongs(){
		
	}
	
	//Iterates through songs from Spotify to get Top Five and returns them
	public ArrayList<String> getTopFive(){
		ArrayList<String> topFive = new ArrayList<String>();
		
		return topFive;
	}*/
	
}
