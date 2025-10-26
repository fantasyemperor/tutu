package cloud.codechun.tutu.controller;


import cloud.codechun.tutu.common.BaseResponse;
import cloud.codechun.tutu.common.ResultUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class MainController {

    @GetMapping("/health")
    public BaseResponse<String> heath() {
        return ResultUtils.success("ok");
    }

}
