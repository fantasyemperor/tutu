package cloud.codechun.tutu.controller;


import cloud.codechun.tutu.api.test1.TestCode;
import cloud.codechun.tutu.api.test1.TestCode2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//@RestController
//@RequestMapping("/")
//public class TestController {
//    @Autowired
//    private TestCode testcode;
//    @Autowired
//    private TestCode2 testcode2;
//
//
//
//    @GetMapping("/test")
//    public String test(){
//
//        testcode.run();
//        return "test";
//    }
//
//    @GetMapping("/test2")
//    public String test2(){
//
//        testcode2.run();
//        return "test";
//    }
//}


//package cloud.codechun.tutu.controller;
//
//
//import cloud.codechun.tutu.common.BaseResponse;
//import cloud.codechun.tutu.common.ResultUtils;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/")
//public class MainController {
//
//    @GetMapping("/health")
//    public BaseResponse<String> heath() {
//        return ResultUtils.success("ok");
//    }
//
//}