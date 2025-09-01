package com.minecraftclone.world;

import com.minecraftclone.Interface.IBlock;

import java.util.List;

import static org.lwjgl.opengl.GL11.*;

public class Block implements IBlock {

    private int id;
    private String name;
    private int textureID;
    private float durability;
    private List<String> effectiveMaterials;
    private List<String> requiredMaterials;

    protected int x, y, z;

    // NOVO: flag para saber se é ar
    private static boolean isAir;

    public Block(int id, String name, int textureID, float durability,
                 List<String> effectiveMaterials, List<String> requiredMaterials) {
        this.id = id;
        this.name = name;
        this.textureID = textureID;
        this.durability = durability;
        this.effectiveMaterials = effectiveMaterials;
        this.requiredMaterials = requiredMaterials;
        this.isAir = false; // padrão é NÃO ser ar
    }

    // Construtor para criar ar
    public static Block air() {
        Block air = new Block(0, "Air", 0, 0f, List.of(), List.of());
        air.isAir = true;
        return air;
    }

    public static void render(float x, float y, float z, int textureID) {
        // Se for ar, não desenha nada
        if (isAir) return;

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

    public boolean isAir() {
        return isAir;
    }

    // Getters e setters (corrigindo seus retornos antigos)
    @Override
    public int getId() { return id; }

    @Override
    public String getName() { return name; }

    @Override
    public int getTextureID() { return textureID; }

    @Override
    public float getDurability() { return durability; }

    @Override
    public List<String> getEffectiveMaterials() { return effectiveMaterials; }

    @Override
    public List<String> getRequiredMaterials() { return requiredMaterials; }

    @Override
    public void setId(int id) { this.id = id; }

    @Override
    public void setName(String name) { this.name = name; }

    @Override
    public void setTextureID(int textureID) { this.textureID = textureID; }

    @Override
    public void setDurability(float durability) { this.durability = durability; }

    @Override
    public void setEffectiveMaterials(List<String> materials) { this.effectiveMaterials = materials; }

    @Override
    public void setRequiredMaterials(List<String> materials) { this.requiredMaterials = materials; }
}
