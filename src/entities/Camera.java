/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import org.lwjgl.input.Keyboard;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Enrico
 */
public class Camera {
    private Vector3f position = new Vector3f(0,0,0);
    private float pitch;
    private float yaw;
    private float roll;
    
    private static final float MOVEMENT_SPEED = 2f;
    private static final float ROTATION_SPEED = 0.5f;
    
    /**
     *
     */
    public void move(){
        if(Keyboard.isKeyDown(Keyboard.KEY_W)){
            position.z -= MOVEMENT_SPEED;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_D)){
            position.x += MOVEMENT_SPEED;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_A)){
            position.x -= MOVEMENT_SPEED;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_S)){
            position.z += MOVEMENT_SPEED;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_SPACE)){
            position.y += MOVEMENT_SPEED/2;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_LSHIFT)){
            position.y -= MOVEMENT_SPEED/2;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_Q)){
            yaw -= ROTATION_SPEED;
        }
        
        if(Keyboard.isKeyDown(Keyboard.KEY_E)){
            yaw += ROTATION_SPEED;
        }
    }
    
    /**
     *
     * @return
     */
    public Vector3f getPosition() {
        return position;
    }

    /**
     *
     * @return
     */
    public float getPitch() {
        return pitch;
    }

    /**
     *
     * @return
     */
    public float getYaw() {
        return yaw;
    }

    /**
     *
     * @return
     */
    public float getRoll() {
        return roll;
    }
    
    
}
