package renderEngine;

import entities.*;
import java.util.*;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.vector.Matrix4f;
import shaders.*;
import terrains.Terrain;

/**
 *
 * @author Enrico
 */
public class MasterRenderer {
    private static float FOV = 70;
    private static float NEAR_PLANE = 0.1f;
    private static float FAR_PLANE = 1000;
    
    private Matrix4f projectionMatrix;
    
    private StaticShader shader = new StaticShader();
    private EntityRenderer renderer;
    
    private TerrainRenderer terrainRenderer;
    private TerrainShader terrainShader= new TerrainShader();
    
    private Map<TexturedModel,List<Entity>> entities = new HashMap<>();
    private List<Terrain> terrains = new ArrayList<>();

    /**
     *
     */
    public MasterRenderer() {
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glCullFace(GL11.GL_BACK);
        createProjectionMatrix();
        renderer = new EntityRenderer(shader, projectionMatrix);
        terrainRenderer = new TerrainRenderer(terrainShader, projectionMatrix);
    }
    
    /**
     *
     * @param sun
     * @param camera
     */
    public void render(Light sun, Camera camera){
        prepare();
        
        shader.start();
        shader.loadLight(sun);
        shader.loadViewMatrix(camera);
        renderer.render(entities);
        shader.stop();
        
        terrainShader.start();
        terrainShader.loadLight(sun);
        terrainShader.loadViewMatrix(camera);
        terrainRenderer.render(terrains);
        terrainShader.stop();
        
        terrains.clear();        
        entities.clear();
    }
    
    /**
     *
     * @param terrain
     */
    public void processTerrain(Terrain terrain){
        terrains.add(terrain);
    }
    
    /**
     *
     * @param entity
     */
    public void processEntity(Entity entity){
        TexturedModel entityModel = entity.getModel();
        List<Entity> batch = entities.get(entityModel);
        if(batch != null){
            batch.add(entity);
        }
        else{
            List<Entity> newBatch = new ArrayList<>();
            newBatch.add(entity);
            entities.put(entityModel, newBatch);
        }
    }
    
    /**
     *
     */
    public void cleanUp(){
        shader.cleanUp();
        terrainShader.cleanUp();
    }
    
    /**
     *
     */
    public void prepare(){
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glClear(GL11.GL_COLOR_BUFFER_BIT|GL11.GL_DEPTH_BUFFER_BIT);
        GL11.glClearColor(0f, 0.75f, 1f, 1);      //red background
    }
    
    private void createProjectionMatrix(){
        float aspectRatio = (float) Display.getWidth() / (float) Display.getHeight();
        float y_scale = (float) ((1f / Math.tan(Math.toRadians(FOV / 2f))) * aspectRatio);
        float x_scale = y_scale / aspectRatio;
        float frustum_length = FAR_PLANE - NEAR_PLANE;
 
        projectionMatrix = new Matrix4f();
        projectionMatrix.m00 = x_scale;
        projectionMatrix.m11 = y_scale;
        projectionMatrix.m22 = -((FAR_PLANE + NEAR_PLANE) / frustum_length);
        projectionMatrix.m23 = -1;
        projectionMatrix.m32 = -((2 * NEAR_PLANE * FAR_PLANE) / frustum_length);
        projectionMatrix.m33 = 0;
    }
}
