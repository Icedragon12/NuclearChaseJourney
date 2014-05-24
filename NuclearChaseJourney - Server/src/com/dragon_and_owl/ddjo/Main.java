package com.dragon_and_owl.ddjo;

import com.dragon_and_owl.DDJOCommonsConfig;
import com.dragon_and_owl.DDJOLogicFactory;
import com.dragon_and_owl.StartGameMessage;
import com.dragon_and_owl.netowl.common.network.serializers.NetOwlSerializerRegister;
import com.dragon_and_owl.netowl.server.DefaultNetOwlServerConfig;
import com.dragon_and_owl.netowl.server.NetOwlServer;
import com.dragon_and_owl.netowl.server.NetOwlServerConfig;
import com.jme3.network.ConnectionListener;
import com.jme3.network.HostedConnection;
import com.jme3.network.Network;
import com.jme3.network.Server;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * test
 * @author normenhansen
 */
public class Main implements ConsoleEventListener, ConnectionListener {
    
    private Server gameServer = null;
    private NetOwlServer netOwlServer;
    private DDJOCommonsConfig commonsConfig;
    
    private int port;
    private boolean run = true;
    private static final Logger logger = Logger.getLogger(Main.class.getName());
    private final Lock lock = new ReentrantLock();
    
    private int neededPlayers = 2;
    private int connectedPlayers = 0;

    public Main(int port) {
        this.port = port;
        
        
    }
    
    public static void main(String[] args) {
        if(args.length != 1) {
            System.out.println("Usage: DDJOServer <port>");
            return;
        }
        
        int port;
        try {
            port = Integer.parseInt(args[0]);
        }
        catch(NumberFormatException fex) {
            System.out.println("Usage: DDJOServer <port>");
            return;
        }
        
        Main main = new Main(port);
        
        if(!main.init()) {
            System.out.println("Initialization failed");
        }
        else {
            main.run();
        }
    }
    
    public boolean init() {
        try {
            System.out.println("Starting server...");
            commonsConfig = new DDJOCommonsConfig();
            
            Serializer.registerClass(StartGameMessage.class);
            NetOwlSerializerRegister.registerClasses(commonsConfig);
            
            
            gameServer = Network.createServer("DDJO", 0, port, port);
            gameServer.start();
            
            System.out.println("Spidermonkey started...");
            
            //Add connection handler
            ConnectHandler conHandler = new ConnectHandler();
            gameServer.addConnectionListener(conHandler);
            gameServer.addConnectionListener(this);
            
            
            //Start ConsoleReader for quitting
            ConsoleInputReader cir = new ConsoleInputReader();
            cir.addConsoleEventListener(this);

            System.out.println("Server started.");
            cir.start();
        } catch (IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
            
            if(gameServer.isRunning()) {
                gameServer.close();
            }
            
            return false;
        }
        
        return true;
    }

    public void run() {
        waitForPlayers();
        
        //Init server
        gameServer.broadcast(new StartGameMessage());
        //Initialize NetOwl
        NetOwlServerConfig serverConfig = new DefaultNetOwlServerConfig();
        serverConfig.setNetOwlCommonsConfig(commonsConfig);
        serverConfig.addNetOwlLogicFactory(new DDJOLogicFactory());
        netOwlServer = new NetOwlServer(serverConfig, new DDJOInitializer(gameServer), gameServer);
        
        
        
        long currentTimeStamp = System.currentTimeMillis();
        while(run) {
            long newTimeStamp = System.currentTimeMillis();
            netOwlServer.update((newTimeStamp - currentTimeStamp)/1000f);
            currentTimeStamp = newTimeStamp;
        }
        
        if(gameServer.isRunning()) {
            gameServer.close();
        }
    }
    
    private void waitForPlayers() {
        System.out.println("Waiting for players to connect...");
        System.out.println(neededPlayers + " players needed to start game!");
        
        while(run) {
            lock.lock();
            if(connectedPlayers == neededPlayers) {
                break;
            }
            lock.unlock();
        }
    }

    @Override
    public void connectionAdded(Server server, HostedConnection conn) {
        lock.lock();
        try {
            if(connectedPlayers >= neededPlayers) {
                System.out.println("Sorry, server full! ->Rejecting player " + conn.getAddress() + "with Id "
                        + conn.getId());
                conn.close("Sorry, server full! Maybe next round!");
                return;
            }
            connectedPlayers++;
            System.out.println(connectedPlayers + " players connected. " + (neededPlayers - connectedPlayers)
                    + " more to go.");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void connectionRemoved(Server server, HostedConnection conn) {
        lock.lock();
        try {
            connectedPlayers--;
            System.out.println(connectedPlayers + " players connected. " + (neededPlayers - connectedPlayers)
                    + " more to go.");
        } finally {
            lock.unlock();
        }
    }

    @Override
    public void onQuitEvent() {
        run = false;
    }
}
