/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.dragon_and_owl;

import com.dragon_and_owl.netowl.client.controller.NetOwlSpatialController;
import com.dragon_and_owl.netowl.common.snapshot.SnapShot;
import com.dragon_and_owl.netowl.common.snapshot.SnapShotObject;

/**
 *
 * @author Stefan Zaufl
 */
public class NetOwlPlayerController extends NetOwlSpatialController {

    public NetOwlPlayerController() {
        super(false, false);
    }

    @Override
    public void update(SnapShotObject sso, SnapShot snapShot) {
        super.update(sso, snapShot);
        
        if(sso.getType() == 2 && spatial instanceof PlayerNode) {
            PlayerNode playerNode = (PlayerNode)spatial;
            PlayerSSObject playerObject = (PlayerSSObject)sso;
            playerNode.setFloor(playerObject.getFloor());
        }
    }
    
    
    
}
