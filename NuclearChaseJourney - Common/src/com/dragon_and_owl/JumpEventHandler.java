
package com.dragon_and_owl;

import com.dragon_and_owl.netowl.common.input.InputEventDescriptor;
import com.dragon_and_owl.netowl.common.input.InputEventHandler;
import static com.dragon_and_owl.netowl.common.input.InputEventHandler.REJECT;
import com.dragon_and_owl.netowl.common.snapshot.SSOId;
import com.dragon_and_owl.netowl.common.snapshot.SnapShot;
import com.dragon_and_owl.netowl.common.snapshot.SnapShotObject;
import com.jme3.math.Vector3f;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author David
 */
public class JumpEventHandler implements InputEventHandler {

    @Override
    public Set<SnapShotObject> handleEvent(InputEventDescriptor eventDescriptor, SnapShot snapShot, float time) {
        final PlayerSSObject player = (PlayerSSObject)snapShot.getSnapShotObjects().get(new SSOId(eventDescriptor.getOriginId()));
        
        if(player == null) {
            return REJECT;
        }
        
        JumpEvent jumpEvent = (JumpEvent)eventDescriptor.getInputEvent();
        
        if(jumpEvent.isUp()) {
            player.setFloor((byte)(player.getFloor() + 1));
        }
        else {
            player.setFloor((byte)(player.getFloor() - 1));
        }
        
        player.getPosition().y = player.getFloor() * 3.5f;
        
        Set<SnapShotObject> changedObjects = new HashSet<>(1);
        changedObjects.add(player);
        
        return changedObjects;
    }

    @Override
    public int[] getTypes() {
        return new int[] {2};
    }
    
}
