/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dragon_and_owl;

import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.util.TangentBinormalGenerator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class NoNetworkAppState extends AbstractAppState {

    private SimpleApplication simpleApp;
    private AssetManager assetManager;
    private Node rootNode;

    @Override
    public void initialize(AppStateManager stateManager, Application app) {
        super.initialize(stateManager, app);
        simpleApp = (SimpleApplication) app;
        assetManager = simpleApp.getAssetManager();
        rootNode = simpleApp.getRootNode();

        //Setup scene
        Logger.getLogger("com.jme3.util").setLevel(Level.SEVERE);
        simpleApp.getFlyByCamera().setMoveSpeed(5);

        Spatial s = assetManager.loadModel("Textures/CC_Low_Poly_Building_1.j3o");
        simpleApp.getViewPort().setBackgroundColor(new ColorRGBA(0.543f, 0.684f, 1, 1));

        TangentBinormalGenerator.generate(s);
        rootNode.attachChild(s);

        setUpLight();
        //TODO: initialize your AppState, e.g. attach spatials to rootNode
        //this is called on the OpenGL thread after the AppState has been attached
    }

    private void setUpLight() {
        // We add light so we see the scene
        AmbientLight al = new AmbientLight();
        al.setColor(ColorRGBA.White.mult(0.1f));
        rootNode.addLight(al);

        DirectionalLight dl = new DirectionalLight();
        dl.setColor(ColorRGBA.White);
        dl.setDirection(new Vector3f(2.8f, -2.8f, -2.8f).normalizeLocal());
        rootNode.addLight(dl);
    }

    @Override
    public void update(float tpf) {
        //TODO: implement behavior during runtime
    }

    @Override
    public void cleanup() {
        super.cleanup();
        //TODO: clean up what you initialized in the initialize method,
        //e.g. remove all spatials from rootNode
        //this is called on the OpenGL thread after the AppState has been detached
    }
}
