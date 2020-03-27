import static org.lwjgl.opengl.GL11.*;

public class Main extends GLBase {

    public static void main(String... args) {
        new Main().run();
    }

    public Main() {
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
