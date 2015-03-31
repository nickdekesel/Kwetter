/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.batch;

import java.util.List;
import javax.batch.api.chunk.AbstractItemWriter;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import kwetter.domain.Tweet;

/**
 *
 * @author Nick
 */
@Named
public class TweetItemWriter extends AbstractItemWriter {

    @PersistenceContext
    EntityManager em;

    @Override
    public void writeItems(List list) {
        System.out.println("writeItems: " + list);
        for (Object tweet : list) {
            if(tweet instanceof Tweet){
                em.persist((Tweet)tweet);           
            }           
        }
    }
}
