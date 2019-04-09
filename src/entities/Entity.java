package entities;

import models.TexturedModel;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Enrico
 */
public class Entity {
    private TexturedModel model;
    private Vector3f position;
    private float rotX, rotY, rotZ;
    private float scale;

    /**
     *
     * @param model
     * @param position
     * @param rotX
     * @param rotY
     * @param rotZ
     * @param scale
     */
    public Entity(TexturedModel model, Vector3f position, float rotX, float rotY, float rotZ, float scale) {
        this.model = model;
        this.position = position;
        this.rotX = rotX;
        this.rotY = rotY;
        this.rotZ = rotZ;
        this.scale = scale;
    }
    
    /**
     *
     * @param dx
     * @param dy
     * @param dz
     */
    public void increasePosition(float dx, float dy, float dz){
        this.position.x += dx;
        this.position.y += dy;
        this.position.z += dz;
    }
    
    /**
     *
     * @param dx
     * @param dy
     * @param dz
     */
    public void increaseRotation(float dx, float dy, float dz){
        this.rotX += dx;
        this.rotY += dy;
        this.rotZ += dz;
    }

    /**
     *
     * @return
     */
    public TexturedModel getModel() {
        return model;
    }

    /**
     *
     * @param model
     */
    public void setModel(TexturedModel model) {
        this.model = model;
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
     * @param position
     */
    public void setPosition(Vector3f position) {
        this.position = position;
    }

    /**
     *
     * @return
     */
    public float getRotX() {
        return rotX;
    }

    /**
     *
     * @param rotX
     */
    public void setRotX(float rotX) {
        this.rotX = rotX;
    }

    /**
     *
     * @return
     */
    public float getRotY() {
        return rotY;
    }

    /**
     *
     * @param rotY
     */
    public void setRotY(float rotY) {
        this.rotY = rotY;
    }

    /**
     *
     * @return
     */
    public float getRotZ() {
        return rotZ;
    }

    /**
     *
     * @param rotZ
     */
    public void setRotZ(float rotZ) {
        this.rotZ = rotZ;
    }

    /**
     *
     * @return
     */
    public float getScale() {
        return scale;
    }

    /**
     *
     * @param scale
     */
    public void setScale(float scale) {
        this.scale = scale;
    }
    
    
}
