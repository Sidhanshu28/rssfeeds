package com.mytask.rssfeeds.dao;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.URL;
import java.net.URLConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.time.format.DateTimeFormatter;  
import java.time.LocalDateTime;
import com.mytask.rssfeeds.model.Feeds;


public class FeedsDAO  {
	
	
	JdbcTemplate template;  
	 
	
	/** setter function for JDBC connection template
	returns null */
	public void setTemplate(JdbcTemplate template) {  
	    this.template = template;  
	    getAllFeeds();
	}  
	
	
	/** save function to save values in feeds table
	returns null */
	public void save(Feeds f) throws Exception {

		f.setLastUpdated(getTodayDate()); /** getting latest date to mark as last updated date */
		f.setFeedTitle(getStringFromXML(f.getFeedUrl(),"title")); /** calling function to get title field from XML (parsing) */
		
		String sql="INSERT INTO feeds(url,title,last_update,feed_name) values ('"+f.getFeedUrl()+"','"+f.getFeedTitle()+"','"+f.getLastUpdated()+"','"+f.getFeedName()+"')";
	     template.update(sql);
	     saveItem(f); /** calling function to store details of items in that XML after parsing it. */
	}
	
	
	/** saveItem function to parse XML and then getting its required attributes and then storing in database
	returns null */
	public void saveItem(Feeds f) throws Exception{
		String xmlstring = convertXMLtoString(f.getFeedUrl()); /** getting XML from URL as a string */
        Element element = getRootElementFromXML(xmlstring); /** converting XML string into a DOM element so as to read it's attributes. */
        pushAllItems(element,f); /** calling function to read attributes and push them to database in a loop */
	}
	
	
	/** getAllFeeds function to get all RSS feeds from database and binding the list in table
	returns list (array) */
	public List<Feeds> getAllFeeds() {
		
		String query = "select * from feeds";
		return template.query(query,new ResultSetExtractor<List<Feeds>>(){  
		    
		     public List<Feeds> extractData(ResultSet rs) throws SQLException,  
		            DataAccessException {  
		    	 
		    	List<Feeds> list=new ArrayList<Feeds>();  
		        while(rs.next()){ 
		        	/** using setters to set the values */
		        Feeds e=new Feeds();  
		        e.setFeedId(rs.getInt(1));  
		        e.setFeedUrl(rs.getString(2));
		        e.setFeedTitle(rs.getString(3));  
		        e.setLastUpdated(rs.getString(4));
		        e.setFeedName(rs.getString(5));
		        list.add(e);  
		        }  
		        return list;  /** returning list which will bind data to table through foreach in table rows */
		        }  
		    });  
		  
		  }
	
	
	
	/** getFeedById function to get details of particular feed by id
	returns list (array) */
	public List<Feeds> getFeedById(int id) {
		
		final int count = getItemsCount(id); /** calling function to get Articles count in that feed XML */
		
		/** this SQL string is taking feed with particular id from feeds table and items with the same foreign key in items table
		also ordering by latest published date (top 5) */
		
		
		String sql = "select  fs.title as feedTitle, fs.url as feedUrl,fs.last_update as feedLastUpdate, its.title as itemsTitle, its.link as itemsLink , its.published as Published from feeds as fs\n" + 
				"inner join items its on fs.id= its.feed_id\n" + 
				"where fs.id = "+ id + "\n"+
				"order by its.published DESC limit 5;";
		
		return template.query(sql,new ResultSetExtractor<List<Feeds>>(){  
		    
		     public List<Feeds> extractData(ResultSet rs) throws SQLException,  
		            DataAccessException {  
		    	 
		    	 List<Feeds> list=new ArrayList<Feeds>();  
		        while(rs.next()){  
		        Feeds e=new Feeds();  
		        e.setFeedName(rs.getString(1));  
		        e.setFeedUrl(rs.getString(2)); 
		        e.setLastUpdated(rs.getString(3));
		        e.setItemTitle(rs.getString(4));
		        e.setItemLink(rs.getString(5));
		        e.setFeedArticleCount(count);
		        list.add(e);  
		        }  
		        return list;  
		        }  
		    });  
		  
		  }
	
	
	
	/** getItemsCount function to get count of articles(items) for one RSS feed
	 returns integer */
	public int getItemsCount(int id) {
		String sqlForCount="select count(*) from items where feed_id = " + id + "";
		int count = template.queryForObject(sqlForCount,Integer.class);
		return count;
	}
	
	
	
	/** delete function to delete a feed by id
	returns null */
	public void delete(int id) {
		
		/** due to constraint of foreign key, first deleting items which contains that feed_id
		* and then deleting that feed */
		
		String sql1="delete from items where feed_id="+ id +"";
	    template.update(sql1);
	    String sql2 = "delete from feeds where id=" + id +"";
	    template.update(sql2);
	    
	    String sqlForCount="select count(*) from feeds";
		int count = template.queryForObject(sqlForCount,Integer.class);
	   
		/** query to set auto increment id field to start with 0 again */
		
		if(count==0) {
			String sqlForIdReset="ALTER TABLE feeds AUTO_INCREMENT = 0";  
		    template.update(sqlForIdReset);
		}
	}
	
	
	
