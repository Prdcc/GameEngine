package engineTester;

import entities.Camera;
import entities.Entity;
import entities.Light;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import models.RawModel;
import models.TexturedModel;
import org.lwjgl.opengl.Display;
import org.lwjgl.util.vector.Vector3f;
import renderEngine.*;
import shaders.StaticShader;
import textures.ModelTexture;
/**
 *
 * @author Enrico
 */
public class MainGameLoop {

    public static void main(String[] args) {
        configureLogger();
        DisplayManager.createDisplay();
        Loader loader = new Loader();
        
        RawModel model = OBJLoader.loadObjModel("dragon", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("orange"));
        texture.setReflectivity(1);
        texture.setShineDamper(2);
        TexturedModel texturedModel = new TexturedModel(model,texture);
        
        Entity entity = new Entity(texturedModel, new Vector3f(0, -3f,-25),0,0,0,1);
        Light light = new Light(new Vector3f(0,0,-20), new Vector3f(1,1,1));
        
        Camera camera = new Camera();
        
        MasterRenderer renderer = new MasterRenderer();
        
        while(!Display.isCloseRequested()){
            entity.increaseRotation(0, 1, 0);
            camera.move();
            
            renderer.processEntity(entity);
            
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
