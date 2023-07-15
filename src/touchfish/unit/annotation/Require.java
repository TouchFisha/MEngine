package touchfish.unit.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Require {

    String name() default "";
    String description() default "";

    Class<?> type() default Object.class;
}