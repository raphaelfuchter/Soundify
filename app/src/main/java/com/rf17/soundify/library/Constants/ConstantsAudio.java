package com.rf17.soundify.library.Constants;

public enum ConstantsAudio {

    SAMPLE_RATE("SAMPLE_RATE", 22050),

    /**
     * The size of the buffer defines how much samples are processed in one step.
     * Common values are 1024,2048.
     */
    BUFFER_SIZE("BUFFER_SIZE", 1024);

    private String desc;
    private int value;

    ConstantsAudio(String desc, int value) {
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
