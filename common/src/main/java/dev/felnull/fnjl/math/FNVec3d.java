package dev.felnull.fnjl.math;

import dev.felnull.fnjl.tuple.FNQuadruple;
import dev.felnull.fnjl.util.FNMath;

import java.util.Objects;

public class FNVec3d {
    private double x;
    private double y;
    private double z;

    public FNVec3d(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
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

    public double distance(FNVec3d vec) {
        return Math.sqrt(Math.pow(x - vec.getX(), 2) + Math.pow(y - vec.getY(), 2) + Math.pow(z - vec.getZ(), 2));
    }

    public FNVec3d add(FNVec3d vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
        return this;
    }

    public FNVec3d sub(FNVec3d vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        this.z -= vec.getZ();
        return this;
    }

    public FNVec3d radians() {
        this.x = Math.toRadians(x);
        this.y = Math.toRadians(y);
        this.z = Math.toRadians(z);
        return this;
    }

    public FNVec3d degrees() {
        this.x = Math.toDegrees(x);
        this.y = Math.toDegrees(y);
        this.z = Math.toDegrees(z);
        return this;
    }

    public FNVec3d normalized() {
        normalize();
        return this;
    }

    public boolean normalize() {
        double f = this.x * this.x + this.y * this.y + this.z * this.z;
        if (f < 1.0E-5D) {
            return false;
        } else {
            double g = FNMath.fastInvSqrt(f);
            this.x *= g;
            this.y *= g;
            this.z *= g;
            return true;
        }
    }

    @Override
    public String toString() {
        return "FNVec3d{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FNVec3d fnVec3d = (FNVec3d) o;
        return Double.compare(fnVec3d.x, x) == 0 && Double.compare(fnVec3d.y, y) == 0 && Double.compare(fnVec3d.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public FNVec3f convertFloat() {
        return new FNVec3f((float) x, (float) y, (float) z);
    }

    public FNVec3i convertInt() {
        return new FNVec3i((int) x, (int) y, (int) z);
    }

    public FNVec4d toQuaternion() {
        FNQuadruple<Double, Double, Double, Double> q = FNMath.toQuaternion(x, y, z);
        return new FNVec4d(q.getLeft(), q.getLeftCenter(), q.getRightCenter(), q.getRight());
    }
}
