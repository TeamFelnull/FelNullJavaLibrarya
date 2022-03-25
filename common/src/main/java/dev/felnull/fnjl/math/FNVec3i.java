package dev.felnull.fnjl.math;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class FNVec3i {
    private int x;
    private int y;
    private int z;

    public FNVec3i() {
    }

    public FNVec3i(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public FNVec3i copy() {
        return new FNVec3i(x, y, z);
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

    public double distance(@NotNull FNVec3i vec) {
        return Math.sqrt(Math.pow(x - vec.getX(), 2) + Math.pow(y - vec.getY(), 2) + Math.pow(z - vec.getZ(), 2));
    }

    public FNVec3i add(@NotNull FNVec3i vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        this.z += vec.getZ();
        return this;
    }

    public FNVec3i sub(@NotNull FNVec3i vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        this.z -= vec.getZ();
        return this;
    }

    @Override
    public String toString() {
        return "FNVec3i{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FNVec3i fnVec3i = (FNVec3i) o;
        return x == fnVec3i.x && y == fnVec3i.y && z == fnVec3i.z;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y, z);
    }

    public FNVec3d convertDouble() {
        return new FNVec3d(x, y, z);
    }

    public FNVec3f convertFloat() {
        return new FNVec3f((float) x, (float) y, (float) z);
    }

}
