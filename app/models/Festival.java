package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.persistence.Entity;
import javax.persistence.Id;

import models.Artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;

import play.db.ebean.Model;

@Entity
public class Festival extends Model{

	@Id
	//TicketMaster's id for this given Festival
	public static String id;
	
	//TicketMaster's official name for this Festival
	public String name;
	public static String eventName;
	
	//TicketMaster's list of artists performing at the festival
	public static ArrayList<Artist> artists;
	
	//Part where we combine TicketMaster and Spotify for playlist
	public static Map< Artist, ArrayList<String> > artistTopFive;
	
	public static URL url;
	
	public Festival(String n){
		name = new String(n);
		System.out.println(name);
		urlParse();	
		
	}
	
	
	//Ticketmaster
	
	//Parses JSON for artists
	public void generateArtist(InputStream s){
		try{
			
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
	
	
	
	private void urlParse(){

		artists = new ArrayList<Artist>();
		
		//System.out.println("Here are the performers:");
		
		String fest = new String(name.replaceAll(" ", "+"));
		
		try {
			
			String urlString = "https://app.ticketmaster.com/v1/light/events?apikey=u63k"
					+ "S9dIzruLsznw2xFAhVVgxftOCpBw&start_date=2013-01-01T18:51:43Z&q="
					+ fest
					+ "&sort_by=match";
			
			url = new URL(urlString);
									
			System.out.println(url.toString());
			
			InputStream stream = url.openStream();
			String jsonReply = convertStreamToString(stream);
			
			//System.out.println(jsonReply);
			
			parseXML(jsonReply);
			
		} catch (Exception e){
			e.printStackTrace();
		}	
	}
	
	private void parseXML(String jsonString){
		try {
			 		
			JsonParserFactory factory=JsonParserFactory.getInstance();
			JSONParser parser=factory.newJsonParser();
			Map jsonData = parser.parseJson(jsonString);
		 			
			List eventList=(List)jsonData.get("events");
					
			//Gets the first event
			Map event = (Map) eventList.get(0);
			
			eventName = (String) event.get("name");
			
			//System.out.println(eventName);
			
			//Gets List of artists
			List list = (List) event.get("artists");
			
			
			for(int i = 0; i < list.size(); i++){
				//Thread.sleep(100);
				Map name1 = (Map) list.get(i);
				String name = (String) name1.get("name");
				artists.add(new Artist(name));
				//System.out.println(artists.get(i).toString());

			}
		
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private String convertStreamToString(InputStream is) {

		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder sb = new StringBuilder();

		String line = null;
		try {
		    while ((line = reader.readLine()) != null) {
		        sb.append(line + "\n");
		    }
		} catch (IOException e) {
		    e.printStackTrace();
		} finally {
		    try {
		        is.close();
		    } catch (IOException e) {
		        e.printStackTrace();
		    }
		}
		return sb.toString();
	}
	
}
