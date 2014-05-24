
package com.dragon_and_owl;

import com.dragon_and_owl.netowl.common.input.AbstractInputEvent;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author David
 */
@Serializable
public class JumpEvent extends AbstractInputEvent {
    private boolean up;

    public JumpEvent() {
        super(2);
    }
    
    public JumpEvent(boolean up) {
        super(2);
        this.up = up;
    }

    public boolean isUp() {
        return up;
    }
}
