/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.dragon_and_owl;

import com.dragon_and_owl.netowl.common.NetOwlLogicFactory;
import com.dragon_and_owl.netowl.common.input.InputEventHandler;
import com.dragon_and_owl.netowl.common.snapshot.SnapShotObjectHandler;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author David Portisch
 */
public class DDJOLogicFactory implements NetOwlLogicFactory{

    @Override
    public Set<InputEventHandler> getInputEventHandlers() {
        Set<InputEventHandler> handlers = new HashSet<>();
        
        handlers.add(new MoveEventHandler());
        handlers.add(new JumpEventHandler());
        
        return handlers;
    }

    @Override
    public Set<SnapShotObjectHandler> getSnapShotObjectHandlers() {
        Set<SnapShotObjectHandler> handlers = new HashSet<>();
        
        
        return handlers;
    }
    
}
