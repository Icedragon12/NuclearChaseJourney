/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.dragon_and_owl;

import com.jme3.app.SimpleApplication;
import com.jme3.renderer.RenderManager;

/**
 * test
 * @author normenhansen
 */
public class Main extends SimpleApplication {
    private String ipAddress;
    private int port;

    public Main(String ipAddress, int port) {
        this.ipAddress = ipAddress;
        this.port = port;
    }
    
    public static void main(String[] args) {
        if(args.length != 2) {
            System.out.println("Usage: ddjo <ipAddress> <port>");
            return;
        }
        
        Main app = new Main(args[0], Integer.parseInt(args[1]));
        app.setPauseOnLostFocus(false);
        app.start();
    }

    @Override
    public void simpleInitApp() {
        stateManager.attach(new LoginAppState(ipAddress, port));
//        stateManager.attach(new NoNetworkAppState());
    }

    @Override
    public void simpleUpdate(float tpf) {
        //TODO: add update code
    }

    @Override
    public void simpleRender(RenderManager rm) {
        //TODO: add render code
    }
}
