package dev.felnull.fnjl.tuple;

import java.util.Objects;

public class SimpleFNPair<K, E> implements FNPair<K, E> {
    private final K key;
    private final E entry;

    public SimpleFNPair(K key, E entry) {
        this.key = key;
        this.entry = entry;
    }

    @Override
    public K getKey() {
        return key;
    }

    @Override
    public E getEntry() {
        return entry;
    }

    @Override
    public String toString() {
        return "[" + key + "," + entry + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SimpleFNPair<?, ?> that = (SimpleFNPair<?, ?>) o;
        return Objects.equals(key, that.key) && Objects.equals(entry, that.entry);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, entry);
    }
}
