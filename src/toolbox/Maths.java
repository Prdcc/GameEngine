package toolbox;

import entities.Camera;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Enrico
 */
public class Maths {

    /**
     *
     * @param translation
     * @param rx
     * @param ry
     * @param rz
     * @param scale
     * @return
     */
    public static Matrix4f createTransformationMatrix(Vector3f translation, 
            float rx, float ry, float rz, float scale){
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Matrix4f.translate(translation, matrix, matrix);
        
        Matrix4f.rotate((float) Math.toRadians(rx), new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(ry), new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(rz), new Vector3f(0,0,1), matrix, matrix);
        
        Matrix4f.scale(new Vector3f(scale,scale,scale), matrix, matrix);
        
        return matrix;
    }
    
    /**
     *
     * @param camera
     * @return
     */
    public static Matrix4f createViewMatrix(Camera camera){
        Matrix4f matrix = new Matrix4f();
        matrix.setIdentity();
        Vector3f pos = camera.getPosition();
        Matrix4f.translate(new Vector3f(-pos.x, -pos.y, -pos.z), matrix, matrix);
        
        Matrix4f.rotate((float) Math.toRadians(camera.getPitch()), new Vector3f(1,0,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getYaw()), new Vector3f(0,1,0), matrix, matrix);
        Matrix4f.rotate((float) Math.toRadians(camera.getRoll()), new Vector3f(0,0,1), matrix, matrix);
        
        return matrix;
    }    
}
