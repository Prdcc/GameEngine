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
 *
 * @author Enrico
 */
public class Loader {
    
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    private List<Integer> textures = new ArrayList<>();
    
    public RawModel loadToVAO(float[] positions, float[] textureCoords, float[] normals, int[] indices){
        int id = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,3,positions);
        storeDataInAttributeList(1,2,textureCoords);
        storeDataInAttributeList(2,3,normals);
        int vertexCount = indices.length;
        unbindVAO();
        
        return new RawModel(id, vertexCount);
    }
    
    public int loadTexture(String fileName){
        Texture texture = null;
        try {
            texture = TextureLoader.getTexture("png", new FileInputStream("res/textures/"+fileName+".png"));
        } catch (IOException ex) {
            Logger.getLogger(Loader.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        int textureID = texture.getTextureID();
        textures.add(textureID);
        return textureID;
    }
    
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }
    
    private void storeDataInAttributeList(int attributeId, int size, float[] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = toFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeId, size, GL11.GL_FLOAT, false, 0, 0);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);         //unbind buffer (unbind given by 0)
        
    }
    
    private void unbindVAO(){
        GL30.glBindVertexArray(0);      //0 = unbind
    }
    
    
    public void cleanUp(){
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
    
    private void bindIndicesBuffer(int[] indices){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ELEMENT_ARRAY_BUFFER, vboID);
        IntBuffer buffer = toIntBuffer(indices);
        GL15.glBufferData(GL15.GL_ELEMENT_ARRAY_BUFFER,buffer, GL15.GL_STATIC_DRAW);
    }
    
    private FloatBuffer toFloatBuffer(float[] data){
        FloatBuffer buffer = BufferUtils.createFloatBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        
        return buffer;
    }
    
    private IntBuffer toIntBuffer(int[] data){
        IntBuffer buffer = BufferUtils.createIntBuffer(data.length);
        buffer.put(data);
        buffer.flip();
        
        return buffer;
    }
    
}
