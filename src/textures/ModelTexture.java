package textures;

/**
 * Stores information on the texture of a model. The texture will give the base
 * colour of a model, to this it is necessary to add any reflection of the
 * light. The latter depends on the reflectivity (how much light is reflected as
 * opposed to diffused) and the dampening, or scattering, of such reflection.
 *
 * @author Enrico
 */
public class ModelTexture {

    private int textureID;
    /**
     * How much the reflection is diffused by.
     */
    private float shineDamper = 1;
    /**
     * This corresponds to the albedo of reflections from the texture.
     */
    private float reflectivity = 0;

    /**
     *
     * @param textureID The id of the texture for the model.
     */
    public ModelTexture(int textureID) {
        this.textureID = textureID;
    }

    public int getTextureID() {
        return textureID;
    }

    public float getShineDamper() {
        return shineDamper;
    }

    /**
     *
     * @param shineDamper Should be a positive real number
     */
    public void setShineDamper(float shineDamper) {
        this.shineDamper = shineDamper;
    }

    public float getReflectivity() {
        return reflectivity;
    }

    /**
     *
     * @param reflectivity Should be a float between 0 and 1
     */
    public void setReflectivity(float reflectivity) {
        this.reflectivity = reflectivity;
    }
}
