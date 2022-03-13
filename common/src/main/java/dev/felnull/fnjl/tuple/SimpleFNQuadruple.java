package dev.felnull.fnjl.tuple;

import java.util.Objects;

public class SimpleFNQuadruple<K, E, F, C> implements FNQuadruple<K, E, F, C> {
    private final K left;
    private final E leftCenter;
    private final F rightCenter;
    private final C right;

    public SimpleFNQuadruple(K left, E leftCenter, F rightCenter, C right) {
        this.left = left;
        this.leftCenter = leftCenter;
        this.rightCenter = rightCenter;
        this.right = right;
    }

    @Override
    public K getLeft() {
        return left;
    }

    @Override
    public C getRight() {
        return right;
    }

    @Override
    public E getLeftCenter() {
        return leftCenter;
    }

    @Override
    public F getRightCenter() {
        return rightCenter;
    }

    @Override
    public String toString() {
        return "[" + left + "," + leftCenter + "," + rightCenter + "," + right + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleFNQuadruple<?, ?, ?, ?> that = (SimpleFNQuadruple<?, ?, ?, ?>) o;
        return Objects.equals(left, that.left) && Objects.equals(leftCenter, that.leftCenter) && Objects.equals(rightCenter, that.rightCenter) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, leftCenter, rightCenter, right);
    }
}
