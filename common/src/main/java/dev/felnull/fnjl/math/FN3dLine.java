package dev.felnull.fnjl.math;

import java.util.Objects;

public class FN3dLine {
    private FNVec3d from;
    private FNVec3d to;

    public FN3dLine(double fromX, double fromY, double fromZ, double toX, double toY, double toZ) {
        this(new FNVec3d(fromX, fromY, fromZ), new FNVec3d(toX, toY, toZ));
    }

    public FN3dLine(FNVec3d from, FNVec3d to) {
        this.from = from;
        this.to = to;
    }

    public FNVec3d getFrom() {
        return from;
    }

    public FNVec3d getTo() {
        return to;
    }

    public void setTo(FNVec3d to) {
        this.to = to;
    }

    public void setFrom(FNVec3d from) {
        this.from = from;
    }


    public double getFromX() {
        return from.getX();
    }

    public double getFromY() {
        return from.getY();
    }

    public double getFromZ() {
        return from.getZ();
    }

    public double getToX() {
        return to.getX();
    }

    public double getToY() {
        return to.getY();
    }

    public double getToZ() {
        return to.getZ();
    }

    /* public FNVec3d getIntersectPoint(FN3dLine line) {
        FN2dLine lineX1 = new FN2dLine(new FNVec2d(getFromX(), getFromY()), new FNVec2d(getToX(), getToY()));
        FN2dLine lineX2 = new FN2dLine(new FNVec2d(line.getFromX(), line.getFromY()), new FNVec2d(line.getToX(), line.getToY()));
        FNVec2d pointX = lineX1.getIntersectPoint(lineX2);

        FN2dLine lineY1 = new FN2dLine(new FNVec2d(getFromX(), getFromZ()), new FNVec2d(getToX(), getToZ()));
        FN2dLine lineY2 = new FN2dLine(new FNVec2d(line.getFromX(), line.getFromZ()), new FNVec2d(line.getToX(), line.getToZ()));
        FNVec2d pointY = lineY1.getIntersectPoint(lineY2);

       FN2dLine lineZ1 = new FN2dLine(new FNVec2d(getFromY(), getFromZ()), new FNVec2d(getToY(), getToZ()));
        FN2dLine lineZ2 = new FN2dLine(new FNVec2d(line.getFromY(), line.getFromZ()), new FNVec2d(line.getToY(), line.getToZ()));
        FNVec2d pointZ = lineZ1.getIntersectPoint(lineZ2);

        if (pointX == null || pointY == null)
            return null;

        return new FNVec3d(pointX.getX(), pointY.getY(), pointY.getY());
    }*/

    @Override
    public String toString() {
        return "FN3dLine{" +
                "from=" + from +
                ", to=" + to +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FN3dLine fn3dLine = (FN3dLine) o;
        return Objects.equals(from, fn3dLine.from) && Objects.equals(to, fn3dLine.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
