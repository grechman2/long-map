package de.comparus.opensource.longmap;

import java.util.Objects;

public class KeyValueNode<V>{
    private long key;
    private V value;

    public KeyValueNode(long key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        KeyValueNode<?> keyValueNode = (KeyValueNode<?>) o;
        return key == keyValueNode.key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key);
    }

    public long getKey() {
        return key;
    }

    public void setKey(long key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }
}
