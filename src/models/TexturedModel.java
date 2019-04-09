package models;

import textures.ModelTexture;

/**
 * Contains the information on the texture and underlying vertices of an entity.
 * @author Enrico
 */
public class TexturedModel {
    private RawModel rawModel;
    private ModelTexture texture;

    /**
     * Constructs a textured model with the specified raw model and texture.
     * @param rawModel
     * @param texture
     */
    public TexturedModel(RawModel rawModel, ModelTexture texture) {
        this.rawModel = rawModel;
        this.texture = texture;
    }

    public RawModel getRawModel() {
        return rawModel;
    }

    public ModelTexture getTexture() {
        return texture;
    }
    
}
