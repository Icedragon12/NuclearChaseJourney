/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.dragon_and_owl;

import com.dragon_and_owl.netowl.common.util.NullArgumentException;
import com.jme3.math.Vector3f;
import com.jme3.renderer.Camera;
import com.jme3.scene.Spatial;

/**
 *
 * @author Stefan Zaufl
 */
public class FocusedCameraControl {
    private Spatial target;
    private Camera cam;

    /**
     * 
     * @param cam the camera to control, never null
     */
    public FocusedCameraControl(Camera cam) {
        if(cam == null) throw new NullArgumentException("cam");
        
        this.cam = cam;
    }
    
    protected void controlUpdate(float tpf) {
        if(target != null) {
            Vector3f targetPosition = target.getWorldTranslation();
            Vector3f cameraLoctaion = new Vector3f(targetPosition.x, targetPosition.y + 30, targetPosition.z + 30);
            cam.setLocation(cameraLoctaion);
            
        }
    }
    
    public Spatial getTarget() {
        return target;
    }

    public void setTarget(Spatial target) {
        this.target = target;
        
        if(target != null) {
            Vector3f targetPosition = target.getWorldTranslation();
            Vector3f cameraLoctaion = new Vector3f(targetPosition.x, targetPosition.y + 20, targetPosition.z + 20);
            cam.setLocation(cameraLoctaion);
            
            cam.lookAt(targetPosition, Vector3f.UNIT_Y);
        }
    }
}
