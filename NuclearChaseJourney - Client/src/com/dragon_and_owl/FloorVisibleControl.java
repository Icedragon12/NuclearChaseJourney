/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.dragon_and_owl;

import com.jme3.export.InputCapsule;
import com.jme3.export.JmeExporter;
import com.jme3.export.JmeImporter;
import com.jme3.export.OutputCapsule;
import com.jme3.material.MatParam;
import com.jme3.material.RenderState;
import com.jme3.math.ColorRGBA;
import com.jme3.renderer.RenderManager;
import com.jme3.renderer.ViewPort;
import com.jme3.renderer.queue.RenderQueue;
import com.jme3.scene.Geometry;
import com.jme3.scene.Spatial;
import com.jme3.scene.control.AbstractControl;
import com.jme3.scene.control.Control;
import java.io.IOException;

/**
 *
 * @author Stefan Zaufl
 */
public class FloorVisibleControl extends AbstractControl {
    private GameInfo gameInfo;
    private int floor;
    private int lastFloor = -1;
    private boolean isInvisible = false;

    @Override
    protected void controlUpdate(float tpf) {
        if(gameInfo.playerNode != null && lastFloor != gameInfo.playerNode.getFloor()) {
            if(gameInfo.playerNode.getFloor() < floor && !isInvisible) {
                //Make invisible
                setInvisibility(true);
            } else if(gameInfo.playerNode.getFloor() >= floor && isInvisible){
                //Make visible
                setInvisibility(false);
            }
            
            lastFloor = gameInfo.playerNode.getFloor();
        }
    }
    
    private void setInvisibility(boolean invisible) {
        Geometry geo = (Geometry)spatial;
                
        MatParam param = geo.getMaterial().getParam("Diffuse");
        ColorRGBA c;

        if(param != null) {
            c = (ColorRGBA)param.getValue();
        } else {
            c = ColorRGBA.White;
        }

        if(geo.getMaterial().getMaterialDef().getMaterialParam("Diffuse") != null) {
            geo.getMaterial().setColor("Diffuse", new ColorRGBA(c.r, c.b, c.g, invisible ? 0.3f : 1f));
        } else if(geo.getMaterial().getMaterialDef().getMaterialParam("Color") != null) {
            geo.getMaterial().setColor("Color", new ColorRGBA(c.r, c.b, c.g, invisible ? 0.3f : 1f));
        }

        if(invisible) {
            geo.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Alpha);
            geo.setQueueBucket(RenderQueue.Bucket.Transparent);
        } else {
            geo.getMaterial().getAdditionalRenderState().setBlendMode(RenderState.BlendMode.Off);
            geo.setQueueBucket(RenderQueue.Bucket.Opaque);
        }
                
        isInvisible = invisible;
    }
    
    @Override
    protected void controlRender(RenderManager rm, ViewPort vp) {}
    
    @Override
    public Control cloneForSpatial(Spatial spatial) {
        FloorVisibleControl control = new FloorVisibleControl();
        control.floor = floor;
        control.gameInfo = gameInfo;
        control.isInvisible = isInvisible;
        control.lastFloor = lastFloor;
        return control;
    }
    
    @Override
    public void read(JmeImporter im) throws IOException {
        super.read(im);
        InputCapsule in = im.getCapsule(this);
        this.floor = in.readInt("floor", -1);
    }
    
    @Override
    public void write(JmeExporter ex) throws IOException {
        super.write(ex);
        OutputCapsule out = ex.getCapsule(this);
        out.write(floor, "floor", -1);
    }

    public GameInfo getGameInfo() {
        return gameInfo;
    }

    public void setGameInfo(GameInfo gameInfo) {
        this.gameInfo = gameInfo;
    }

    public int getFloor() {
        return floor;
    }

    public void setFloor(int floor) {
        this.floor = floor;
    }
}
