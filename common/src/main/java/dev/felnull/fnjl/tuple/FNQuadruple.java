package dev.felnull.fnjl.tuple;

public interface FNQuadruple<K, E, F, C> {

    static <K, E, F, C> SimpleFNQuadruple<K, E, F, C> of(K left, E leftCenter, F rightCenter, C right) {
        return new SimpleFNQuadruple<>(left, leftCenter, rightCenter, right);
    }

    K getLeft();

    C getRight();

    E getLeftCenter();

    F getRightCenter();
}
