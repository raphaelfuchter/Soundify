package com.rf17.soundify.library.Constants;

public enum ConstantesAudio {

    SAMPLE_RATE("SAMPLE_RATE", 22050),
    BUFFER_SIZE("BUFFER_SIZE", 1024);//The size of the buffer defines how much samples are processed in one step. Common values are 1024,2048.

    private String desc;
    private int value;

    ConstantesAudio(String desc, int value) {
        this.desc = desc;
        this.value = value;
    }

    public String getDesc() {
        return this.desc;
    }

    public int getValue() {
        return this.value;
    }


}
