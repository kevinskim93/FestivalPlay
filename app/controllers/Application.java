package controllers;

import java.util.ArrayList;

import models.Festival;
import models.Artist;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {
	public static Festival f;
	public static ArrayList<Artist> artists = new ArrayList<Artist>();
    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result callback(){
    	
    	return ok(callback.render("Authenticated"));
    }
    public static Result getFestival(){
    	Festival festival = Form.form(Festival.class).bindFromRequest().get();
    	f = new Festival(festival.name);
    	artists = f.getArtists();
    	// this return statement refreshes the page
    	//TODO: change that so it will display the playlist
    	String client_id = "e61890b23e6d46eeb90fc9818cbe4c29";
    	String redirect_uri = "http://localhost:9000/callback.html";
    	String url = "https://accounts.spotify.com/authorize?client_id=" + client_id +
    		"&response_type=code" +
    		"&scope=playlist-modify-public" +
    		"&redirect_uri=" +  redirect_uri;
        return redirect(url);
    }
}


