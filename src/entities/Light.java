package entities;

import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Enrico
 */
public class Light {
    private Vector3f position;
    private Vector3f colour;

    /**
     *
     * @param position
     * @param colour
     */
    public Light(Vector3f position, Vector3f colour) {
        this.position = position;
        this.colour = colour;
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
    public Vector3f getColour() {
        return colour;
    }

    /**
     *
     * @param colour
     */
    public void setColour(Vector3f colour) {
        this.colour = colour;
    }
    
}
