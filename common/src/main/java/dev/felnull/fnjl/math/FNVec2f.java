package dev.felnull.fnjl.math;

import java.util.Objects;

public class FNVec2f {
    private float x;
    private float y;

    public FNVec2f(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public double distance(FNVec2i vec) {
        return Math.sqrt(Math.pow(x - vec.getX(), 2) + Math.pow(y - vec.getY(), 2));
    }

    public FNVec2f add(FNVec2f vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        return this;
    }

    public FNVec2f sub(FNVec2f vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        return this;
    }

    @Override
    public String toString() {
        return "FNVec2f{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FNVec2f fnVec2f = (FNVec2f) o;
        return Float.compare(fnVec2f.x, x) == 0 && Float.compare(fnVec2f.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }
}
