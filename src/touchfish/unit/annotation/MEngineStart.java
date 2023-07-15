package touchfish.unit.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@MEngineRuntimeStaticInterface
@Retention(RetentionPolicy.RUNTIME)
public @interface MEngineStart {

    String name() default "";

    String description() default "";

    Class<?> type() default Object.class;

}