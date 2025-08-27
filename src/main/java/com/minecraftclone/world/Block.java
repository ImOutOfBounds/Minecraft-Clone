package com.minecraftclone.world;

import com.minecraftclone.Interface.IBlock;
import org.lwjgl.system.MemoryStack;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import java.util.List;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glTexCoord2f;
import static org.lwjgl.opengl.GL11.glVertex3f;
import static org.lwjgl.stb.STBImage.*;

public class Block implements IBlock {

    private final int id;
    private final String name;
    private final int textureID;
    private final float durability;
    private final List<String> effectiveMaterials;
    private final List<String> requiredMaterials;

    protected int x, y, z;

    public Block(int id, String name, int textureID, float durability,
                 List<String> effectiveMaterials, List<String> requiredMaterials) {
        this.id = id;
        this.name = name;
        this.textureID = textureID;
        this.durability = durability;
        this.effectiveMaterials = effectiveMaterials;
        this.requiredMaterials = requiredMaterials;
    }


    public static void render(float x, float y, float z, int cubeTexture) {
        glPushMatrix();
        glTranslatef(x, y, z);
        glBindTexture(GL_TEXTURE_2D, cubeTexture);

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

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return "";
    }

    @Override
    public int getTextureID() {
        return 0;
    }

    @Override
    public float getDurability() {
        return 0;
    }

    @Override
    public List<String> getEffectiveMaterials() {
        return List.of();
    }

    @Override
    public List<String> getRequiredMaterials() {
        return List.of();
    }

    @Override
    public void setId(int id) {

    }

    @Override
    public void setName(String name) {

    }

    @Override
    public void setTextureID(int textureID) {

    }

    @Override
    public void setDurability(float durability) {

    }

    @Override
    public void setEffectiveMaterials(List<String> materials) {

    }

    @Override
    public void setRequiredMaterials(List<String> materials) {

    }
}
