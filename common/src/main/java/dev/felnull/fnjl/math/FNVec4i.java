package dev.felnull.fnjl.math;

import java.util.Objects;

public class FNVec4i {
    private int x;
    private int y;
    private int z;
    private int w;

    public FNVec4i() {
    }

    public FNVec4i(int x, int y, int z, int w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public FNVec4i copy() {
        return new FNVec4i(x, y, z, w);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public int getZ() {
        return z;
    }

    public int getW() {
        return w;
    }

    public double distance(FNVec4i vec) {
        return Math.sqrt(Math.pow(x - vec.getX(), 2) + Math.pow(y - vec.getY(), 2) + Math.pow(z - vec.getZ(), 2) + Math.pow(w - vec.getW(), 2));
    }

    public FNVec4i add(FNVec4i vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
        this.w += vec.getW();
        return this;
    }

    public FNVec4i sub(FNVec4i vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        this.z -= vec.getZ();
        this.w -= vec.getW();
        return this;
    }

    @Override
    public String toString() {
        return "FNVec4i{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", w=" + w +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FNVec4i fnVec4i = (FNVec4i) o;
        return x == fnVec4i.x && y == fnVec4i.y && z == fnVec4i.z && w == fnVec4i.w;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    public FNVec4f convertFloat() {
        return new FNVec4f((float) x, (float) y, (float) z, (float) w);
    }

    public FNVec4d convertDouble() {
        return new FNVec4d(x, y, z, w);
    }
}
