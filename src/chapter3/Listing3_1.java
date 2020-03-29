package chapter3;

import main.GLBase;

import static org.lwjgl.opengl.GL11.*;

public class Listing3_1 extends GLBase {

    public Listing3_1() {
        super("Example3.1", 600, 600, true);
    }

    @Override
    protected void init() {
        glClearColor(0, 0, 1, 1);
    }

    @Override
    protected void render() {
        glClear(GL_COLOR_BUFFER_BIT);
    }

    @Override
    public void resize() {
        float nRange = 100;

        int w = getWidth(), h = getHeight();

        // Prevent a divide by zero
        if(h == 0)
            h = 1;

        // Set the viewport to window dimensions
        glViewport(0, 0, w, h);

        // Reset the projection matrix stack
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        float aspectRatio = (float)w / h;

        // Establish the clipping volume (left, right, bottom, top, near, far)
        if(w <= h)
            glOrtho(-nRange, nRange, nRange / aspectRatio, -nRange / aspectRatio, nRange, -nRange);
        else
            glOrtho(-nRange * aspectRatio, nRange * aspectRatio, nRange, -nRange, nRange, -nRange);

        // Reset model view matrix stack
        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }
}
