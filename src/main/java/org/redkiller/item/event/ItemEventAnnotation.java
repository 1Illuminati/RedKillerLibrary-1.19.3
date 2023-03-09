package org.redkiller.item.event;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ItemEventAnnotation {
    Act act();

    public enum Act {
        LEFT_CLICK,
        SHIFT_LEFT_CLICK,
        RIGHT_CLICK,
        SHIFT_RIGHT_CLICK,
        DROP,
        SHIFT_DROP,
        SWAP_HAND,
        SHIFT_SWAP_HAND,
        HIT,
        BREAK,
        FISHING,
    }
}
