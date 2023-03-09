package org.redkiller.util.map;

import java.io.Serial;
import java.util.HashMap;

public class NotNullMap<K, V> extends HashMap<K, V> {

    @Serial
    private static final long serialVersionUID = 1L;
    private final V defaultValue;

    public NotNullMap(V defaultValue) {
        this.defaultValue = defaultValue;
    }

    public V getDefaultValue() {
        return defaultValue;
    }

    @Override
    public V get(Object key) {
        if (!super.containsKey(key))
            this.newValue((K) key);

        return super.get(key);
    }

    private void newValue(K key) {
        super.put(key, defaultValue);
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = 31 * result + super.hashCode();
        result = 31 * result + defaultValue.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;
        if (!(obj instanceof NotNullMap))
            return false;
        NotNullMap<?, ?> other = (NotNullMap<?, ?>) obj;
        if (!other.defaultValue.equals(this.defaultValue))
            return false;
        return super.equals(obj);
    }
}
