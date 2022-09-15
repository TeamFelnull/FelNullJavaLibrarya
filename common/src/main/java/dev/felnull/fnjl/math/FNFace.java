package dev.felnull.fnjl.math;

import java.util.Objects;

public class FNFace {
    private FNVec3d from;
    private FNVec3d to;

    public FNFace(double fromX, double fromY, double fromZ, double toX, double toY, double toZ) {
        this(new FNVec3d(fromX, fromY, fromZ), new FNVec3d(toX, toY, toZ));
    }

    public FNFace(FNVec3d from, FNVec3d to) {
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

    /**
     * 　　　0
     * 　　┌━━┐
     * 　３┃  ┃ １
     * 　　└━━┘
     * 　　 ２
     * <p>
     * ｙ┃　／z
     * 　└━━
     * 　ｘ
     *
     * @param num Edge number
     * @return Edge
     */
    public FN3dLine getEdge(int num) {
        switch (num) {
            case 0:
                return new FN3dLine(getFromX(), getFromY(), getFromZ(), getToX(), getFromY(), getToZ());
            case 1:
                return new FN3dLine(getToX(), getFromY(), getToZ(), getToX(), getToY(), getToZ());
            case 2:
                return new FN3dLine(getToX(), getToY(), getToZ(), getFromX(), getToY(), getFromZ());
            case 3:
                return new FN3dLine(getFromX(), getFromY(), getFromZ(), getFromX(), getToY(), getFromZ());
        }
        throw new IllegalStateException("Invalid edge number");
    }

    @Override
    public String toString() {
        return "FNFace{" + "from=" + from + ", to=" + to + '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FNFace fnFace = (FNFace) o;
        return Objects.equals(from, fnFace.from) && Objects.equals(to, fnFace.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }
}
