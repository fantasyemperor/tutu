package cloud.codechun.tutu.model.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

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


    private String pname;

    @Override
    public boolean equals(Object that) {
        if (this == that) {
            return true;
        }
        if (that == null) {
            return false;
        }
        if (getClass() != that.getClass()) {
            return false;
        }
        Errordws other = (Errordws) that;
        return (this.getName() == null ? other.getName() == null : this.getName().equals(other.getName()))
            && (this.getReason() == null ? other.getReason() == null : this.getReason().equals(other.getReason())&& (this.getPname() == null ? other.getPname() == null : this.getPname().equals(other.getPname())));
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((getName() == null) ? 0 : getName().hashCode());
        result = prime * result + ((getReason() == null) ? 0 : getReason().hashCode());
        result = prime * result + ((getPname() == null) ? 0 : getPname().hashCode());
        return result;
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
        sb.append("]");
        return sb.toString();
    }
}