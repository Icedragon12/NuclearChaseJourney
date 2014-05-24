/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dragon_and_owl;

import com.dragon_and_owl.netowl.client.DefaultNOCC;
import com.dragon_and_owl.netowl.client.NetOwlClient;
import com.dragon_and_owl.netowl.client.NetOwlClientConfig;
import com.dragon_and_owl.netowl.client.controller.NetOwlSpatialController;
import com.dragon_and_owl.netowl.client.debug.NetOwlClientDebuggerAppState;
import com.dragon_and_owl.netowl.client.input.InputEventContainer;
import com.dragon_and_owl.netowl.client.snapshot.DeltaSnapShot;
import com.dragon_and_owl.netowl.common.input.Direction;
import com.dragon_and_owl.netowl.common.input.MoveEvent;
import com.dragon_and_owl.netowl.common.snapshot.SSOId;
import com.dragon_and_owl.netowl.common.snapshot.SnapShot;
import com.dragon_and_owl.netowl.common.snapshot.SnapShotObject;
import com.jme3.app.Application;
import com.jme3.app.SimpleApplication;
import com.jme3.app.state.AbstractAppState;
import com.jme3.app.state.AppStateManager;
import com.jme3.asset.AssetManager;
import com.jme3.input.KeyInput;
import com.jme3.input.controls.ActionListener;
import com.jme3.input.controls.AnalogListener;
import com.jme3.input.controls.KeyTrigger;
import com.jme3.light.AmbientLight;
import com.jme3.light.DirectionalLight;
import com.jme3.material.Material;
import com.jme3.math.ColorRGBA;
import com.jme3.math.Vector3f;
import com.jme3.network.Client;
import com.jme3.scene.Geometry;
import com.jme3.scene.Node;
import com.jme3.scene.Spatial;
import com.jme3.scene.shape.Box;
import com.jme3.util.TangentBinormalGenerator;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author David
 */
public class GameAppState extends AbstractAppState {

    private DDJOCommonsConfig commonsConfig;
    private NetOwlClient netOwlClient;
    private Client client;
    private InputEventContainer eventContainer;
    private NetOwlClientDebuggerAppState debugAppState = new NetOwlClientDebuggerAppState();
    
    
    private SimpleApplication simpleApp;
    private AssetManager assetManager;
    private Node rootNode;
    private static Box b = new Box(0.5f, 0.5f, 0.5f);
    
    private boolean firstRun = true;

    public GameAppState(DDJOCommonsConfig commonsConfig, Client client) {
        this.commonsConfig = commonsConfig;
        this.client = client;
    }

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

        //Setup Netowl
        NetOwlClientConfig clientConfig = new DefaultNOCC();
        clientConfig.addNetOwlLogicFactory(new DDJOLogicFactory());
        clientConfig.setNetOwlCommonsConfig(commonsConfig);

        netOwlClient = new NetOwlClient(client, clientConfig);



        eventContainer = netOwlClient.getInputEventContainer();

        //Add the inputMappings
        simpleApp.getInputManager().addMapping("PLAYER_LEFT", new KeyTrigger(KeyInput.KEY_J));
        simpleApp.getInputManager().addMapping("PLAYER_RIGHT", new KeyTrigger(KeyInput.KEY_L));
        simpleApp.getInputManager().addMapping("PLAYER_FRONT", new KeyTrigger(KeyInput.KEY_I));
        simpleApp.getInputManager().addMapping("PLAYER_BACK", new KeyTrigger(KeyInput.KEY_K));
        simpleApp.getInputManager().addMapping("PLAYER_UP", new KeyTrigger(KeyInput.KEY_U));
        simpleApp.getInputManager().addMapping("PLAYER_DOWN", new KeyTrigger(KeyInput.KEY_O));
        simpleApp.getInputManager().addMapping("SHOW_DEBUG", new KeyTrigger(KeyInput.KEY_F12));
        
        simpleApp.getInputManager().addListener(analogListener, new String[]{"PLAYER_LEFT", "PLAYER_RIGHT", "PLAYER_FRONT",
            "PLAYER_BACK"});
        simpleApp.getInputManager().addListener(showNetDebugListener, new String[]{"SHOW_DEBUG"});
        simpleApp.getInputManager().addListener(jumpListener, new String[]{"PLAYER_UP", "PLAYER_DOWN"});
        
        netOwlClient.attachNetOwlDebugger(debugAppState);
//        stateManager.attach(debugAppState);
    }

    @Override
    public void update(float tpf) {
        SnapShot s = netOwlClient.calculateNextSnapShot();
//        if(s != null) {
//            System.out.println("Num Objects: " + s.getSnapShotObjects().size());;
//        }
        if(s != null && firstRun) {
            for(SnapShotObject sso : s.getSnapShotObjects().values()) {
                if(sso.getType() == 2) {
                    addPlayer(sso);
                }
            }
            firstRun = false;
        }

        DeltaSnapShot deltaSnapShot = netOwlClient.getDeltaSnapShot();

        if (deltaSnapShot != null) {


            for (SnapShotObject sso : deltaSnapShot.getAddedObjects()) {
                switch (sso.getType()) {
                    case 2:
                        addPlayer(sso);
                }
            }
        }
    }

    private void addPlayer(SnapShotObject sso) {
        Material blueMat = new Material(assetManager, "Common/MatDefs/Misc/Unshaded.j3md");
        blueMat.setColor("Color", ColorRGBA.Blue);

        Geometry geom = new Geometry("player" + sso.getId().objectId, b);
        geom.setMaterial(blueMat);
        rootNode.attachChild(geom);
        //Add the controllers for the players
        NetOwlSpatialController netOwlController = new NetOwlSpatialController(false, false);

        geom.addControl(netOwlController);
        netOwlClient.registerController(sso.getId(), netOwlController);
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
    
    private AnalogListener analogListener = new AnalogListener() {
        @Override
        public void onAnalog(String name, float keyPressed, float tpf) {
            switch (name) {
                case "PLAYER_LEFT":
                    eventContainer.addUniqueInputEvent(new MoveEvent(1, Direction.LEFT, 1, new SSOId(client.getId())));
                    break;
                case "PLAYER_RIGHT":
                    eventContainer.addUniqueInputEvent(new MoveEvent(1, Direction.RIGHT, 1, new SSOId(client.getId())));
                    break;
                case "PLAYER_FRONT":
                    eventContainer.addUniqueInputEvent(new MoveEvent(1, Direction.FRONT, 1, new SSOId(client.getId())));
                    break;
                case "PLAYER_BACK":
                    eventContainer.addUniqueInputEvent(new MoveEvent(1, Direction.BACK, 1, new SSOId(client.getId())));
            }
        }
    };
    
    private ActionListener showNetDebugListener = new ActionListener() {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("SHOW_DEBUG") && !isPressed) {
                if(simpleApp.getStateManager().hasState(debugAppState)) {
                    simpleApp.getStateManager().detach(debugAppState);
                }
                else {
                    simpleApp.getStateManager().attach(debugAppState);
                }
            }
        }
    };
    
    private ActionListener jumpListener = new ActionListener() {

        @Override
        public void onAction(String name, boolean isPressed, float tpf) {
            if(name.equals("PLAYER_UP") && !isPressed) {
                eventContainer.addUniqueInputEvent(new JumpEvent(true));
            }
            else if(name.equals("PLAYER_DOWN") && !isPressed) {
                eventContainer.addInputEvent(new JumpEvent(false));
            }
        }
    };

    @Override
    public void cleanup() {
        super.cleanup();
        if (client != null) {
            if (client.isConnected()) {
                client.close();
            }
        }
    }
}
