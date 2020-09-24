/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.bean;

import com.catneye.util.Constants;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.naming.InitialContext;
import javax.naming.NamingException;

/**
 *
 * @author plintus
 */
@Stateless
public class MessageSenderBean implements MessageSenderBeanRemote {

    /**
     * Send simple text message to jms queue.
     * @param msg This message text.  
     */
    @Override
    public void sendTextMessage(String msg) {
        try {
            InitialContext ic = new InitialContext();
            ConnectionFactory connectionFactory = (ConnectionFactory) ic.lookup("ConnectionFactory");

            Destination destination = (Destination) ic.lookup(Constants.QUEUE_NAME);
            Connection connection = connectionFactory.createConnection();
            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
            MessageProducer producer = session.createProducer(destination);
            ObjectMessage message = session.createObjectMessage();
            message.setObject(msg);
            producer.send(message);
            session.close();
            connection.close();
        } catch (JMSException ex) {
            Logger.getLogger(MessageSenderBean.class.getName()).log(Level.SEVERE, "JMSException {0}", ex);
        } catch (NamingException ex) {
            Logger.getLogger(MessageSenderBean.class.getName()).log(Level.SEVERE, "NamingException {0}", ex);
        }
    }
}
