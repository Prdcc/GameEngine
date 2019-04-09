package renderEngine;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import models.RawModel;
import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector3f;

/**
 *
 * @author Enrico
 */
public class OBJLoader {
    
    /**
     *
     * @param fileName
     * @param loader
     * @return
     */
    public static RawModel loadObjModel(String fileName, Loader loader){
        FileReader fr = null;
        try {
            fr = new FileReader(new File("res/models/"+fileName+".obj"));
        } catch (FileNotFoundException ex) {
            Logger.getLogger(OBJLoader.class.getName()).log(Level.SEVERE, "Couldn't load file", ex);
            System.exit(-1);
        }
        
        BufferedReader reader = new BufferedReader(fr);
        String line;
        List<Vector3f> vertices = new ArrayList<>();
        List<Vector2f> textures = new ArrayList<>();
        List<Vector3f> normals = new ArrayList<>();
        List<Integer> indices = new ArrayList<>();
        
        float[] verticesArray = null;
        float[] normalsArray = null;
        float[] textureArray = null;
        int[] indicesArray = null;
        
        try{
            while(true){
                line = reader.readLine();
                String[] currentLine = line.split(" ");
                
                if(line.startsWith("v ")){
                    Vector3f vertex = new Vector3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
                    vertices.add(vertex);
                }
                else if(line.startsWith("vt ")){
                    Vector2f texture = new Vector2f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]));
                    textures.add(texture);
                } 
                else if(line.startsWith("vn ")){
                    Vector3f normal = new Vector3f(Float.parseFloat(currentLine[1]),
                            Float.parseFloat(currentLine[2]),Float.parseFloat(currentLine[3]));
                    normals.add(normal);
                }
                else if(line.startsWith("f ")){
                    textureArray = new float[vertices.size()*2];
                    normalsArray = new float[vertices.size()*3];
                    break;
                }
            }
            
            while(line != null){
                if(!line.startsWith("f ")){
                    line = reader.readLine();
                    continue;
                }
                
                String[] currentLine = line.split(" ");
                String[] vertex1 = currentLine[1].split("/");
                String[] vertex2 = currentLine[2].split("/");
                String[] vertex3 = currentLine[3].split("/");
                
                processVertex(vertex1,indices,textures,normals,textureArray,normalsArray);
                processVertex(vertex2,indices,textures,normals,textureArray,normalsArray);
                processVertex(vertex3,indices,textures,normals,textureArray,normalsArray);
                line = reader.readLine();
            }
            reader.close();
            
        }catch(Exception ex){
            Logger.getLogger(OBJLoader.class.getName()).log(Level.SEVERE, "Couldn't load file", ex);
            System.exit(-1);
        }
        verticesArray = new float[vertices.size()*3];
        indicesArray = new int[indices.size()];
        
        int vertexPointer = 0;
        for(Vector3f vertex:vertices){
            verticesArray[vertexPointer++] = vertex.x;
            verticesArray[vertexPointer++] = vertex.y;
            verticesArray[vertexPointer++] = vertex.z;
        }
        
        for(int i=0; i < indices.size(); i++){
            indicesArray[i] = indices.get(i);
        }
        
        return loader.loadToVAO(verticesArray, textureArray, normalsArray, indicesArray);
    }
    
    private static void processVertex(String[] vertexData, List<Integer> indices, 
            List<Vector2f> textures, List<Vector3f> normals, float[] texturesArray, 
            float[] normalsArray){
        
        int currentVertexPointer = Integer.parseInt(vertexData[0])-1;
        indices.add(currentVertexPointer);
        
        Vector2f currentTexture = textures.get(Integer.parseInt(vertexData[1])-1);
        texturesArray[currentVertexPointer*2] = currentTexture.x;
        texturesArray[currentVertexPointer*2+1] = 1 - currentTexture.y;
        
        Vector3f currentNormal = normals.get(Integer.parseInt(vertexData[2])-1);
        normalsArray[currentVertexPointer*3] = currentNormal.x;
        normalsArray[currentVertexPointer*3+1] = currentNormal.y;
        normalsArray[currentVertexPointer*3+2] = currentNormal.z;
    }
    
}
