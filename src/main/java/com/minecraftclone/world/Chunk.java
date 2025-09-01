package com.minecraftclone.world;

public class Chunk {

    private static int chunkSize;

    public int getChunkSize() {
        return 0;
    }

    public static void setChunkSize(int chunkSize) {
        Chunk.chunkSize = chunkSize;
    }

    public static void render(int cubeTexture, int chunkIndex_x, int chunkIndex_z){
        for (int i = 0; i < chunkSize; i++) {
            for (int j = 0; j < chunkSize * 2; j++) {
                for (int k = 0; k < chunkSize; k++) {
                    Block.render(i + chunkIndex_x * chunkSize, j, k + chunkIndex_z * chunkSize, cubeTexture);
                }
            }
        }
    }


}
