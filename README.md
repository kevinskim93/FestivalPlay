FestivalPlay
============

FestivalPlay is a Java based web application using the Play framework. FestivalPlay generates playlists with a list of popular tracks given a set of artists who will be performing at a given festival. For example, provided the input "Coachella", the application will output a playlist of the top 3 songs of all the artists playing at Coachella. The user's queries will generate an entire playlist (typically anywhere from 1 hour to 4 hours long) of songs related to events they may be interested in attending!

###Website and Details
1. Website
	* FestivalPlay will be available online soon, currently working on web hosting
2. ChallengePost
	* Here is the link to our ChallengePost submission, which also contains a gallery of images
	* http://challengepost.com/software/festivalplay

###Code Location
1. Java Back-End
	* All Java back-end code can be found in app/models/
	* These classes essentially contain the search queries and API calls
2. HTML and Javascript Front-End
	* Front-end UI and Spotify Authentication can be found in app/views/

###Data Extraction

1. TicketMaster API
	* The application will begin by querying Ticketmaster API for the list of artists who will be performing at a given event
	* This query will generate the list of artists we will feed into the Spotify API

2. Spotify API
	* Given a list of artist objects, we will parse through their songs, and add their top 3 most played tracks into a playlist object
	* The playlist object will then be POSTed to the user's spotify account

3. Play Framework
	* The Play framework will support our application's GET and POST requests

4. Web Application
	* Through the FestivalPlay application, users will be prompted to login to their Spotify account
	* This will retreive a client code to access the user's account permissions
	* Users will input the name of their festival into the input box
	* A playlist will be generated and added directly to their spotify account

###Goals of the Application

1. Promote lesser-known artists to event-goers who are interested in listening to songs from artists they have never heard
2. Raise hype for events that are coming in the near future
	* Customers who are unsure of their decision can get excited after listening to great songs
3. Promote Ticketmaster as a ticket-source to event hosters due to increased awareness of their festivals
4. Encourage more people to enjoy great music!

###Future expansion
1. Input a "buy ticket" link for the festival
	* Track which artist the user bought the ticket on to evaluate each artist's performance
2. Incorporate other music applications, such as Soundcloud
	* Soundcloud supports more unsigned artists