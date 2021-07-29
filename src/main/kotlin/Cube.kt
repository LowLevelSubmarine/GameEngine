import org.lwjgl.opengl.GL11.*

class Cube (size: Float, pos: Vec3d) {

    init {
        glMatrixMode(GL_PROJECTION)
        glLoadIdentity()
        //glRotatef(20f, 0f, 1f, 0f)
        glTranslatef(pos.x, pos.y, pos.z)
        glBegin(GL_QUADS)
        glColor3f(0.69f, 0.52f, 0.34f)
        glVertex3f(1f, 1f, 0f)
        glVertex3f(1f, -1f, 0f)
        glVertex3f(-1f, -1f, 0f)
        glVertex3f(-1f, 1f, 0f)
        glEnd()
    }

}