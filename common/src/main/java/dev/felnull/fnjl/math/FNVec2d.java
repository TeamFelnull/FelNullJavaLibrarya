package dev.felnull.fnjl.math;

import java.util.Objects;

public class FNVec2d {
    private double x;
    private double y;

    public FNVec2d(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double distance(FNVec2i vec) {
        return Math.sqrt(Math.pow(x - vec.getX(), 2) + Math.pow(y - vec.getY(), 2));
    }

    public FNVec2d add(FNVec2d vec) {
        this.x += vec.getX();
        this.y += vec.getY();
        return this;
    }

    public FNVec2d sub(FNVec2d vec) {
        this.x -= vec.getX();
        this.y -= vec.getY();
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FNVec2d fnVec2d = (FNVec2d) o;
        return Double.compare(fnVec2d.x, x) == 0 && Double.compare(fnVec2d.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "FNVec2d{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public FNVec2f convertFloat() {
        return new FNVec2f((float) x, (float) y);
    }

    public FNVec2i convertInt() {
        return new FNVec2i((int) x, (int) y);
    }
}
