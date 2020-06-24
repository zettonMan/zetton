package com.zetton.thymeleaf.entity;

import com.zetton.thymeleaf.common.entity.Manager;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

/**
 * Description: 后台运维管理员信息
 */
@Data
@NoArgsConstructor
@ToString
public class ManagerInfo extends Manager implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 状态
     */
    private String stateStr;
    /**
     * 所属项目id列表（逗号分隔）
     */
    private String pids;
    /**
     * 所属项目名列表（逗号分隔）
     */
    private String pnames;
    /**
     * 所属项目id列表
     */
    private List<Integer> pidsList;

    /**
     * 一个管理员具有多个角色
     */
    private List<SysRole> roles;// 一个用户具有多个角色

    /**
     * 密码盐
     */
    public String getCredentialsSalt() {
        return getUsername() + getSalt();
    }
}