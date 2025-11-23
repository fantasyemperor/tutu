package cloud.codechun.tutu.controller;


import cloud.codechun.tutu.service.PictureService;
import cloud.codechun.tutu.service.UserService;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/picture")

/**
 * 图片通用接口
 *
 */
public class pictureController {

    @Resource
    private PictureService pictureService;

    /**
     *上传图片
     */
    void shangchuan(String canshu){
    }


    /**
     * 下载
     */
    void xiazai(String canshu){

    }
}
