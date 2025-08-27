package com.minecraftclone.Interface;

import java.util.List;

public interface IBlock {

    // Informações básicas
    int getId();
    String getName();
    int getTextureID();
    float getDurability();

    // Materiais que afetam a mineração
    List<String> getEffectiveMaterials(); // materiais que mineram o bloco rapidamente
    List<String> getRequiredMaterials();  // materiais necessários pra minerar

    // Operações
    void setId(int id);
    void setName(String name);
    void setTextureID(int textureID);
    void setDurability(float durability);
    void setEffectiveMaterials(List<String> materials);
    void setRequiredMaterials(List<String> materials);
}
