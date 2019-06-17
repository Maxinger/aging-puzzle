package org.agingpuzzle.model;

public interface WithImage {

    default WithImage getBaseEntity() {
        return this;
    }

    default Image getImage() {
        return getBaseEntity().getImage();
    }

    default void setImage(Image image) {
        getBaseEntity().setImage(image);
    }
}

