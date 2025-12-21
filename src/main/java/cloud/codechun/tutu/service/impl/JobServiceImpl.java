package cloud.codechun.tutu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cloud.codechun.tutu.model.entity.Job;
import cloud.codechun.tutu.service.JobService;
import cloud.codechun.tutu.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
* @author 17763
* @description 针对表【job】的数据库操作Service实现
* @createDate 2025-12-21 18:46:03
*/
@Service
public class JobServiceImpl extends ServiceImpl<JobMapper, Job>
    implements JobService{


    @Autowired
    private JobMapper jobMapper;


    @Override
    public void setJob0(String path) {

        Job job = new Job();
        job.setStatus(0);
        job.setCreatetime(new Date());
        job.setJobname(path);
        jobMapper.insert(job);

    }

    @Override
    public void setJob1(String path) {



        QueryWrapper<Job> queryWrapper = new QueryWrapper<Job>();
        queryWrapper.eq("jobname",path);

        Job jobTe = new Job();
        jobTe.setStatus(1);
        jobMapper.update(jobTe,queryWrapper);

    }

    @Override
    public void setJob2(String path) {
        QueryWrapper<Job> queryWrapper = new QueryWrapper<Job>();
        queryWrapper.eq("jobname",path);

        Job jobTe = new Job();
        jobTe.setStatus(2);
        jobMapper.update(jobTe,queryWrapper);





    }

    @Override
    public void setError(String path) {
        QueryWrapper<Job> queryWrapper = new QueryWrapper<Job>();
        queryWrapper.eq("jobname",path);

        Job jobTe = new Job();
        jobTe.setIsError(1);
        jobMapper.update(jobTe,queryWrapper);


    }

}




