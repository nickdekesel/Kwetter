/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package kwetter.batch;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.batch.api.chunk.AbstractItemReader;
import javax.inject.Named;

/**
 *
 * @author Nick
 */
@Named
public class TweetItemReader extends AbstractItemReader {

    private BufferedReader reader;

    @Override
    public void open(Serializable checkpoint) throws Exception {
        reader = new BufferedReader(
                new InputStreamReader(
                this
                .getClass()
                .getClassLoader()
                .getResourceAsStream("/META-INF/tweets-input.json")));
    }

    @Override
    public String readItem() {
        try {
            return reader.readLine();
        } catch (IOException ex) {
            Logger.getLogger(TweetItemReader.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}