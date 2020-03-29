package practices;

import main.GLBase;

import static org.lwjgl.opengl.GL11.*;

public class TerrainGenerator extends GLBase {

    // Perspective inclination angle (in degrees)
    private final static float ANGLE = 60;

    // Size of the terrain
    private int rows, cols, tSize;

    // Array for the noise generation
    float[][] terrain;
    OpenSimplexNoise noise;

    // Range of the clipping volume
    float range = 600;

    // Movement
    float movement = 0;

    public TerrainGenerator(int rows, int cols, int tSize) {
        super(false);
        setRows(rows);
        setCols(cols);
        settSize(tSize);
        this.terrain = new float[rows][cols];
        this.noise = new OpenSimplexNoise();
    }

    @Override
    protected void init() {
        // Set clear color to black
        glClearColor(0.5f, 0.75f, 0.95f, 1);
    }

    @Override
    protected void render() {
        // Clear the window with the current clear color
        glClear(GL_COLOR_BUFFER_BIT);

        // Generate movement sensation
        calculateZCoordinate();

        // Save the current context
        glPushMatrix();

        // Create the perspective for the terrain
        glTranslatef(range / 2, range / 2 + 80, 0);
        glRotatef(ANGLE, 1, 0, 0);
        glTranslatef(-range / 2, -range / 2, 0);

        // Draw terrain and its outlines
        drawTerrainMesh();
        drawMeshOutlines();

        // Restore the context
        glPopMatrix();
    }

    @Override
    public void resize() {
        int w = getWidth(), h = getHeight();

        // Prevent a divide by zero
        if (h == 0)
            h = 1;

        // Set the viewport to plae the center of coordinates at the top left corner
        glViewport(0, 0, w, h);

        // Reset the matrix projection stack
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        // Establish the clipping volume (left, right, bottom, top, near, far)
        glOrtho(0, range, range, 0, range, -range);

        // Reset model view stack
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

    public void setRows(int rows) {
        this.rows = Math.max(rows, 0);
    }

    public void setCols(int cols) {
        this.cols = Math.max(cols, 0);
    }

    public void settSize(int tSize) {
        this.tSize = Math.max(tSize, 0);
    }

    private float map(double value, double oldMin, double oldMax, double newMin, double newMax) {
        double oldRange = (oldMax - oldMin);
        if (oldRange == 0)
            return (float) newMin;
        else {
            double newRange = (newMax - newMin);
            return (float) ((value - oldMin) * newRange / oldRange + newMin);
        }
    }

    private void calculateZCoordinate() {
        // Move
        movement -= 0.0005;

        float yOff = movement;

        for (int row = 0; row < rows; row++) {

            // Create the noise for the x coordinate
            float xOff = 0;

            for (int col = 0; col < cols; col++) {
                terrain[row][col] = map(noise.eval(xOff, yOff), -1, 1, 0, 120);
                xOff += 0.2;
            }

            yOff += 0.2;
        }
    }

    private void drawTerrainMesh() {
        // Set drawing color
        glColor3f(0.2f, 0.7f, 0.35f);

        // Coordinates for each vertex forming the terrain
        for (int row = 0; row < rows - 1; row++) {

            // Start drawing
            glBegin(GL_TRIANGLE_STRIP);

            for (int col = 0; col < cols; col++) {

                glVertex3f(col * tSize, row * tSize, terrain[row][col]);
                glVertex3f(col * tSize, (row + 1) * tSize, terrain[row + 1][col]);

            }

            glEnd();
        }
    }

    private void drawMeshOutlines() {
        // Set the drawing color
        glColor3f(0, 0, 0);

        // Coordinates for each vertex forming the terrain
        for (int row = 0; row < rows - 1; row++) {

            // Start drawing
            glBegin(GL_LINE_STRIP);

            for (int col = 0; col < cols; col++) {

                glVertex3f(col * tSize, row * tSize, terrain[row][col]);
                glVertex3f(col * tSize, (row + 1) * tSize, terrain[row + 1][col]);

            }

            glEnd();
        }
    }

}
