package dev.felnull.fnjl.tuple;

public interface FNTriple<K, E, F> {
    static <K, E, F> FNTriple<K, E, F> of(K left, E center, F right) {
        return new SimpleFNTriple<>(left, center, right);
    }

    K getLeft();

    F getRight();

    E getCenter();
}
