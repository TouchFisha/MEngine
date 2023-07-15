package touchfish.unit.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface MEngineStatic {

    String name() default "";

    String description() default "";

    Class<?> type() default java.lang.Object.class;

}