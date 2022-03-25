package dev.felnull.fnjl.math;

import dev.felnull.fnjl.tuple.FNQuadruple;
import dev.felnull.fnjl.util.FNMath;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FNVec3f {
    private float x;
    private float y;
    private float z;

    public FNVec3f() {

    }

    public FNVec3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public FNVec3f copy() {
        return new FNVec3f(x, y, z);
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

    public double distance(@NotNull FNVec3f vec) {
        return Math.sqrt(Math.pow(x - vec.getX(), 2) + Math.pow(y - vec.getY(), 2) + Math.pow(z - vec.getZ(), 2));
    }


    public FNVec3f add(@NotNull FNVec3f vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
        return this;
    }

    public FNVec3f sub(@NotNull FNVec3f vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        this.z -= vec.getZ();
        return this;
    }

    public FNVec3f radians() {
        this.x = (float) Math.toRadians(x);
        this.y = (float) Math.toRadians(y);
        this.z = (float) Math.toRadians(z);
        return this;
    }

    public FNVec3f degrees() {
        this.x = (float) Math.toDegrees(x);
        this.y = (float) Math.toDegrees(y);
        this.z = (float) Math.toDegrees(z);
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FNVec3f fnVec3f = (FNVec3f) o;
        return Float.compare(fnVec3f.x, x) == 0 && Float.compare(fnVec3f.y, y) == 0 && Float.compare(fnVec3f.z, z) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    @Override
    public String toString() {
        return "FNVec3f{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    public FNVec3d convertDouble() {
        return new FNVec3d(x, y, z);
    }

    public FNVec3i convertInt() {
        return new FNVec3i((int) x, (int) y, (int) z);
    }

    public FNVec4f toQuaternion() {
        FNQuadruple<Double, Double, Double, Double> q = FNMath.toQuaternion(x, y, z);
        double qx = q.getLeft();
        double qy = q.getLeftCenter();
        double qz = q.getRightCenter();
        double qw = q.getRight();
        return new FNVec4f((float) qx, (float) qy, (float) qz, (float) qw);
    }
}
