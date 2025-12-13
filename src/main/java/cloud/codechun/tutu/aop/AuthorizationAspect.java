package cloud.codechun.tutu.aop;


import org.aspectj.lang.annotation.Aspect;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Aspect
@Component
@Order(1) //优先级，数字越小越高
public class AuthorizationAspect {
}
