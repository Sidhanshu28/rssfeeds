package com.mytask.rssfeeds.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.context.ContextLoader;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.mytask.rssfeeds.model.Feeds;
import com.mytask.rssfeeds.dao.FeedsDAO;

@Controller

public class FeedsController {
	
	 @Autowired
	private FeedsDAO feedsDao;
	 
	 /** object to get web context loader */
	 WebApplicationContext ctx = ContextLoader.getCurrentWebApplicationContext();
	 
	 
	 /**calling save function to post values provided by user to the database  and redirects to /viewFeeds */ 
	@RequestMapping(value ="/save",method = RequestMethod.POST)
	public String savingFeed(@Valid Feeds feeds,
			BindingResult result, ModelMap model,RedirectAttributes redirectAttributes) throws Exception {
		feedsDao.save(feeds);		
		return "redirect:/viewFeeds";
	}
	
	/** calling function to get list of feeds to display them on viewfeeds model */
	@RequestMapping("/viewFeeds")  
    public ModelAndView viewfeeds(ModelMap model){  
        List<Feeds> list=feedsDao.getAllFeeds();
        Feeds feed = new Feeds();
		model.addAttribute("feeds", feed);
        return new ModelAndView("viewfeeds","list",list);  
    } 
	
	
	/** calling function to get details of particular feed by id */
	@RequestMapping("/viewFeed/{id}")  
    public ModelAndView viewfeedById(ModelMap model,@PathVariable int id){  
        List<Feeds> list=feedsDao.getFeedById(id);
        return new ModelAndView("viewfeedById","list",list);  
    } 


	 
	/** It deletes record for the given id  and redirects to /viewFeeds  */
	    @RequestMapping(value="/deletefeed/{id}",method = RequestMethod.GET)  
	    public ModelAndView delete(@PathVariable int id){  
	    	feedsDao.delete(id);  
	        return new ModelAndView("redirect:/viewFeeds");  
	    }  
	     
	

}