	/** getStringFromXML function which deals with three functions
	* converting XML to String
	* getting root element from XML String
	* getting name through searching in attributes of that element
	* returns string */
	public String getStringFromXML(String url, String toSearch) throws Exception {	
		String xmlstring = convertXMLtoString(url); /** URL will be provided by user */
        Element element = getRootElementFromXML(xmlstring);
        return getName(toSearch, element);   /** toSearch is string which will be attribute and need to search */
	}
	
	
	
	/**  convertXMLToString function which converts XML to string and return string
	returns string */
	public String convertXMLtoString(String url) throws Exception {
		URL feedurl = new URL(url);/** making String URL to URL type. */
		URLConnection yc = feedurl.openConnection(); /** getting URL connection */
        BufferedReader in = new BufferedReader(new InputStreamReader(
               yc.getInputStream())); /** reading input stream */
        String inputLine;
        String str = "";
        while ((inputLine = in.readLine()) != null)
        str = str + inputLine; /** interpreting the stream as string */
        	in.close();
        return str; /** returning a string which needs to be convert in DOM element type. */
	}
	
	
	
	/** getRootElementFromXML is element type function, which return a DOM element.
	returns DOM element */
	public Element getRootElementFromXML(String xml) throws SAXException, IOException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder;
        Document doc;
        /** using try catch to deal with exceptions */
        try {
            builder = factory.newDocumentBuilder();
            doc =  builder.parse(new InputSource( new StringReader(xml))); /** parsing XML string */
            Element rootElement = doc.getDocumentElement();
            return  rootElement; /** returning DOM element */
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
            return null;
        }
	}
	
	
	
	/** getName function to get value of particular attribute your required to search.
	returns string */
	public String getName(String tagName, Element element) {
        NodeList list = element.getElementsByTagName(tagName);
        if (list != null && list.getLength() > 0) {
            NodeList subList = list.item(0).getChildNodes();

            if (subList != null && subList.getLength() > 0) {
                return subList.item(0).getNodeValue();
            }
        }
        return null;
    }
	
	
	
	/**  pushAllItems function which pushes the data of items in a loop, depending on how many tags are present in feed XML.
	returns null. */
	public void pushAllItems(Element doc, Feeds f) throws Exception {
		int id = getLastId(); /** function to get last inserted feed id so as to push as foreign key in items table */
		NodeList nle = doc.getElementsByTagName("channel"); /** making channel as nodeList root element here */
		NodeList nl  = nle.item(0).getChildNodes(); /** getting children of nodeList as another nodeList */
	    if (nl != null) { /** if nodeList is not null */
	        int length = nl.getLength();
	        for (int i = 0; i < length; i++) { /** looping through its children  */
	            if (nl.item(i).getNodeType() == Node.ELEMENT_NODE) {
	                Element el = (Element) nl.item(i);
	               
	                if (el.getNodeName().contains("item")) {/** if child element have tag of item */
	                	/** it will search for other required tags in child nodes of item */
	                	/** getting formatted format of date */
	                	String datestr =  el.getElementsByTagName("pubDate").item(0).getTextContent();
	                	f.setItemPublished(getDateStr(datestr));
	                    f.setItemTitle( el.getElementsByTagName("title").item(0).getTextContent() );
	                    f.setItemLink( el.getElementsByTagName("link").item(0).getTextContent() );
	                    f.setItemDescription( el.getElementsByTagName("description").item(0).getTextContent() );
	                    String sql="INSERT INTO items(feed_id,title,link,description,published) values ('"+id+"','"+f.getItemTitle()+"','"+f.getItemLink()+"','"+f.getItemDescription()+"','"+f.getItemPublished()+"')";
	           	        template.update(sql);
	                }
	            }
	        }
	    }
	}
	
	
	/**  getLastId to get last inserted feed id so as to push as foreign key in items table
	returns id */
	public int getLastId() {
		String sql="SELECT id FROM feeds WHERE ID = (SELECT MAX(ID) FROM feeds)";
		int id = template.queryForObject(sql,Integer.class);
		return id;
	}
	
	
	/** getDateStr function to get formatted date string
	returns string */
	public String getDateStr(String d) throws Exception {  
		DateFormat outputFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		DateFormat inputFormat = new SimpleDateFormat("EEE, d MMM yyyy HH:mm:ss Z");
		Date date = inputFormat.parse(d);
		String outputText = outputFormat.format(date);
		return outputText;
	}
	
	
	/**  getTodayDate function to get current data and time.
	returns string */
	public String getTodayDate() {
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");  
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	
}
