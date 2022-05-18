package dev.felnull.fnjl.math;

import dev.felnull.fnjl.util.FNMath;

import java.util.Objects;

public class FN2dLine {
    private FNVec2d from;
    private FNVec2d to;

    public FN2dLine(FNVec2d from, FNVec2d to) {
        this.from = from;
        this.to = to;
    }

    public FN2dLine(double fromX, double fromY, double toX, double toY) {
        this(new FNVec2d(fromX, fromY), new FNVec2d(toX, toY));
    }

    public FNVec2d getFrom() {
        return from;
    }

    public FNVec2d getTo() {
        return to;
    }

    public void setFrom(FNVec2d from) {
        this.from = from;
    }

    public void setTo(FNVec2d to) {
        this.to = to;
    }

    public double getFromX() {
        return from.getX();
    }

    public double getFromY() {
        return from.getY();
    }

    public double getToX() {
        return to.getX();
    }

    public double getToY() {
        return to.getY();
    }

    public FNVec2d getIntersectPoint(FN2dLine line) {
        return FNMath.getLinesIntersectPoint(this, line);
    }

    public boolean isOnPoint(FNVec2d point) {
        return FNMath.isPointOnLine(this, point);
    }

    @Override
    public String toString() {
        return "FN2dLine{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FN2dLine fn2dLine = (FN2dLine) o;
        return Objects.equals(from, fn2dLine.from) && Objects.equals(to, fn2dLine.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
