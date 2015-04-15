/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package kwettermonitor;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.JMSException;
import javax.naming.NamingException;

/**
 *
 * @author Memet
 */
public class KwetterMonitor {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            new MonitorUI().setVisible(true);
        } catch (NamingException | JMSException ex) {
            Logger.getLogger(KwetterMonitor.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
