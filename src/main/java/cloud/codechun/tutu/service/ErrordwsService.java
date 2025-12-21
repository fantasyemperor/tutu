package cloud.codechun.tutu.service;

import cloud.codechun.tutu.model.entity.Errordws;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 17763
* @description 针对表【errorDws】的数据库操作Service
* @createDate 2025-11-30 15:55:14
*/
public interface ErrordwsService extends IService<Errordws> {


//    //拒识分析 test
//    void jushi(String path);
//




    // 使用线程池调用ai分析文件夹 path填单台DWS设备存图文件绝对路径
    void  run(String path,String userName);




    void getResult(String path);


}
