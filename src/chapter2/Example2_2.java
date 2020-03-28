package chapter2;

import main.GLBase;

import static org.lwjgl.opengl.GL11.*;

public class Example2_2 extends GLBase {

    public Example2_2() {
        super("chapter2.Example2_2", 600, 600, true);
    }

    @Override
    protected void init() {
        glClearColor(0, 0, 1, 1);
    }

    @Override
    protected void render() {
        // Clear window with current clear color
        glClear(GL_COLOR_BUFFER_BIT);

        // Set the current drawing color to red
        glColor3f(1, 0, 0);

        // Draw a filled rectangle with the current color
        glRectf(-25, 25, 25, -25);
    }

    @Override
    public void resize() {
        int w = getWidth(), h = getHeight();

        // Prevent a divide by zero
        if(h == 0)
            h = 1;

        // Set the viewport to the window dimensions
        glViewport(0, 0, w, h);

        // Reset coordinate system
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();

        // Establish the clipping volume (left, right, bottom, up, near, far)
        float aspectRatio = (float)w / h;
        if(w <= h)
            glOrtho(-100, 100, -100 / aspectRatio, 100 / aspectRatio, 1, -1);
        else
            glOrtho(-100 * aspectRatio, 100 * aspectRatio, -100, 100, 1, -1);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

}
