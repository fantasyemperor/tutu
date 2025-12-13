package cloud.codechun.tutu.controller;

import cloud.codechun.tutu.api.aliyunai.MiandanDws;
import cloud.codechun.tutu.api.aliyunai.TiaomaDws;
import cloud.codechun.tutu.api.test1.TestCode;
import cloud.codechun.tutu.api.test1.TestCode2;
import cloud.codechun.tutu.service.UserService;
import cloud.codechun.tutu.service.impl.ErrordwsServiceImpl;
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

    /**
     * 单张图片逐一分析 代码实现判断逻辑
     * @param path
     */
    @PostMapping("/test3")
    public void test3(String path){
        errordwsServiceImpl.jushi("C:\\Users\\17763\\Desktop\\222\\2025-11-29-2-1\\2-DWS-4");
    }


    /**
     * 五张图片一起分析， ai是实现判断逻辑 ，有示例图
     * @return
     */
    @GetMapping("/test")
    public String test(){

        testcode.run();
        return "test";
    }

    /**
     * 五张图片一起分析， ai是实现判断逻辑 ，无示例图
     * @return
     */
    @GetMapping("/test2")
    public String test2(){

        testcode2.run();
        return "test";
    }
    }



