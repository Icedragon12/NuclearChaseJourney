/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.dragon_and_owl.ddjo;

import com.dragon_and_owl.PlayerSSObject;
import com.dragon_and_owl.netowl.common.snapshot.SSOId;
import com.dragon_and_owl.netowl.common.snapshot.SnapShot;
import com.dragon_and_owl.netowl.common.snapshot.SnapShotObject;
import com.dragon_and_owl.netowl.common.util.NullArgumentException;
import com.dragon_and_owl.netowl.server.snapshot.InitialSnapShotCreator;
import com.jme3.math.Vector3f;
import com.jme3.network.HostedConnection;
import com.jme3.network.Server;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author David Portisch 
 */
public class DDJOInitializer implements InitialSnapShotCreator {
    private Collection<SnapShotObject> initialSSOs = new ArrayList<>();
    
    
    public DDJOInitializer(Server server) {
        if(server == null) {
            throw new NullArgumentException("server");
        }
        if(!server.isRunning()) {
            throw new IllegalArgumentException("Given server is not running");
        }
        
        for(HostedConnection con : server.getConnections()) {
            PlayerSSObject player = new PlayerSSObject(new SSOId(con.getId()), new Vector3f());
            player.setOwnerId(con.getId());
            initialSSOs.add(player);
        }
    }
    
    
    @Override
    public void createInitialSnapShot(SnapShot snapShot) {
        
        for(SnapShotObject sso : initialSSOs) {
            snapShot.addSnapShotObject(sso);
        }
    }
    
}
