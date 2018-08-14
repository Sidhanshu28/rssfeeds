# RSS Feeds

Basic java spring framework - mvc application - using mySql as database. This app allows to add new RSS feeds giving name and link of feed as input, parsing the xml data retrieved from link provided as user input, and storing it in database. You can view the details of particular RSS feed. Also you can delete any feed.

## Getting Started

Download zip of this repository, extract and open in any java editor ( preferably Eclipse), do a maven build and start server. (Default runs on http://localhost:8080/rssfeeds/viewFeeds)

### Prerequisites

You will need to have following installed :

```
Java 1.8
MySql - version 5.6 +
Spring MVC Framework - version 4.0 +
Eclipse ( prefer latest version )
Maven
Apache tomcat - version 8.5 +
```

### Installing

You can follow these steps to get it running and live on your local server.

- Install java and set the env paths ( you can see : https://www.guru99.com/install-java.html )
- Intsall tomcat and set paths ( you can see : http://www.ntu.edu.sg/home/ehchua/programming/howto/tomcat_howto.html )
- Download and install mysql (remember the password you set at start) ( you can see https://www.sitepoint.com/how-to-install-mysql/ )
- Download zip from here and extract the folder
- open the project folder in eclipse and do a maven build (as it is maven project)
- Setup tomcat server within eclipse and start it running
- Your web application will be live at localhost:8080/rssfeeds/viewFeeds

### Solving errors

If you see 'ContextLoaderException'
- Go to project properties
- Select Deployment assembly
- Click on add
- Click on 'java build path entries'
- Click on 'click on maven dependecy'
- Click 'apply and close'

If you see 'could not find manifest.mf file error'
- Right click on project
- Select 'maven'
- Select 'update project'

If you see database errors
- Check whether mysql server is running or not
- Check for path, username and password of database in spring-servlet.xml file, you might have to change them ( depends upon your database )
- Check mysql root user have permissions or not.

## Built With

* [Spring MVC Framework](https://docs.spring.io/spring-framework/docs/3.2.x/spring-framework-reference/html/mvc.html) - The web framework used
* [Maven](https://maven.apache.org/) - Dependency Management
* [Eclipse](https://www.eclipse.org/) - IDE used
* [Tomcat](http://tomcat.apache.org/) - Deployment platform


## Authors

* **Sidhanshu Monga** - *Initial work* - [Sidhanshu Monga](https://github.com/sidhanshu28)


## References

* Javatpoint
* Youtube videos
* stackoverflow
