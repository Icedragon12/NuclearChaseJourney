/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.dragon_and_owl;

import com.dragon_and_owl.netowl.common.network.serializers.NetOwlSerializerRegister;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.network.Client;
import com.jme3.network.Message;
import com.jme3.network.MessageListener;
import com.jme3.network.Network;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class LoginAppState extends AbstractAppState implements MessageListener<Client> {
    private Client client = null;
    private String ipAddress;
    private int port;
    
    private boolean stateSwitch = false;
    
    private DDJOCommonsConfig commonsConfig;
    
    private AppStateManager stateManager;

    public LoginAppState(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        this.stateManager = stateManager;
        commonsConfig = new DDJOCommonsConfig();
            
        Serializer.registerClass(StartGameMessage.class);
        NetOwlSerializerRegister.registerClasses(commonsConfig);
        try {
            client = Network.connectToServer("DDJO", 0, ipAddress, port);
        } catch (IOException ex) {
            Logger.getLogger(LoginAppState.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        client.addMessageListener(this, StartGameMessage.class);
        client.start();
        
        ((SimpleApplication)app).getFlyByCamera().setDragToRotate(true);
    }
    
    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }
    
    @Override
    public void cleanup() {
        super.cleanup();
        client.removeMessageListener(this);
        
        if(!stateSwitch && client != null && client.isConnected()) {
            client.close();
        }
    }

    @Override
    public void messageReceived(Client source, Message m) {
        if(m instanceof StartGameMessage) {
            stateSwitch = true;
            stateManager.detach(this);
            stateManager.attach(new GameAppState(commonsConfig, client));
        }
    }
}
