package renderEngine;

import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.PixelFormat;

/**
 * Manages the main display on which OpenGL draws to.
 *
 * @author Enrico
 */
public class DisplayManager {

    private static final int WIDTH = 1280;
    private static final int HEIGHT = 720;
    private static final int FPS_CAP = 60;

    /**
     * Creates the main display for the game. This is where OpenGL will draw to.
     */
    public static void createDisplay() {
        //3,2 is the OpenGL version we are targeting (3.2)
        ContextAttribs attribs = new ContextAttribs(3, 2)
                .withForwardCompatible(true)
                .withProfileCore(true);

        try {
            Display.setDisplayMode(new DisplayMode(WIDTH, HEIGHT));
            Display.create(new PixelFormat(), attribs);
            Display.setTitle("Game Engine");
        } catch (LWJGLException ex) {
            ex.printStackTrace();
        }

        GL11.glViewport(0, 0, WIDTH, HEIGHT);

    }

    /**
     * Updates the display every frame. It syncs the refresh rate to a certain
     * framerate.
     */
    public static void updateDisplay() {

        Display.sync(FPS_CAP);
        Display.update();

    }

    /**
     * Closes the frame on program exit.
     */
    public static void closeDisplay() {

        Display.destroy();

    }
}
