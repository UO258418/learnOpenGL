package chapter2;

import main.GLBase;

import java.util.concurrent.TimeUnit;

import static org.lwjgl.opengl.GL11.*;

public class Example2_3 extends GLBase {

    // Initial square position and size
    private float x1, y1, rsize = 25;

    // Step size om x and y directions
    // (number of pixels t move each time)
    float xstep = 1, ystep = 1;

    // Keep track of windows changing width and height
    float windowWidth = 100, windowHeight = 100;

    // Timer counts for the update
    long ellapsed, delay = TimeUnit.MILLISECONDS.toNanos(33);

    public Example2_3() {
        super("Example2.3", 600, 600, true);
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
        glRectf(x1, y1, x1 + rsize, y1 - rsize);
    }

    @Override
    public void update(long deltaTime) {
        ellapsed += deltaTime;
        if(ellapsed > delay) {
            // Reverse direction when you reach left or right edge
            if(x1 > windowWidth - rsize || x1 < -windowWidth)
                xstep = -xstep;

            // Reverse direction when you reach top or bottom edge
            if(y1 > windowHeight || y1 < -windowHeight + rsize)
                ystep = -ystep;

            // Actually move the square
            x1 += xstep;
            y1 += ystep;

            // Check bounds. This is in case the window is made
            // smaller while the rectangle is bouncing and the
            // rectangle suddenly finds itself outside the new
            // clipping volume
            if(x1 > windowWidth - rsize + xstep)
                x1 = windowWidth - rsize - 1;
            else if(x1 < -(windowWidth + xstep))
                x1 = -windowWidth - 1;

            if(y1 > windowHeight + ystep)
                y1 = windowHeight - 1;
            else if(y1 < -(windowHeight - rsize + ystep))
                y1 = -windowHeight + rsize - 1;

            ellapsed = 0;
        }
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
        if(w <= h){
            windowWidth = 100;
            windowHeight = 100 / aspectRatio;
        } else {
            windowWidth = 100 * aspectRatio;
            windowHeight = 100;
        }

        glOrtho(-windowWidth, windowWidth, -windowHeight, windowHeight, 1, -1);

        glMatrixMode(GL_MODELVIEW);
        glLoadIdentity();
    }

}
