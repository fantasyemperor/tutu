package cloud.codechun.tutu.controller;

import cloud.codechun.tutu.annotation.AuthCheck;
import cloud.codechun.tutu.api.aliyunai.MiandanDws;
import cloud.codechun.tutu.api.aliyunai.TiaomaDws;
import cloud.codechun.tutu.api.test1.TestCode;
import cloud.codechun.tutu.api.test1.TestCode2;
import cloud.codechun.tutu.common.BaseResponse;
import cloud.codechun.tutu.model.entity.MyMessage;
import cloud.codechun.tutu.model.entity.User;
import cloud.codechun.tutu.mq.MyMessageProducer;
import cloud.codechun.tutu.service.UserService;
import cloud.codechun.tutu.service.impl.ErrordwsServiceImpl;
import cloud.codechun.tutu.service.impl.JobServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping
public class DwsController {
    @Autowired
    private TiaomaDws tiaomaDws;
    @Autowired
    private MiandanDws miandanDws;
    @Autowired
    private ErrordwsServiceImpl errordwsServiceImpl;

    @Autowired
    private TestCode testcode;
    @Autowired
    private TestCode2 testcode2;

    @Autowired
    private MyMessageProducer myMessageProducer;
    @Autowired
    private UserService userService;

    @Autowired
    private JobServiceImpl  jobServiceImpl;

    /**
     * 单张图片逐一分析 代码实现判断逻辑
     * @param path
     */
//    @PostMapping("/test3")
//    public void test3(String path){
//        errordwsServiceImpl.jushi("C:\\Users\\17763\\Desktop\\222\\2025-11-29-2-1\\2-DWS-4");
//    }
//
//
//    /**
//     * 五张图片一起分析， ai是实现判断逻辑 ，有示例图
//     * @return
//     */
//    @GetMapping("/test")
//    public void test(){
//
//        testcode.run();
//
//    }
//
//    /**
//     * 五张图片一起分析， ai是实现判断逻辑 ，无示例图
//     * @return
//     */
//    @GetMapping("/test2")
//    public void test2(){
//
//        testcode2.run();
//
//    }
////
////    @PostMapping("/test4")
////    public void test4(String path){
////
////        errordwsServiceImpl.run(path);
////    }
//
//
//    /**
//     * 将path发送到rabbitmq
//     * @param path
//     */

    @AuthCheck(mustRole = "admin")
    @PostMapping("/dws")
    public void dws(String path, HttpServletRequest request){
        jobServiceImpl.setJob0(path);

        User user = userService.getLoginUser(request);
        String useName = user.getUserAccount();
        MyMessage mymessage = new MyMessage();
        mymessage.setUsername(useName);
        mymessage.setMessage(path);
        myMessageProducer.sendMessage("code_exchange","my_routingKey",mymessage);

    }



    @GetMapping("/result")
    public BaseResponse result(String path){


      return errordwsServiceImpl.getResult(path);




    }


}




