package dev.felnull.fnjl.math;

import java.util.Objects;

public class FNVec2i {
    private int x;
    private int y;

    public FNVec2i(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public double distance(FNVec2i vec) {
        return Math.sqrt(Math.pow(x - vec.getX(), 2) + Math.pow(y - vec.getY(), 2));
    }

    public FNVec2i add(FNVec2i vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        return this;
    }

    public FNVec2i sub(FNVec2i vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FNVec2i fnVec2i = (FNVec2i) o;
        return x == fnVec2i.x && y == fnVec2i.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "FNVec2i{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public FNVec2f convertFloat() {
        return new FNVec2f(x, y);
    }

    public FNVec2d convertDouble() {
        return new FNVec2d(x, y);
    }
}
