package dev.felnull.fnjl.tuple;

public interface FNPair<K, E> {
    public K getKey();

    public E getEntry();

    default K getLeft() {
        return getKey();
    }

    default E getRight() {
        return getEntry();
    }
}
