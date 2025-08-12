package agents.annotations;

import jakarta.persistence.Convert;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface DbJsonB {

    /**
     * For VARCHAR storage specify the column length.
     */
    int length() default 0;
}

