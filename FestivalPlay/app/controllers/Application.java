package controllers;

import models.Festival;
import play.*;
import play.data.Form;
import play.mvc.*;
import views.html.*;

public class Application extends Controller {

    public static Result index() {
        return ok(index.render("Your new application is ready."));
    }

    public static Result getFestival(){
    	Festival festival = Form.form(Festival.class).bindFromRequest().get();
    	Festival f = new Festival(festival.name);
    	// this return statement refreshes the page
    	//TODO: change that so it will display the playlist
    	return redirect(routes.Application.index());
    }
}
