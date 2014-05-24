/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
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
