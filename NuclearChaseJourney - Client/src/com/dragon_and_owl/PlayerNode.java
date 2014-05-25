/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.dragon_and_owl;

import com.jme3.scene.Node;

/**
 *
 * @author Stefan Zaufl
 */
public class PlayerNode extends Node {
    private int floor = 0;

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
