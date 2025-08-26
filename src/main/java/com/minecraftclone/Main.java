package com.minecraftclone;

import org.lwjgl.glfw.GLFWVidMode;
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

    // Posição da câmera
    private float camX = 0f, camY = 0f, camZ = 5f;

    // Rotação da câmera
    private float yaw = 0f;   // esquerda ↔ direita
    private float pitch = 0f; // cima ↔ baixo

    // Sensibilidade e velocidade
    private final float MOUSE_SENSITIVITY = 0.1f;
    private final float MOVE_SPEED = 0.1f;

    // Para armazenar última posição do mouse
    private double lastMouseX, lastMouseY;
    private boolean firstMouse = true;


    public void run() {
        init();
        loop();

        glfwFreeCallbacks(window);
        glfwDestroyWindow(window);
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    private void perspectiveGL(float fovY, float aspect, float zNear, float zFar) {
        float fH = (float) Math.tan(Math.toRadians(fovY) / 2) * zNear;
        float fW = fH * aspect;
        glFrustum(-fW, fW, -fH, fH, zNear, zFar);
    }


    private void init() {
        if (!glfwInit()) throw new IllegalStateException("Falha ao inicializar GLFW");

        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);

        long monitor = glfwGetPrimaryMonitor();
        GLFWVidMode vidmode = glfwGetVideoMode(monitor);

        window = glfwCreateWindow(vidmode.width(), vidmode.height(), "Minecraft Clone", monitor, NULL);
        if (window == NULL) throw new RuntimeException("Falha ao criar janela");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);
        if (window == NULL) throw new RuntimeException("Falha ao criar janela");

        glfwMakeContextCurrent(window);
        glfwSwapInterval(1);
        glfwShowWindow(window);

        GL.createCapabilities();
        glEnable(GL_TEXTURE_2D);
        glEnable(GL_DEPTH_TEST);

        cubeTexture = loadTexture("src/main/resources/sprites/stone.jpg");

        // Configura projeção em perspectiva
        int width = 800;
        int height = 600;
        glMatrixMode(GL_PROJECTION);
        glLoadIdentity();
        perspectiveGL(70f, (float) width / (float) height, 0.1f, 1000f);
        glMatrixMode(GL_MODELVIEW);

        // Callback para redimensionamento
        glfwSetFramebufferSizeCallback(window, (win, w, h) -> {
            glViewport(0, 0, w, h);

            glMatrixMode(GL_PROJECTION);
            glLoadIdentity();
            perspectiveGL(70f, (float) w / (float) h, 0.1f, 1000f);
            glMatrixMode(GL_MODELVIEW);
        });

        // trava e esconde o cursor
        glfwSetInputMode(window, GLFW_CURSOR, GLFW_CURSOR_DISABLED);

        glfwSetCursorPosCallback(window, (win, xpos, ypos) -> {
            if (firstMouse) {
                lastMouseX = xpos;
                lastMouseY = ypos;
                firstMouse = false;
            }

            double offsetX = lastMouseX - xpos;
            double offsetY = lastMouseY - ypos; // invertido: cima aumenta pitch
            lastMouseX = xpos;
            lastMouseY = ypos;

            offsetX *= MOUSE_SENSITIVITY;
            offsetY *= MOUSE_SENSITIVITY;

            yaw   += offsetX;
            pitch += offsetY;

            // limita a câmera pra não virar de cabeça pra baixo
            if (pitch > 89.0f) pitch = 89.0f;
            if (pitch < -89.0f) pitch = -89.0f;
        });
    }

    private void loop() {
        glClearColor(0.0f, 0.9f, 2.0f, 0.0f);

        // posição inicial da câmera para ver a chunk (centrinho 8,8,8)
        camX = 8f; camY = 8f; camZ = 35f; // recua no Z pra enxergar tudo

        int chunkSize = 16;

        while (!glfwWindowShouldClose(window)) {
            glfwPollEvents();         // 1) primeiro: eventos
            processInput();           // 2) depois: processa entradas

            glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
            glLoadIdentity();

            // 3) aplica câmera por frame
            glRotatef(-pitch, 1f, 0f, 0f);
            glRotatef(-yaw,   0f, 1f, 0f);
            glTranslatef(-camX, -camY, -camZ);

            // 4) desenha o mundo (sem glRotatef fixo aqui)
            for (int i = 0; i < chunkSize; i++) {
                for (int j = 0; j < chunkSize; j++) {
                    for (int k = 0; k < chunkSize; k++) {
                        drawCube(i, j, k, cubeTexture);
                    }
                }
            }

            glfwSwapBuffers(window);
        }
    }


    private void processInput() {
        float speed = MOVE_SPEED;

        // Direções baseadas na rotação da câmera
        float radYaw = (float) Math.toRadians(yaw);
        float radPitch = (float) Math.toRadians(pitch);

        // Vetor frente (forward)
        float forwardX = (float) Math.cos(radPitch) * (float) Math.sin(radYaw);
        float forwardY = (float) Math.sin(radPitch);
        float forwardZ = (float) -Math.cos(radPitch) * (float) Math.cos(radYaw);

        // Vetor direita (right)
        float rightX = (float) Math.sin(radYaw - Math.PI / 2.0);
        float rightZ = (float) -Math.cos(radYaw - Math.PI / 2.0);

        // W
        if (glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            camX += forwardX * speed;
            camY += forwardY * speed;
            camZ += forwardZ * speed;
        }
        // S
        if (glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            camX -= forwardX * speed;
            camY -= forwardY * speed;
            camZ -= forwardZ * speed;
        }
        // A
        if (glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            camX += rightX * speed;
            camZ += rightZ * speed;
        }
        // D
        if (glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            camX -= rightX * speed;
            camZ -= rightZ * speed;
        }
        // Espaço = voa pra cima
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            camY += speed;
        }
        // Shift = desce
        if (glfwGetKey(window, GLFW_KEY_LEFT_SHIFT) == GLFW_PRESS) {
            camY -= speed;
        }

        if (glfwGetKey(window, GLFW_KEY_ESCAPE) == GLFW_PRESS) {
            glfwSetWindowShouldClose(window, true);
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
