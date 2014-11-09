package models;

import java.util.ArrayList;

import com.wrapper.spotify.*;
import com.wrapper.spotify.methods.TrackSearchRequest;
import com.wrapper.spotify.models.Page;
import com.wrapper.spotify.models.Track;

import play.db.ebean.Model;

public class Artist extends Model{
	public String id;
	
	public String name;
	
	//List of songs from Spotify
	public ArrayList<String> listOfSongs;
	
	public static Api api;
	
	public Artist(String name){
		this.name = name;
		
		setAPI();
		getSongs();
	}
	
	//Spotify
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
	
	
	//gets top 10 songs from Spotify and stores it in listOfSongs
	public void getSongs(){
		
	}
	/*
	//Iterates through songs from Spotify to get Top Five and returns them
	public ArrayList<String> getTopFive(){
		ArrayList<String> topFive = new ArrayList<String>();
		
		return topFive;
	}*/
	
	public String toString(){
		return name;
	}
	
}
