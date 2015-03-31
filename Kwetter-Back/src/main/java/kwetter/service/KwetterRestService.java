/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.service;

import java.util.Collection;
import kwetter.domain.User;

import java.util.List;
import java.util.Properties;
import javax.batch.operations.JobOperator;
import javax.batch.runtime.BatchRuntime;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MultivaluedMap;
import javax.ws.rs.core.UriInfo;
import kwetter.domain.Trend;
import kwetter.domain.Tweet;

/**
 *
 * @author Nick
 */
@Path("/kwetter")
public class KwetterRestService {
    @Inject
    KwetterService kwetterService;
    
    @GET
    @Path("batch")
    @Produces("application/json")
    public Long batch(){ 
        kwetterService.initUsers();
        JobOperator jo = BatchRuntime.getJobOperator();
        long jid = jo.start("tweetsJob", new Properties());
        return jid;
    }
    
    @GET
    @Path("users")
    @Produces("application/json")
    public List<User> getUsers(){  
        JobOperator jo = BatchRuntime.getJobOperator();
        long jid = jo.start("tweetsJob", new Properties());
        return kwetterService.findAll();
    }
    
    @GET
    @Path("tweets")
    @Produces("application/json")
    public Collection<Tweet> getAllTweets(){
        return kwetterService.findAllTweets();
    }

    @GET
    @Path("users/random")
    @Produces("application/json")
    public User getRandomUser(){
        return kwetterService.getRandomUser();
    }
    
    @GET
    @Path("users/byUsername/{username}")
    @Produces("application/json")
    public User getUser(@PathParam("username") String username){
        return kwetterService.find(username);
    }
    
    @GET
    @Path("users/byId/{userid}")
    @Produces("application/json")
    public User getUser(@PathParam("userid") Long userId){
        return kwetterService.find(userId);
    }
    
    @GET
    @Path("users/{userid}/followers")
    @Produces("application/json")
    public Collection<User> getFollowers(@PathParam("userid") Long userid){
        return kwetterService.getFollowers(userid);
    }
    
    @GET
    @Path("users/{userid}/following")
    @Produces("application/json")
    public Collection<User> getFollowing(@PathParam("userid") Long userid){
        return kwetterService.getFollowing(userid);
    }
    
    @GET
    @Path("tweets/mentions/{username}")
    @Produces("application/json")
    public Collection<Tweet> getMenstions(@PathParam("username") String username){
        return kwetterService.getMentions(username);
    } 
    
    @GET
    @Path("tweets/timeline/{userid}")
    @Produces("application/json")
    public Collection<Tweet> getTimeLine(@PathParam("userid") Long userid){
        return kwetterService.getTimeLine(userid);
    }
    
    @GET
    @Path("tweets/trends")
    @Produces("application/json")
    public Collection<Trend> getTrends(){
        return kwetterService.getTrends();
    }
    
    @GET
    @Path("tweets/{userid}")
    @Produces("application/json")
    public Collection<Tweet> getTweets(@PathParam("userid") Long userId){
        return kwetterService.findTweets(userId);
    }    
    
    @POST
    @Path("tweets/add/{username}")
    @Consumes("application/json")    
    @Produces("text/plain")
    public String addTweet(@Context UriInfo ui, @PathParam("username")String username){
        MultivaluedMap<String, String> map = ui.getQueryParameters();
        String tweet = map.get("tweet").get(0);
        return kwetterService.addTweet(username, tweet);
    }    
}
