package models;

/**
 * This class contains a pointer and information relative to the raw,
 * untextured, model of an entity. It contains a reference to the id of the VAO
 * associated with the model and the number of vertices it has. The model is
 * given by the polygons that form an object together with some information like
 * the texture coordinates and the normal vectors at each vertex.
 *
 * @author Enrico
 */
public class RawModel {

    private int vaoID;
    private int vertexCount;

    /**
     * Basic constructor
     *
     * @param vaoID The id of the VAO containing the information on the model
     * @param vertexCount The number of vertices in the model
     */
    public RawModel(int vaoID, int vertexCount) {
        this.vaoID = vaoID;
        this.vertexCount = vertexCount;
    }

    /**
     *
     * @return The id of the VAO associated with the model
     */
    public int getVaoID() {
        return vaoID;
    }

    /**
     *
     * @return The number of vertices of the model
     */
    public int getVertexCount() {
        return vertexCount;
    }
}
