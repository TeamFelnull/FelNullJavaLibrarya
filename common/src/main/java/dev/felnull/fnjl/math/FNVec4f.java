package dev.felnull.fnjl.math;

import dev.felnull.fnjl.tuple.FNTriple;
import dev.felnull.fnjl.util.FNMath;

import java.util.Objects;

public class FNVec4f {
    private float x;
    private float y;
    private float z;
    private float w;

    public FNVec4f(float x, float y, float z, float w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public float getZ() {
        return z;
    }

    public float getW() {
        return w;
    }

    public double distance(FNVec4f vec) {
        return Math.sqrt(Math.pow(x - vec.getX(), 2) + Math.pow(y - vec.getY(), 2) + Math.pow(z - vec.getZ(), 2) + Math.pow(w - vec.getW(), 2));
    }

    public FNVec4f add(FNVec4f vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
        this.w += vec.getW();
        return this;
    }

    public FNVec4f sub(FNVec4f vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        this.z -= vec.getZ();
        this.w -= vec.getW();
        return this;
    }

    @Override
    public String toString() {
        return "FNVec4f{" +
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
        FNVec4f fnVec4f = (FNVec4f) o;
        return Float.compare(fnVec4f.x, x) == 0 && Float.compare(fnVec4f.y, y) == 0 && Float.compare(fnVec4f.z, z) == 0 && Float.compare(fnVec4f.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }


    public FNVec4i convertInt() {
        return new FNVec4i((int) x, (int) y, (int) z, (int) w);
    }

    public FNVec4d convertDouble() {
        return new FNVec4d(x, y, z, w);
    }

    public FNVec3f toEulerAngle() {
        FNTriple<Double, Double, Double> e = FNMath.toEulerAngle(x, y, z, w);
        double ex = e.getLeft();
        double ey = e.getCenter();
        double ez = e.getRight();
        return new FNVec3f((float) ex, (float) ey, (float) ez);
    }
}
