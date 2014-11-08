FestivalPlay
============

FestivalPlay is a Java based web application using the Play framework. FestivalPlay generates playlists with a list of popular tracks given a set of artists who will be performing at a given festival. For example, provided the input "Coachella", the application will output a playlist of the top 5 songs of all the artists playing at Coachella.

###Data Extractions
+ TicketMaster API
++ The application will begin by querying Ticketmaster API for the list of artists who will be performing at a given event
++ This query will generate the list of artists we will feed into the Spotify API

+ Spotify API
++ Given a list of artist objects, we will parse through their songs, and add their top 5 most played tracks into a playlist object
++ The playlist object will then be POSTed to the user's spotify account

