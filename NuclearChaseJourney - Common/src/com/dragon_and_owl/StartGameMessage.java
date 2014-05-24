
package com.dragon_and_owl;

import com.jme3.network.AbstractMessage;
import com.jme3.network.serializing.Serializable;

/**
 *
 * @author David
 */
@Serializable
public class StartGameMessage extends AbstractMessage {
    private boolean start = true;
    public StartGameMessage() {
    }
}
