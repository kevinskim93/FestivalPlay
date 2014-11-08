package models;

import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import play.db.ebean.Model;

@Entity
public class Festival extends Model {

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
	}
	
	//Parses JSON for artists
	public void generateArtist(){
		
	}
	
	public void generatePlaylist(){
		for(Artist a : artists){
			artistTopFive.put(a, a.listOfSongs);
		}	
	}
}
