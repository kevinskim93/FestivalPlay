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
	
	//After acquiring JSON from TicketMaster find top 5 tracks from
	//Spotify and add to each Artist for the festival
	public Map< Artist, ArrayList<String> > artistTopFive;
	
	public void generatePlaylist(){
		//for each artist a in artistTopFive call a.getTopFive() and add to map
	}
}
