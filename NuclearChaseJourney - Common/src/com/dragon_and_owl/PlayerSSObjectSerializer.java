/*
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package com.dragon_and_owl;

import com.dragon_and_owl.netowl.common.snapshot.DefaultSnapShotObject;
import com.jme3.network.serializing.Serializer;
import java.io.IOException;
import java.nio.ByteBuffer;

/**
 *
 * @author Stefan Zaufl
 */
public class PlayerSSObjectSerializer extends Serializer{

    @Override
    public <T> T readObject(ByteBuffer data, Class<T> c) throws IOException {
        if(!PlayerSSObject.class.equals(c)) return null;
        DefaultSnapShotObject dsso = Serializer.getSerializer(DefaultSnapShotObject.class).readObject(data, DefaultSnapShotObject.class);
        
        PlayerSSObject psso = new PlayerSSObject(dsso.getId(), dsso.getPosition());
        psso.setRotation(dsso.getRotation());
        psso.setFloor(data.get());
        
        return (T)psso;
    }

    @Override
    public void writeObject(ByteBuffer buffer, Object object) throws IOException {
        PlayerSSObject psso = (PlayerSSObject)object;
        Serializer.getSerializer(DefaultSnapShotObject.class).writeObject(buffer, psso);
        buffer.put(psso.getFloor());
    }
    
}
