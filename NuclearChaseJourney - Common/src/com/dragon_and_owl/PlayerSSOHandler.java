/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.dragon_and_owl;

import com.dragon_and_owl.netowl.common.snapshot.SnapShot;
import com.dragon_and_owl.netowl.common.snapshot.SnapShotObject;
import com.dragon_and_owl.netowl.common.snapshot.SnapShotObjectHandler;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author Stefan Zaufl
 */
public class PlayerSSOHandler implements SnapShotObjectHandler {
    private static final int[] handledTypes = new int[]{2};

    @Override
    public Set<SnapShotObject> handleObject(SnapShot snapShot, SnapShotObject sso, float time) {
        Set<SnapShotObject> changedObjects = new HashSet<>(1);
        
        return changedObjects;
    }

    @Override
    public int[] getTypes() {
        return handledTypes;
    }

}
