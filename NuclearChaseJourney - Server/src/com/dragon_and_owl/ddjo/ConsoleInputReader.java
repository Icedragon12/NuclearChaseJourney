/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */
package com.dragon_and_owl.ddjo;

import com.dragon_and_owl.netowl.common.util.NullArgumentException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Scanner;

/**
 *
 * @author David Portisch 
 */
public class ConsoleInputReader {
    private final Collection<ConsoleEventListener> listeners;
    private Thread consoleReaderThread = null;
    
    public ConsoleInputReader() {
        listeners = new ArrayList<>();
    }
    
    public void start() {
        consoleReaderThread = new Thread(new Runnable() {
            @Override
            public void run() {
                Scanner sc = new Scanner(System.in);
                
                System.out.println("Press <Enter> to quit.");
                sc.nextLine();

                synchronized(listeners) {
                    for(ConsoleEventListener listener : listeners) {
                        listener.onQuitEvent();
                    }
                }
            }
        });
        
        consoleReaderThread.start();
    }
    
    public boolean addConsoleEventListener(ConsoleEventListener listener) {
        if(listener == null) {
            throw new NullArgumentException("listener");
        }
        
        synchronized(listeners) {
            return listeners.add(listener);
        }
    }
    
    public boolean removeConsoleEventListener(ConsoleEventListener listener) {
        if(listener == null) {
            throw new NullArgumentException("listener");
        }
        
        synchronized(listeners) {
            return listeners.remove(listener);
        }
    }
}
