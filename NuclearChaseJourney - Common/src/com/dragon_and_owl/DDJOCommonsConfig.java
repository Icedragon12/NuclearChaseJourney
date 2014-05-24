
package com.dragon_and_owl;

import com.dragon_and_owl.netowl.common.NetOwlCommonsConfig;
import com.dragon_and_owl.netowl.common.snapshot.DefaultSnapShot;
import com.dragon_and_owl.netowl.common.snapshot.SnapShot;
import com.jme3.network.serializing.Serializer;

/**
 *
 * @author David Portisch 
 */
public class DDJOCommonsConfig implements NetOwlCommonsConfig {

    @Override
    public SnapShot createSnapShot(long id, long serverTimeStamp) {
        return new DefaultSnapShot(id, serverTimeStamp);
    }

    @Override
    public void registerNetClasses() {
        Serializer.registerClass(PlayerSSObject.class, new PlayerSSObjectSerializer());
        Serializer.registerClass(JumpEvent.class);
//        Serializer.registerClass(FireEvent.class);
//        Serializer.registerClass(ShotObject.class, new ShotObjectSerializer());
//        Serializer.registerClass(CubePlayerSSObject.class, new CubePlayerSSObjectSerializer());
    }
    
}
