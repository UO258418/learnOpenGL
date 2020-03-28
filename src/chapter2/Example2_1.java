package chapter2;

import main.GLBase;

import static org.lwjgl.opengl.GL11.*;

public class Example2_1 extends GLBase {

    public Example2_1() {
        super("SIMPLE", 800, 600, false);
    }

    @Override
    protected void init() {
        glClearColor(0, 0, 1, 1);
    }

    @Override
    protected void render() {
        glClear(GL_COLOR_BUFFER_BIT);
    }
}
