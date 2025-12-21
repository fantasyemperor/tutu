package cloud.codechun.tutu.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.METHOD)  //限制注解只能用在方法上
@Retention(RetentionPolicy.RUNTIME)  //表示 程序运行时还能通过反射或 AOP 读取到这个注解  (AOP、反射、权限校验必须是 RUNTIME)

//定义一个注解：  AuthCheck
public @interface AuthCheck {

    /**
     * 声明一个注解：调用这个方法的用户，必须具备某个角色
     */
    String mustRole() default "";
}
