package cloud.codechun.tutu.service;

import cloud.codechun.tutu.model.entity.Job;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author 17763
* @description 针对表【job】的数据库操作Service
* @createDate 2025-12-21 18:46:03
*/
public interface JobService extends IService<Job> {

    void  setJob0(String path);
    void  setJob1(String path);
    void  setJob2(String path);


    void  setError(String path);

}
