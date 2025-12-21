package cloud.codechun.tutu.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.util.Date;
import lombok.Data;

/**
 * 
 * @TableName job
 */
@TableName(value ="job")
@Data
public class Job {
    /**
     * 
     */
    @TableId(type = IdType.AUTO)
    private Integer id;

    /**
     * 0-未开始 1-进行中 2-已完成
     */
    private Integer status;

    /**
     * 
     */
    private Date createtime;

    /**
     * 
     */
    private String jobname;

    private Integer isError;
}