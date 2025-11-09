package cloud.codechun.tutu.service;

import cloud.codechun.tutu.model.dto.user.UserLoginRequest;
import cloud.codechun.tutu.model.entity.User;
import cloud.codechun.tutu.model.vo.LoginUserVO;
import com.baomidou.mybatisplus.extension.service.IService;
import jakarta.servlet.http.HttpServletRequest;

/**
* @author hanjichun
* @description 针对表【user(用户)】的数据库操作Service
* @createDate 2025-11-08 13:19:44
*/
public interface UserService extends IService<User> {

    long userRegister(String userAccount, String userPassword,String checkPassword);


    /**
     *获取加密后的密码
     */
    String getEncryptPassword(String userPassword);

    LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request);




}
