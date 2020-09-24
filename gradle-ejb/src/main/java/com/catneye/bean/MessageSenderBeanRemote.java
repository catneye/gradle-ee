/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.catneye.bean;

import javax.ejb.Remote;

/**
 *
 * @author plintus
 */
@Remote
public interface MessageSenderBeanRemote {
    /**
     * Send simple text message to jms queue.
     * @param msg This message text.  
     */
    public void sendTextMessage(String msg);
}
