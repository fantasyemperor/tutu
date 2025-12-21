package cloud.codechun.tutu.model.entity;

import lombok.Data;

import java.io.Serializable;


/**
 * 消息 对象
 */
@Data
public class MyMessage implements Serializable {
    private String message;
    private String username;


}
