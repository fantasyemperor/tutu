package cloud.codechun.tutu.common;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class PasswordUtil {
    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder(12); // cost 12


    /**
     * 加密
     */
    public static String encryptCode(String plainPassword) {
        return ENCODER.encode(plainPassword);
    }

    /**
     * 验证密码
     */
    public static boolean verifyCode(String plainPassword, String hashed) {
        return ENCODER.matches(plainPassword, hashed);
    }


}
