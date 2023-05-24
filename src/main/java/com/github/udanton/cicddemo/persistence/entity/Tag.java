package com.github.udanton.cicddemo.persistence.entity;

public enum Tag {
    HAPPY("Happy"),
    SAD("Sad"),
    EXCITED("Excited"),
    ANGRY("Angry"),
    RELAXED("Relaxed"),
    SURPRISED("Surprised"),
    SCARED("Scared"),
    ROMANTIC("Romantic");

    private String displayName;

    Tag(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}







