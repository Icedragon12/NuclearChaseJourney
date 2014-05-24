/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.dragon_and_owl;

import com.dragon_and_owl.netowl.common.snapshot.DefaultSnapShotObject;
import com.dragon_and_owl.netowl.common.snapshot.SSOId;
import com.dragon_and_owl.netowl.common.snapshot.SnapShotObject;
import com.dragon_and_owl.netowl.common.util.UniversalMixer;
import com.jme3.math.Vector3f;

/**
 *
 * @author Stefan Zaufl
 */
public class PlayerSSObject extends DefaultSnapShotObject {
    private byte  floor = 0;
    
    public PlayerSSObject(SSOId ssoId, Vector3f position) {
        super(2, ssoId);
        setPosition(position);
    }

    @Override
    public SnapShotObject clone() {
        PlayerSSObject clone = new PlayerSSObject(getId(), getPosition());
        clone.setRotation(getRotation());
        clone.setFloor(floor);
        
        return clone;
    }

    @Override
    public void mix(SnapShotObject predecessor, float mixFactor, SnapShotObject target) {
        super.mix(predecessor, mixFactor, target);
        PlayerSSObject predecessorPlayer = (PlayerSSObject)predecessor;
        PlayerSSObject targetPlayer = (PlayerSSObject)target;
        targetPlayer.setFloor((byte)UniversalMixer.mix(predecessorPlayer.floor, floor, mixFactor));
    }

    public byte getFloor() {
        return floor;
    }

    public void setFloor(byte floor) {
        this.floor = floor;
    }
}
