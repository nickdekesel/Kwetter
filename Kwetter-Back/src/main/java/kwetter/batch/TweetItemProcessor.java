/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.batch;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.batch.api.chunk.ItemProcessor;
import javax.inject.Inject;
import javax.inject.Named;
import kwetter.dao.UserDAO;
import kwetter.dao.UserDOA_JPAQualifier;
import kwetter.domain.Tweet;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *
 * @author Nick
 */
@Named
public class TweetItemProcessor implements ItemProcessor {

    @Inject
    @UserDOA_JPAQualifier
    private UserDAO userDAO;
    
    @Override
    public Tweet processItem(Object t) throws JSONException, ParseException {
        System.out.println("processItem: " + t);
        String jsonString = t.toString();
        
        if(jsonString.contains("{\"screenName\":")){
            int startIndex = jsonString.indexOf("{\"screenName\":");
            int endIndex = jsonString.lastIndexOf("}");
            JSONObject obj = new JSONObject(jsonString.substring(startIndex, endIndex+1));
                       
            String username = obj.getString("screenName");
            String text = obj.getString("tweet");
            String postedFrom = obj.getString("postedFrom");
            DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            Date postDate = format.parse(obj.getString("postDate"));
            System.out.println(postDate);
            
            Tweet tweet = new Tweet(text, postDate, postedFrom);
            tweet.setUser(userDAO.find(username));
            return tweet;
        }
        return null;       
    }
}