package renderEngine;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.*;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;

/**
 *
 * @author Enrico
 */
public class Loader {
    
    private List<Integer> vaos = new ArrayList<>();
    private List<Integer> vbos = new ArrayList<>();
    
    public RawModel loadToVAO(float[] positions, int[] indices){
        int id = createVAO();
        bindIndicesBuffer(indices);
        storeDataInAttributeList(0,positions);
        int vertexCount = indices.length;
        unbindVAO();
        
        return new RawModel(id, vertexCount);
    }
    
    private int createVAO(){
        int vaoID = GL30.glGenVertexArrays();
        vaos.add(vaoID);
        GL30.glBindVertexArray(vaoID);
        return vaoID;
    }
    
    private void storeDataInAttributeList(int attributeId, float[] data){
        int vboID = GL15.glGenBuffers();
        vbos.add(vboID);
        GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboID);
        FloatBuffer buffer = toFloatBuffer(data);
        GL15.glBufferData(GL15.GL_ARRAY_BUFFER, buffer, GL15.GL_STATIC_DRAW);
        GL20.glVertexAttribPointer(attributeId, 3, GL11.GL_FLOAT, false, 0, 0);
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
