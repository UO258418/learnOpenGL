package chapter3;

import main.GLBase;
import org.lwjgl.input.Keyboard;

import static org.lwjgl.opengl.GL11.*;

public class Listing3_5 extends GLBase {

    private float xRot, yRot;

    public Listing3_5() {
        super("Example3.1", 600, 600, true);
    }

    @Override
    protected void init() {
        // Black background
        glClearColor(0, 0, 0, 1);

        // Set drawing color to green
        glColor3f(0, 1, 0);
    }

    @Override
    protected void render() {
        // Storage for the coordinates and angles
        float x, y, z, angle;

        // Clear the window with the current clearing color
        glClear(GL_COLOR_BUFFER_BIT);

        // Save the matrix state and do the rotation
        glPushMatrix();
        glRotatef(xRot, 1, 0, 0);
        glRotatef(yRot, 0, 1, 0);

        // Call only once for all remaining points
        glBegin(GL_LINE_STRIP);

        z = -50;
        for(angle = 0; angle <= 2 * Math.PI * 3; angle += .1f, z += 0.5) {
            x = (float) (50 * Math.sin(angle));
            y = (float) (50 * Math.cos(angle));

            // Specify the point and move the Z value up a little
            glVertex3f(x, y, z);
        }

        // Done drawing points
        glEnd();

        // Restore transformations
        glPopMatrix();
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

    @Override
    public void update(long deltaTime) {
        if(Keyboard.isKeyDown(Keyboard.KEY_LEFT))
            xRot += 5;
        if(Keyboard.isKeyDown(Keyboard.KEY_RIGHT))
            xRot -= 5;

        if(Keyboard.isKeyDown(Keyboard.KEY_DOWN))
            yRot -= 5;
        if(Keyboard.isKeyDown(Keyboard.KEY_UP))
            yRot += 5;

        if(Keyboard.isKeyDown(Keyboard.KEY_R))
            xRot = yRot = 0;
    }

}
