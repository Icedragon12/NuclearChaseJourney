/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.dragon_and_owl;

import com.dragon_and_owl.netowl.common.input.InputEventDescriptor;
import com.dragon_and_owl.netowl.common.input.InputEventHandler;
import com.dragon_and_owl.netowl.common.input.MoveEvent;
import com.dragon_and_owl.netowl.common.snapshot.SSOId;
import com.dragon_and_owl.netowl.common.snapshot.SnapShot;
import com.dragon_and_owl.netowl.common.snapshot.SnapShotObject;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author David Portisch
 */
public class MoveEventHandler implements InputEventHandler{

    @Override
    public Set<SnapShotObject> handleEvent(InputEventDescriptor eventDescriptor, SnapShot snapShot, float time) {
        Collection<SSOId> targetIds = eventDescriptor.getInputEvent().getTargetIds();
        if(targetIds == null || targetIds.size() != 1) {
            return null;
        }
        
        SSOId targetId = targetIds.iterator().next();
        
        if(targetId.objectId != eventDescriptor.getOriginId()) {
            return null;
        }
        
        SnapShotObject target = snapShot.getSnapShotObjects().get(targetId);
        
        if(target == null) {
            return Collections.EMPTY_SET;
        }
        
        MoveEvent moveEvent = (MoveEvent)eventDescriptor.getInputEvent();
        Set<SnapShotObject> modifiedObjects = new HashSet<>(1);
        
        switch(moveEvent.getDirection()) {
            case FRONT:
                target.getPosition().z -= time * 3;
                break;
            case BACK:
                target.getPosition().z += time * 3;
                break;
            case LEFT:
                target.getPosition().x -= time * 3;
                break;
            case RIGHT:
                target.getPosition().x += time * 3;
                break;
            default:
                return null;
        }
        modifiedObjects.add(target);
        
        return modifiedObjects;
    }

    @Override
    public int[] getTypes() {
        return new int[] {1};
    }
    
}
