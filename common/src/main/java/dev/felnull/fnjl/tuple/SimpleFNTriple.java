package dev.felnull.fnjl.tuple;

import java.util.Objects;

public class SimpleFNTriple<K, E, F> implements FNTriple<K, E, F> {
    private final K left;
    private final E center;
    private final F right;

    public SimpleFNTriple(K left, E center, F right) {
        this.left = left;
        this.center = center;
        this.right = right;
    }

    @Override
    public K getLeft() {
        return left;
    }

    @Override
    public F getRight() {
        return right;
    }

    @Override
    public E getCenter() {
        return center;
    }

    @Override
    public String toString() {
        return "[" + left + "," + center + "," + right + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleFNTriple<?, ?, ?> that = (SimpleFNTriple<?, ?, ?>) o;
        return Objects.equals(left, that.left) && Objects.equals(center, that.center) && Objects.equals(right, that.right);
    }

    @Override
    public int hashCode() {
        return Objects.hash(left, center, right);
    }
}
