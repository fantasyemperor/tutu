package cloud.codechun.tutu.service.impl;

import cloud.codechun.tutu.exception.BusinessException;
import cloud.codechun.tutu.exception.ErrorCode;
import cloud.codechun.tutu.mapper.UserMapper;
import cloud.codechun.tutu.model.entity.User;
import cloud.codechun.tutu.model.entity.UserRoleEnum;
import cloud.codechun.tutu.model.vo.LoginUserVO;
import cloud.codechun.tutu.service.UserService;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import static cloud.codechun.tutu.common.PasswordUtil.encryptCode;
import static cloud.codechun.tutu.common.PasswordUtil.verifyCode;
import static cloud.codechun.tutu.constant.UserConstant.USER_LOGIN_STATE;

/**
* @author hanjichun
* @description 针对表【user(用户)】的数据库操作Service实现
* @createDate 2025-11-08 13:19:44
*/

@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{

    @Override
    public long userRegister(String userAccount, String userPassword, String checkPassword) {
        // 1. 校验
        if (StrUtil.hasBlank(userAccount, userPassword, checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "参数为空");
        }
        if (userAccount.length() < 4) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户账号过短");
        }
        if(userAccount.length()>11){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号太长");
        }
        if (userPassword.length() < 8 || checkPassword.length() < 8) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "用户密码过短");
        }
        if(userPassword.length()>15){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码太长");
        }
        if (!userPassword.equals(checkPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }
        // 2. 检查是否重复
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);
        long count = this.baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号重复");
        }
        // 3. 加密
        String encryptPassword = encryptCode(userPassword);
        // 4. 插入数据
        User user = new User();
        user.setUserAccount(userAccount);
        user.setUserPassword(encryptPassword);
        user.setUserName("无名");
        user.setUserRole(UserRoleEnum.USER.getValue());
        boolean saveResult = this.save(user);
        if (!saveResult) {
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，数据库错误");
        }
        return user.getId();
    }




    @Override
    public LoginUserVO userLogin(String userAccount, String userPassword, HttpServletRequest request) {

        if (StrUtil.hasBlank(userAccount, userPassword)) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"参数为空");
        }

        if(userAccount.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号太短");
        }

        if(userAccount.length()>11){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"账号太长");
        }

        if(userPassword.length()<4){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码太短");
        }

        if(userPassword.length()>15){
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码太长");
        }

        /**
         * 用密码工具类中的加密方法对密码进行加密
         */
//        String encryptPassword = encryptCode(userPassword);

        /**
         * 查询用户是否存在
         */

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userAccount", userAccount);

        /**
         * 得到sql查询后到user
         */
        User user = this.baseMapper.selectOne(queryWrapper);

        if (user == null){
            throw new BusinessException(ErrorCode.NOT_FOUND_ERROR,"用户不存在") ;
        }

        if(!verifyCode(userPassword,user.getUserPassword()))
        {
            throw new BusinessException(ErrorCode.PARAMS_ERROR,"密码错误");
        }


        request.getSession().setAttribute(USER_LOGIN_STATE,user);

        return this.getLoginUserVO(user);
    }


    @Override
    public LoginUserVO getLoginUserVO(User user) {
        if (user == null) return null;
        LoginUserVO loginUserVO = new LoginUserVO();
        BeanUtils.copyProperties(user, loginUserVO);
        return loginUserVO;
    }


}






