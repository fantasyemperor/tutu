package cloud.codechun.tutu.service;

import cloud.codechun.tutu.model.entity.Errordws;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 17763
* @description 针对表【errorDws】的数据库操作Service
* @createDate 2025-11-30 15:55:14
*/
public interface ErrordwsService extends IService<Errordws> {


    //拒识分析
    void jushi(String path);




    //导出为原因表


    //导出为总结表

}
