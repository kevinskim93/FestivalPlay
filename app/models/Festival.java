package models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import javax.persistence.Entity;
import javax.persistence.Id;

import models.Artist;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.SettableFuture;
import com.json.parsers.JSONParser;
import com.json.parsers.JsonParserFactory;
import com.wrapper.spotify.Api;
import com.wrapper.spotify.methods.AddTrackToPlaylistRequest;
import com.wrapper.spotify.methods.PlaylistCreationRequest;
import com.wrapper.spotify.models.AuthorizationCodeCredentials;
import com.wrapper.spotify.models.Playlist;
import com.wrapper.spotify.models.Track;

import play.db.ebean.Model;

@Entity
public class Festival extends Model{

	@Id
	//TicketMaster's id for this given Festival
	public static String id;
	
	//TicketMaster's official name for this Festival
	public String name;
	public static String eventName;
	public static Api api;
	
	//TicketMaster's list of artists performing at the festival
	public static ArrayList<Artist> artists;
	
	public static URL url;
	
	public Festival(String n){
		name = new String(n);
		setAPI();
		urlParse();	
		getAuthorization();
		addToPlaylist();
		System.out.println(eventName);
	}
	
	public ArrayList<Artist> getArtists(){
		return artists;
	}
	
	//Spotify
	public void setAPI(){
		final String clientId = "e61890b23e6d46eeb90fc9818cbe4c29";
		final String clientSecret = "6929417ed59e4e6a91cdb02bea8222d1";
		final String redirectURI = "http://localhost:9000/callback.html";
		api = Api.builder()
				  .clientId(clientId)
				  .clientSecret(clientSecret)
				  .redirectURI(redirectURI)
				  .build();
	}
	
	
	private void getAuthorization(){
		/* Application details necessary to get an access token */
		final String code = "AQCSqSgk7FOw8tLmraZdAaZhMb-udJDHs1mkYPvXQxM75oy7skPEQcSYawvcf7isWzwFQA21t0U71mRiFiFKrQHHI-NIargrLQK9wcCWlaXBj9ZWGHDbe9KkH92RMzWc7U161k7T9CL5f0UlpHUQEsmbOfCa3oYMI0qHIt7sGuRHAvBTNGRFI3588sgWRD2Mc-brqBTfc9t-Cv5U7lR_9OuNWmc7gkeZsjcobaGcZjAt";

		/* Make a token request. Asynchronous requests are made with the .getAsync method and synchronous requests
		 * are made with the .get method. This holds for all type of requests. */
		final SettableFuture<AuthorizationCodeCredentials> authorizationCodeCredentialsFuture = api.authorizationCodeGrant(code).build().getAsync();

		/* Add callbacks to handle success and failure */
		Futures.addCallback(authorizationCodeCredentialsFuture, new FutureCallback<AuthorizationCodeCredentials>() {
		  @Override
		  public void onSuccess(AuthorizationCodeCredentials authorizationCodeCredentials) {
		    /* The tokens were retrieved successfully! */
		    System.out.println("Successfully retrieved an access token! " + authorizationCodeCredentials.getAccessToken());
		    System.out.println("The access token expires in " + authorizationCodeCredentials.getExpiresIn() + " seconds");
		    System.out.println("Luckily, I can refresh it using this refresh token! " +     authorizationCodeCredentials.getRefreshToken());

		    /* Set the access token and refresh token so that they are used whenever needed */
		    api.setAccessToken(authorizationCodeCredentials.getAccessToken());
		    api.setRefreshToken(authorizationCodeCredentials.getRefreshToken());
		  }

		  @Override
		  public void onFailure(Throwable throwable) {
		    /* Let's say that the client id is invalid, or the code has been used more than once,
		     * the request will fail. Why it fails is written in the throwable's message. */

		  }
		});
	}
		
	private void addToPlaylist(){
		final PlaylistCreationRequest request = api.createPlaylist("annekao", eventName).publicAccess(true).build();
		try{
			final Playlist playlist = request.get();
			String pID = playlist.getId();
			for (Artist a : artists){
				System.out.println(a.name);
				final List<String> tracksToAdd = Arrays.asList("spotify:track:"+a.getTop().get(0).getId(), "spotify:track:"+a.getTop().get(1).getId());
				
				final int insertIndex = 3;
				final AddTrackToPlaylistRequest plRequest = api.addTracksToPlaylist("annekao", pID, tracksToAdd).position(insertIndex).build();
			
				plRequest.get();
			}
		} catch (Exception e){
			e.printStackTrace();
			System.out.println("Error adding to playlist");
		}
	}
	
	//TICKETMASTER JSON PARSING
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
			
			parseJSON(jsonReply);
			
		} catch (Exception e){
			e.printStackTrace();
		}	
	}
	
	private void parseJSON(String jsonString){
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
