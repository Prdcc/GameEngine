package engineTester;

import entities.Camera;
import entities.Entity;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.FileHandler;
import java.util.logging.Handler;
import java.util.logging.Level;
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
        StaticShader shader = new StaticShader();
        Renderer renderer = new Renderer(shader);
        
        RawModel model = OBJLoader.loadObjModel("stall", loader);
        ModelTexture texture = new ModelTexture(loader.loadTexture("stallTexture"));
        TexturedModel texturedModel = new TexturedModel(model,texture);
        
        Entity entity = new Entity(texturedModel, new Vector3f(0,0,-50),0,0,0,1);
        
        Camera camera = new Camera();
        
        while(!Display.isCloseRequested()){
            entity.increaseRotation(0, 1, 0);
            camera.move();
            renderer.prepare();
            shader.start();
            shader.loadViewMatrix(camera);
            renderer.render(entity,shader);
            shader.stop();
            DisplayManager.updateDisplay();            
        }
        
        shader.cleanUp();
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
