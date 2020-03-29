package chapter3;

import main.GLBase;
import org.lwjgl.BufferUtils;
import org.lwjgl.input.Keyboard;

import java.nio.FloatBuffer;

import static org.lwjgl.opengl.GL11.*;

public class Listing3_7 extends GLBase {

    private float xRot, yRot;

    public Listing3_7() {
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
        short pattern = 0x5555; // Stipple pattern
        int factor = 1; // Stippling factor

        // Clear the window with the current clearing color
        glClear(GL_COLOR_BUFFER_BIT);

        glPushMatrix();

        glRotatef(xRot, 1, 0, 0);
        glRotatef(yRot, 0, 1, 0);

        // Enable stippling
        glEnable(GL_LINE_STIPPLE);

        // Step up Y axis 20 units at a time
        for(float y = - 90; y <= 90; y += 20, factor++) {
            // Reset the repeat factor and pattern
            glLineStipple(factor, pattern);

            // Draw the line
            glBegin(GL_LINES);
                glVertex2f(-80, y);
                glVertex2f(80, y);
            glEnd();
        }

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
            glOrtho(-nRange, nRange, -nRange / aspectRatio, nRange / aspectRatio, nRange, -nRange);
        else
            glOrtho(-nRange * aspectRatio, nRange * aspectRatio, -nRange, nRange, nRange, -nRange);

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
