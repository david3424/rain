package org.david.rain.tools;

import org.david.rain.act.utils.entity.HdTable;
import org.david.rain.act.utils.entity.IdEntity;
import org.david.rain.tools.exception.ServiceException;

import java.util.HashMap;
import java.util.Map;

/**
 * davidxu
 */
@HdTable("ss_role2role")
public class RoleBean extends IdEntity {


    public static final Map<Integer, String> IDX_COLUMN_MAP = new HashMap<Integer, String>(4);

    public static final String NUMBER_COLUMN = "int";
    public static final String STRING_COLUMN = "String";


    public static final String USERNAME_COLUMN = "account";
    public static final String ROLENAME_COLUMN = "rolename";

    static {
        IDX_COLUMN_MAP.put(0, USERNAME_COLUMN);
        IDX_COLUMN_MAP.put(1, ROLENAME_COLUMN);
    }

    public static final Map<String, String> COLUMN_TYPE_MAP = new HashMap<String, String>(2);

    static {
        COLUMN_TYPE_MAP.put(USERNAME_COLUMN, STRING_COLUMN);
        COLUMN_TYPE_MAP.put(ROLENAME_COLUMN, STRING_COLUMN);
    }



    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }


    public RoleBean(Map<String, Object> params) {
        if (params.get(ROLENAME_COLUMN) == null
                || params.get(USERNAME_COLUMN) == null) {
            throw new ServiceException("非法数据！");
        }
        this.username = (String) params.get(USERNAME_COLUMN);
        this.rolename = ((String) params.get(ROLENAME_COLUMN));
    }

    private String username;
    private String rolename;
    private String roleid;
    private Integer status;
    private Long id;

    public RoleBean() {
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getRolename() {
        return rolename;
    }

    public void setRolename(String rolename) {
        this.rolename = rolename;
    }

    public String getRoleid() {
        return roleid;
    }

    public void setRoleid(String roleid) {
        this.roleid = roleid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
