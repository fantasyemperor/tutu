package cloud.codechun.tutu.model.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.Objects;

/**
 *
 * @TableName errorDws
 */
@TableName(value ="errorDws")
@Data
public class Errordws {
    /**
     *
     */
    private String name;

    /**
     *
     */
    private Character reason;

    private Date createTime;

    private String pname;

    private String userName;

    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Errordws other = (Errordws) o;

        return Objects.equals(name, other.name)
                && Objects.equals(reason, other.reason)
                && Objects.equals(pname, other.pname)
                && Objects.equals(userName, other.userName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, reason, pname,userName);
    }


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", name=").append(name);
        sb.append(", reason=").append(reason);
        sb.append(", pname=").append(pname);
        sb.append(", user=").append(userName);
        sb.append("]");
        return sb.toString();
    }
}