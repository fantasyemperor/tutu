package cloud.codechun.tutu.controller;

import cloud.codechun.tutu.api.aliyunai.MiandanDws;
import cloud.codechun.tutu.api.aliyunai.TiaomaDws;
import cloud.codechun.tutu.service.UserService;
import cloud.codechun.tutu.service.impl.ErrordwsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
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

    @PostMapping("/test3")
    public void test(String path){
        errordwsServiceImpl.jushi("C:\\Users\\17763\\Desktop\\222\\2025-11-29-2-1\\2-DWS-4");
    }


}
