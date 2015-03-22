Step 45. JAXB Classes were generated based on xsd downloaded from http://europa.eu/rapid/conf/RSS20.xsd. The classes were added to
package us.jblog.aggregator.rss.
STEP 46. Create Service to convert the downloaded entitites into items.
Refer the feeds listed below for test data-
tomcat exper - http://www.tomcatexpert.com/blog/feed
eclipse - http://eclipsesource.com/blogs/author/irbull/feed/
javavids - http://feeds.feedburner.com/javavids?format=xml
javaworld core java (more here - http://www.javaworld.com/about/rss/) - http://www.javaworld.com/category/core-java/index.rss

##############
How to create war file to deploy on some other server?
Right click on project > run as > maven build > goal:package

#######deploying
download heroku toolbelt and install
On cmd prompt, type heroku login and provide heroku account credentials.
navigate to directory where <app>.war exists
download heroku deploy plugin using 
	heroku plugins:install https://github.com/heroku/heroku-deploy
type - 
heroku deploy:war --war <app>.war --app thetopblogs

##for restarting application
heroku restart --app thetopblogs
