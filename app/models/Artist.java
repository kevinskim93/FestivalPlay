package models;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.wrapper.spotify.*;
import com.wrapper.spotify.exceptions.WebApiException;
import com.wrapper.spotify.methods.ArtistSearchRequest;
import com.wrapper.spotify.methods.TopTracksRequest;
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
		associateWithSpotify();
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
	
	public void associateWithSpotify(){
		final ArtistSearchRequest request = api.searchArtists(name).query("match").limit(1).build();

		try {
		   final Page<com.wrapper.spotify.models.Artist> artistSearchResult = request.get();
		   final List<com.wrapper.spotify.models.Artist> artists = artistSearchResult.getItems();
		   //System.out.println(artists.get(0).getName());
		   //System.out.println(artists.get(0).getId());
		   id = artists.get(0).getId();
		   
		   getSongs();
		} catch (Exception e) {
		   System.out.println("Something went wrong!" + e.getMessage());
		}
	}
	
	//gets top 10 songs from Spotify and stores it in listOfSongs
	private void getSongs(){
		try {
			Comparator<Track> c = new Comparator<Track>(){
				@Override
				public int compare(Track l, Track r){
					if (l.getPopularity() < r.getPopularity()){
						return 1;
					}
					else if (l.getPopularity() > r.getPopularity()){
						return -1;
					}
					return 0;
				}
			};
			final TopTracksRequest topFive = api.getTopTracksForArtist(id, "US").build();
			final List<Track> tracks = topFive.get();
			Collections.sort(tracks, c);
			/*
			System.out.println(tracks.get(0).getName());
			System.out.println(tracks.get(0).getPopularity());
			System.out.println(tracks.get(1).getName());
			System.out.println(tracks.get(1).getPopularity());
			*/
		} catch (IOException | WebApiException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
