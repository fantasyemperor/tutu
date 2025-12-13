package cloud.codechun.tutu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD}) //限定该自定义注解只能标注在方法上
@Retention(RetentionPolicy.RUNTIME)  //运行时可见
public @interface AuthCheck {
    String value() ;

}
