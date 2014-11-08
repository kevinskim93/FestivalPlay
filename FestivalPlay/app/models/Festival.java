package models;

import java.io.File;
import java.io.FileReader;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.fasterxml.jackson.databind.ObjectMapper;

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
		final TrackSearchRequest request = api.searchTracks("Mr. Brightside").market("US").build();

		try {
		   final Page<Track> trackSearchResult = request.get();
		   System.out.println("I got " + trackSearchResult.getTotal() + " results!");
		} catch (Exception e) {
		   System.out.println("Something went wrong!" + e.getMessage());
		}
		/*
		//send information to TicketMaster and get JSON back to parse
		String urlBase = "https://app.ticketmaster.com/v1/light/events?apikey=";
		String APIkey = "u63kS9dlzruLsznw2xFAhVVgxftOCpBw";
		
		try{
			URL url = new URL(urlBase+APIkey);
			InputStream stream = url.openStream();
			generateArtist(stream);
		} catch (Exception e){
			e.printStackTrace();
		}*/
	}
	
	//Parses JSON for artists
	public void generateArtist(InputStream s){
		try{
			ObjectMapper mapper = new ObjectMapper();
			String string = mapper.readValue(s, String.class);
			System.out.println(string);
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
