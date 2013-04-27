PlayersNext
===========

Instructions for building/running PlayersNext web app
-------------

Ensure a JDK is installed, I’ve been using Java 6.  
You don’t need to have Scala installed, Play will download the needed scala jars

Install MongoDB
Install mongodb on your machine, ensure mongodb is running and “mongo” is able to connect

Install Play Webframework 2.1.0
---------------------------------
> Download Play 2.1.0: http://www.playframework.com/download
> 
> Unzip play-2.1.0.zip to ~
> Add ~/play-2.1.0 to your path
> EXPORT PATH=${PATH}:~/play-2.1.0
---------------------------------

Build/Run PlayersNext from github
---------------------------------
> git clone https://github.com/jwmatthews/PlayersNext
> cd PlayersNext
> play compile
> play run
> Point web browser to 127.0.0.1:9000
---------------------------------

To access ember.js work in progress point browser to:
http://127.0.0.1:9000/assets/index.html



Note on HTML Templates
---------------------------------
Two types of HTML templates exist
 - Server side HTML templates rendered by Play Webframework
 - Javascript HTML templates rendered by Handlebars as part of Ember.js

We have a generic main.scala.html server side template which loads the needed javascript files and does nothing else.
All HTML for the webui is rendered client side by Handlebars.
Look in the app/assets/templates to see the client side handlebar templates.

