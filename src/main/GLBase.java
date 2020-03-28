package main;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.ContextAttribs;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.lwjgl.opengl.PixelFormat;

import static org.lwjgl.opengl.GL11.glViewport;

public abstract class GLBase {

    /**
     * Creates the window in fullscreen mode
     *
     * @param vsync determines whether the vsync is enabled or disabled
     */
    public GLBase(boolean vsync) {
        try {
            Display.setFullscreen(true);
            Display.setVSyncEnabled(vsync);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Creates the window in window mode
     *
     * @param title title of the display window
     * @param width width of the window
     * @param height height of the window
     * @param resizable determines whether the window is resizable or not
     */
    public GLBase(String title, int width, int height, boolean resizable) {
        Display.setTitle(title);

        try {
            Display.setDisplayMode(new DisplayMode(width, height));
        } catch (Exception e) {
            e.printStackTrace();
        }

        Display.setResizable(resizable);
    }

    /**
     * Runs the application with the default pixel format and no context attributes
     */
    public final void run() {
        run(new PixelFormat(), null);
    }

    /**
     * Runs the application with the given context attributes and the default pixel format
     *
     * @param contextAttribs The attributes for the OpenGL context
     */
    public final void run(ContextAttribs contextAttribs) {
        run(new PixelFormat(), contextAttribs);
    }

    /**
     * Runs the application with the given pixel format and no context attributes
     *
     * @param pixelFormat
     */
    public final void run(PixelFormat pixelFormat) {
        run(pixelFormat, null);
    }

    /**
     * Runs the application with the given pixel format and context attributes
     *
     * @param pixelFormat
     * @param contextAttribs
     */
    public final void run(PixelFormat pixelFormat, ContextAttribs contextAttribs) {
        try {
            Display.create(pixelFormat, contextAttribs); // create the display
        } catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }

        mainLoop(); // start the application loop
    }

    /**
     * main.Main application loop
     */
    private void mainLoop() {
        try {
            init(); // initialize

            resize(); // set the viewport to its proper size

            long lastTime = System.nanoTime();

            while(!shouldTerminate()) {
                long deltaTime = System.nanoTime() - lastTime;
                lastTime += deltaTime;

                if(Display.wasResized()) {
                    resize(); // if the window was resized
                }

                // process keyboard events
                while(Keyboard.next()) {
                    if(Keyboard.getEventKeyState())
                        keyPressed(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                    else
                        keyReleased(Keyboard.getEventKey(), Keyboard.getEventCharacter());
                }

                update(deltaTime);

                render(); // perform render operations

                updateDisplay(); // update the display
            }

            destroyDisplay();

        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Returns the width of the window
     *
     * @return the width of the window
     */
    public int getWidth() {
        return Display.getWidth();
    }

    /**
     * Returns the height of the window
     *
     * @return the height of the window
     */
    public int getHeight() {
        return Display.getHeight();
    }

    /**
     * Resizes the viewport
     */
    public void resize() {
        glViewport(0, 0, getWidth(), getHeight());
    }

    /**
     * Returns true if the application should terminate, false otherwise
     *
     * @return Whether the application should terminate or not
     */
    private boolean shouldTerminate() {
        return Display.isCloseRequested() || Keyboard.isKeyDown(Keyboard.KEY_ESCAPE);
    }

    /**
     * Destroys the application display
     */
    private void destroyDisplay() {
        Display.destroy();
    }

    /**
     * Updates the application display
     */
    private void updateDisplay() {
        Display.update();
    }

    /**
     * Called when a key has been pressed.
     *
     * @param key The @see org.lwjgl.input.Keyboard keycode of the pressed key.
     * @param c The literal character of the pressed key.
     */
    public void keyPressed(int key, char c) {}

    /**
     * Called when a key has been released.
     *
     * @param key The @see org.lwjgl.input.Keyboard keycode of the released key.
     * @param c The literal character of the released key.
     */
    public void keyReleased(int key, char c) {}

    /**
     * Called once per frame and given the elapsed time since the last call
     * to this method.
     *
     * @param deltaTime The elapsed time since the last call to this method, in nanoseconds.
     */
    public void update(long deltaTime) {}

    /**
     * Initialization setup
     */
    protected abstract void init();

    /**
     * Called once per frame
     *
     * Render operations should take place here
     */
    protected abstract void render();

}
