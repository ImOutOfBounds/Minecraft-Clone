package com.minecraftclone.world;

public class Chunk {

    private static int chunkSize;

    public int getChunkSize() {
        return 0;
    }

    public static void setChunkSize(int chunkSize) {
        Chunk.chunkSize = chunkSize;
    }

    public static void render(int cubeTexture, int chunkIndex_x, int chunkIndex_z) {
        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize * 2; j++) {
                for (int k = 0; k < chunkSize; k++) {
                    int worldX = i + chunkIndex_x * chunkSize;
                    int worldY = j;
                    int worldZ = k + chunkIndex_z * chunkSize;

                    // Desenhar apenas blocos "sólidos"
                    if (shouldRenderBlock(worldX, worldY, worldZ)) {
                        Block.render(worldX, worldY, worldZ, cubeTexture);
                    }
                }
            }
        }
    }

    private static boolean shouldRenderBlock(int x, int y, int z) {
        return true;
    }


}
