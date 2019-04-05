package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import terrains.Terrain;
import textures.ModelTexture;
/**
 *
 * @author Enrico
 */
public class MainGameLoop {

    public static void main(String[] args) {
        Random rand = new Random();
        configureLogger();
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        
        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("orange"));
        texture.setReflectivity(1);
        texture.setShineDamper(2);
        TexturedModel texturedModel = new TexturedModel(model,texture);
        
        Entity dragon = new Entity(texturedModel, new Vector3f(0, 0,-25),0,0,0,0.25f);
        Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
        
        List<Entity> trees = new ArrayList<>();
        RawModel treeModel = OBJLoader.loadObjModel("tree", loader);
        ModelTexture treeTexture = new ModelTexture(loader.loadTexture("tree"));
        TexturedModel treeTexturedModel = new TexturedModel(treeModel, treeTexture);
        for(int i = 0; i<100; i++){
            float x = rand.nextFloat();
            float z = rand.nextFloat();
            trees.add(new Entity(treeTexturedModel, new Vector3f(x*800, 0,-z*800),0,0,0,4));
        }
        
        List<Terrain> terrains = new ArrayList<>();
        terrains.add(new Terrain(1,-1,loader,new ModelTexture(loader.loadTexture("grass"))));
        terrains.add(new Terrain(0,-1,loader,new ModelTexture(loader.loadTexture("grass"))));
        terrains.add(new Terrain(-1,-1,loader,new ModelTexture(loader.loadTexture("grass"))));
        
        Camera camera = new Camera();
        
        MasterRenderer renderer = new MasterRenderer();
        
        while(!Display.isCloseRequested()){
            camera.move();
            
            for(Terrain terrain: terrains){
                renderer.processTerrain(terrain);
            }
            
            renderer.processEntity(dragon);
            
            for(Entity tree: trees){
                renderer.processEntity(tree);
            }
            
            renderer.render(light, camera);
            DisplayManager.updateDisplay();            
        }
        
        renderer.cleanUp();
        loader.cleanUp();
        DisplayManager.closeDisplay();
    }
    
    private static void configureLogger(){
        Logger logger = Logger.getLogger("");
        for(Handler handler: logger.getHandlers()){
            logger.removeHandler(handler);
        }
        FileHandler fh = null;  

        try {  
            // This block configure the logger with handler and formatter  
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH mm ss");
            fh = new FileHandler("logs/"+dtf.format(LocalDateTime.now())+".log");  
            logger.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);  
        } catch (SecurityException | IOException e) {  
            e.printStackTrace();  
            System.exit(-1);
        }
    }

}
