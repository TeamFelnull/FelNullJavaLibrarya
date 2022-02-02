package dev.felnull.fnjl.tuple;

public interface FNPair<K, E> {

    public static <K, E> FNPair<K, E> of(K key, E entry) {
        return new SimpleFNPair<>(key, entry);
    }

    public K getKey();

    public E getEntry();

    default K getLeft() {
        return getKey();
    }

    default E getRight() {
        return getEntry();
    }
}
