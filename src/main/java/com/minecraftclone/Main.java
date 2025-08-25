package com.minecraftclone;

import org.lwjgl.opengl.GL;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Main {

    private long window;
    private int cubeTexture;

    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void init() {
        if (!glfwInit()) throw new IllegalStateException("Falha ao inicializar GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        window = glfwCreateWindow(800, 600, "Minecraft Clone", NULL, NULL);
        if (window == NULL) throw new RuntimeException("Falha ao criar janela");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        // Carrega a textura apenas uma vez
        cubeTexture = loadTexture("src/main/resources/sprites/stone.jpg");

        // Configura o viewport
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        glOrtho(-2, 2, -2, 2, -2, 2); // simples ortográfica
        glMatrixMode(GL_MODELVIEW);
    }

    private void loop() {
        glClearColor(0.0f, 0.9f, 2.0f, 0.0f);

        while (!glfwWindowShouldClose(window)) {
            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();

            glRotatef(30f, 1f, 0f, 0f);
            glRotatef(20f, 0f, 1f, 0f);

            drawCube(0f, 0f, 0f, cubeTexture);

            glfwSwapBuffers(window);
            glfwPollEvents();
        }
    }

    public int loadTexture(String path) {
        int textureID;
        try (MemoryStack stack = MemoryStack.stackPush()) {
            IntBuffer width = stack.mallocInt(1);
            IntBuffer height = stack.mallocInt(1);
            IntBuffer channels = stack.mallocInt(1);

            ByteBuffer image = stbi_load(path, width, height, channels, 4);
            if (image == null)
                throw new RuntimeException("Falha ao carregar imagem: " + stbi_failure_reason());

            textureID = glGenTextures();
            glBindTexture(GL_TEXTURE_2D, textureID);

            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_REPEAT);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
            glTexParameteri(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_NEAREST);

            glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, width.get(), height.get(), 0,
                    GL_RGBA, GL_UNSIGNED_BYTE, image);

            stbi_image_free(image);
        }
        return textureID;
    }

    private void drawCube(float x, float y, float z, int textureID) {
        glPushMatrix();
        glTranslatef(x, y, z);
        glBindTexture(GL_TEXTURE_2D, textureID);

        glBegin(GL_QUADS);

        // Frente
        glTexCoord2f(0f, 0f); glVertex3f(-0.5f, -0.5f, 0.5f);
        glTexCoord2f(1f, 0f); glVertex3f(0.5f, -0.5f, 0.5f);
        glTexCoord2f(1f, 1f); glVertex3f(0.5f, 0.5f, 0.5f);
        glTexCoord2f(0f, 1f); glVertex3f(-0.5f, 0.5f, 0.5f);

        // Trás
        glTexCoord2f(0f, 0f); glVertex3f(-0.5f, -0.5f, -0.5f);
        glTexCoord2f(1f, 0f); glVertex3f(0.5f, -0.5f, -0.5f);
        glTexCoord2f(1f, 1f); glVertex3f(0.5f, 0.5f, -0.5f);
        glTexCoord2f(0f, 1f); glVertex3f(-0.5f, 0.5f, -0.5f);

        // Esquerda
        glTexCoord2f(0f, 0f); glVertex3f(-0.5f, -0.5f, -0.5f);
        glTexCoord2f(1f, 0f); glVertex3f(-0.5f, -0.5f, 0.5f);
        glTexCoord2f(1f, 1f); glVertex3f(-0.5f, 0.5f, 0.5f);
        glTexCoord2f(0f, 1f); glVertex3f(-0.5f, 0.5f, -0.5f);

        // Direita
        glTexCoord2f(0f, 0f); glVertex3f(0.5f, -0.5f, -0.5f);
        glTexCoord2f(1f, 0f); glVertex3f(0.5f, -0.5f, 0.5f);
        glTexCoord2f(1f, 1f); glVertex3f(0.5f, 0.5f, 0.5f);
        glTexCoord2f(0f, 1f); glVertex3f(0.5f, 0.5f, -0.5f);

        // Topo
        glTexCoord2f(0f, 0f); glVertex3f(-0.5f, 0.5f, -0.5f);
        glTexCoord2f(1f, 0f); glVertex3f(-0.5f, 0.5f, 0.5f);
        glTexCoord2f(1f, 1f); glVertex3f(0.5f, 0.5f, 0.5f);
        glTexCoord2f(0f, 1f); glVertex3f(0.5f, 0.5f, -0.5f);

        // Fundo
        glTexCoord2f(0f, 0f); glVertex3f(-0.5f, -0.5f, -0.5f);
        glTexCoord2f(1f, 0f); glVertex3f(0.5f, -0.5f, -0.5f);
        glTexCoord2f(1f, 1f); glVertex3f(0.5f, -0.5f, 0.5f);
        glTexCoord2f(0f, 1f); glVertex3f(-0.5f, -0.5f, 0.5f);

        glEnd();
        glPopMatrix();
    }

    public static void main(String[] args) {
        new Main().run();
    }
}
