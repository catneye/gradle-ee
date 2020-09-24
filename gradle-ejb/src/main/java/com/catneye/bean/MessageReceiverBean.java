/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.bean;

import com.catneye.util.Constants;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

/**
 *
 * @author plintus
 */
@MessageDriven(mappedName = "jms/Queue", activationConfig = {
    @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
    @ActivationConfigProperty(propertyName = "destination", propertyValue = Constants.QUEUE_NAME),
    @ActivationConfigProperty(propertyName = "acknowledgeMode", propertyValue = "Auto-acknowledge")
})
public class MessageReceiverBean implements MessageListener {

    @Resource
    private MessageDrivenContext mdc;

    /**
     * Receive text message from jms queue.
     * @param msg contains message. 
     */
    @Override
    public void onMessage(Message msg) {
        if (msg != null) {
            try {
                if (msg instanceof TextMessage) {
                    TextMessage textMsg = (TextMessage) msg;
                    Logger.getLogger(MessageReceiverBean.class.getName()).log(Level.INFO, "Message received: {0}", textMsg);
                } else {
                    Logger.getLogger(MessageReceiverBean.class.getName()).log(Level.INFO, "Message received type: {0}", msg.getClass().getName());
                }
            } catch (Throwable e) {
                Logger.getLogger(MessageReceiverBean.class.getName()).log(Level.SEVERE, "Throwable exception: {0}", e);
            }
        }
    }

}
