package models;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import play.db.ebean.Model;

@Entity
public class Festival extends Model{

	@Id
	//TicketMaster's id for this given Festival
	public String id;
	
	//TicketMaster's official name for this Festival
	public String name;
	
	//TicketMaster's list of artists performing at the festival
	public ArrayList<Artist> artists;
	
	//Part where we combine TicketMaster and Spotify for playlist
	public Map< Artist, ArrayList<String> > artistTopFive;
	
	public Festival(String name){
		this.name = name;
		System.out.println(name);
		
		//send information to TicketMaster and get JSON back to parse
		String APIkey = "u63kS9dlzruLsznw2xFAhVVgxftOCpBw";
		String urlBase = "http://app.ticketmaster.com/v1/light/events?apikey=";
		generateArtist(urlBase+APIkey);
	}
	
	//Parses JSON for artists
	public void generateArtist(String url){
		try{
			JSONParser parser = new JSONParser();
			Object obj = parser.parse(new FileReader(url));
			
			JSONObject jsonObj = (JSONObject) obj;
			String testing = (String) jsonObj.get("token");
			System.out.println(testing);
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void generatePlaylist(){
		for(Artist a : artists){
			artistTopFive.put(a, a.listOfSongs);
		}	
	}
}
