package dev.felnull.fnjl.math;

import dev.felnull.fnjl.tuple.FNTriple;
import dev.felnull.fnjl.util.FNMath;

import java.util.Objects;

public class FNVec4d {
    private double x;
    private double y;
    private double z;
    private double w;

    public FNVec4d() {

    }

    public FNVec4d(double x, double y, double z, double w) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.w = w;
    }

    public FNVec4d copy() {
        return new FNVec4d(x, y, z, w);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return z;
    }

    public double getW() {
        return w;
    }

    public double distance(FNVec4d vec) {
        return Math.sqrt(Math.pow(x - vec.getX(), 2) + Math.pow(y - vec.getY(), 2) + Math.pow(z - vec.getZ(), 2) + Math.pow(w - vec.getW(), 2));
    }

    public FNVec4d add(FNVec4d vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
        this.w += vec.getW();
        return this;
    }

    public FNVec4d sub(FNVec4d vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        this.z -= vec.getZ();
        this.w -= vec.getW();
        return this;
    }

    @Override
    public String toString() {
        return "FNVec4d{" +
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
        FNVec4d fnVec4d = (FNVec4d) o;
        return Double.compare(fnVec4d.x, x) == 0 && Double.compare(fnVec4d.y, y) == 0 && Double.compare(fnVec4d.z, z) == 0 && Double.compare(fnVec4d.w, w) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z, w);
    }

    public FNVec4f convertFloat() {
        return new FNVec4f((float) x, (float) y, (float) z, (float) w);
    }

    public FNVec4i convertInt() {
        return new FNVec4i((int) x, (int) y, (int) z, (int) w);
    }

    public FNVec3d toEulerAngle() {
        FNTriple<Double, Double, Double> e = FNMath.toEulerAngle(x, y, z, w);
        return new FNVec3d(e.getLeft(), e.getCenter(), e.getRight());
    }
}
