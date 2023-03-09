package org.redkiller.util;

import java.util.Objects;

public class ArrayHelper {
    /**
     * Returns the name of the class, as the JVM would output it. For instance, for an int, "I" is returned, for an
     * array of Objects, "[Ljava/lang/Object;" is returned. If the input is null, null is returned.
     *
     * @param clazz The class to get the name of.
     * @return className
     */
    public static String getJVMName(Class<?> clazz) {
        if(clazz == null) {
            return null;
        }
        //For arrays, .getName() is fine.
        if(clazz.isArray()) {
            return clazz.getName().replace('.', '/');
        }
        if(clazz == boolean.class) {
            return "Z";
        } else if(clazz == byte.class) {
            return "B";
        } else if(clazz == short.class) {
            return "S";
        } else if(clazz == int.class) {
            return "I";
        } else if(clazz == long.class) {
            return "J";
        } else if(clazz == float.class) {
            return "F";
        } else if(clazz == double.class) {
            return "D";
        } else if(clazz == char.class) {
            return "C";
        } else {
            return "L" + clazz.getName().replace('.', '/') + ";";
        }
    }

    /**
     * Generically and dynamically returns the array class type for the given class type. The dynamic equivalent of
     * sending {@code String.class} and getting {@code String[].class}. Works with array types as well.
     * @param clazz The class to convert to an array type.
     * @return The array type of the input class.
     */
    public static Class<?> getArrayClassFromType(Class<?> clazz) {
        Objects.requireNonNull(clazz);
        try {
            return Class.forName("[" + getJVMName(clazz).replace('/', '.'));
        } catch(ClassNotFoundException ex) {
            // This cannot naturally happen, as we are simply creating an array type for a real type that has
            // clearly already been loaded.
            throw new NoClassDefFoundError(ex.getMessage());
        }
    }
}
