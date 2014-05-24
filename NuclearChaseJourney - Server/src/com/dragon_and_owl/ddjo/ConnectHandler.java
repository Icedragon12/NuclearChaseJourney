
package com.dragon_and_owl.ddjo;

import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;

/**
 *
 * @author David Portisch 
 */
public class ConnectHandler implements ConnectionListener {
    
    @Override
    public void connectionAdded(Server server, HostedConnection conn) {
        System.out.println("New client connected: " + conn.getAddress() + " with ID " + conn.getId());
    }

    @Override
    public void connectionRemoved(Server server, HostedConnection conn) {
        System.out.println("Client disconnected: " + conn.getAddress() + " with ID " + conn.getId());
    }

}
