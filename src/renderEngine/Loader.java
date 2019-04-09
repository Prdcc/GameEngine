package renderEngine;

import models.RawModel;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureLoader;

/**
 * Manages loading of RawModels and access to texture loading.
 *
 * @author Enrico
 */
public class Loader {

    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();

    /**
     * Loads a model to a Vertex Array Object. A VAO is basically an array of
     * size 16, each line is a VBO, an array which contains one information
     * about a vertex. These are organised in groups of 2 (eg texture
     * coordinates) or 3 (eg vertex coordinates). The order in every line for
     * each vertex should be the same. the indices array then associates every
     * vertex to any triangle in which it may lay.
     *
     * @param positions The positions of the vertices
     * @param textureCoords The UV coordinates of the vertices
     * @param normals Normal vector at each vertex
     * @param indices Index of each vertex for every distinct triangle
     * @return The RawModel containing a reference to the id of the VAO that has
     * been created
     */
    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices) {
        int id = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0, 3, positions);
        storeDataInAttributeList(1, 2, textureCoords);
        storeDataInAttributeList(2, 3, normals);
        int vertexCount = indices.length;
        unbindVAO();

        return new RawModel(id, vertexCount);
    }

    /**
     * Loads a texture from memory and adds its id to the list of all loaded
     * textures. This uses the
     * {@link org.newdawn.slick.opengl.TextureLoader TexureLoader} API to load
     * the texture to memory.
     *
     * @param fileName The name of the texture as is saved in memory
     * @return The id of the texture
     */
    public int loadTexture(String fileName) {
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("png", new FileInputStream("res/textures/" + fileName + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, "Texture file not found", ex);
        }

        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }

    private int createVAO() {
        //Create the VAO
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }

    /**
     * Creates a VBO from floating point data. A VBO requires the data to be
     * saved as a buffer rather than an array. The id of the VBO is also saved
     * so that it can be deleted on program termination.
     *
     * @param attributeId The id of the attribute (0-15) in which the data
     * should be stored
     * @param size Number of data points per vertex
     * @param data The data to be stored
     */
    private void storeDataInAttributeList(int attributeId, int size, float[] data) {
        //Create the VBO
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);   //associates the VBO id to a certain data type
        FloatBuffer buffer = toFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeId, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);         //unbind buffer (unbind given by 0)
    }

    private void unbindVAO() {
        GL30.glBindVertexArray(0);      //0 = unbind
    }

    /**
     * Destroys any texture, VAO or VBO which has been loaded, called on program
     * termination.
     */
    public void cleanUp() {
        vaos.stream().forEach((vao) -> {
            GL30.glDeleteVertexArrays(vao);
        });

        vbos.stream().forEach((vbo) -> {
            GL15.glDeleteBuffers(vbo);
        });

        textures.stream().forEach((texture) -> {
            GL11.glDeleteTextures(texture);
        });
    }

    /**
     * Similar to storeDataInAttributeList
     *
     * @param indices
     */
    private void bindIndicesBuffer(int[] indices) {
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = toIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
    }

    /**
     * Converts a float array to a float-buffer in read-mode. A float-buffer is
     * an array with a pointer to the current entry. Read-mode means that the
     * pointer is set to the first entry.
     *
     * @param data The array to be converted
     * @return The float buffer.
     */
    private FloatBuffer toFloatBuffer(float[] data) {
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();  //Sets the pointer to the first entry.

        return buffer;
    }

    /**
     * Converts an int array to an int-buffer in read-mode. An int-buffer is an
     * array with a pointer to the current entry. Read-mode means that the
     * pointer is set to the first entry.
     *
     * @param data The array to be converted
     * @return The float buffer.
     */
    private IntBuffer toIntBuffer(int[] data) {
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();  //Sets the pointer to the first entry.

        return buffer;
    }

}
