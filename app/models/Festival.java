package models;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.Id;

import com.wrapper.spotify.*;
import com.wrapper.spotify.methods.TrackSearchRequest;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.Track;
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
	
	public Api api;
	
	public Festival(String name){
		this.name = name;
		setAPI();
		final TrackSearchRequest request = api.searchTracks("Mr. Brightside").market("US").build();

		try {
		   final Page<Track> trackSearchResult = request.get();
		   System.out.println("I got " + trackSearchResult.getTotal() + " results!");
		} catch (Exception e) {
		   System.out.println("Something went wrong!" + e.getMessage());
		}
	}
	
	public void setAPI(){
		final String clientId = "e61890b23e6d46eeb90fc9818cbe4c29";
		final String clientSecret = "6929417ed59e4e6a91cdb02bea8222d1";
		//final String redirectURI = "<your_redirect_uri>";
		api = Api.builder()
				  .clientId(clientId)
				  .clientSecret(clientSecret)
				 // .redirectURI(redirectURI)
				  .build();
	}
	
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
}
