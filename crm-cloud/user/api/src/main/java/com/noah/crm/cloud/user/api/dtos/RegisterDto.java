package com.noah.crm.cloud.user.api.dtos;

import com.noah.crm.cloud.user.api.utils.RegExpUtils;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author xdw9486
 */
public class RegisterDto {

    @NotBlank(message = "用户名不能为空")
    @Pattern(regexp = RegExpUtils.USERNAME_REG_EXP, message = "用户名请输入2-20位，可由中文、英文或数字或_或-组成")
    private String username;

    @NotBlank(message = "密码不能为空")
    @Size(min = 4, max = 20, message = "密码长度需要为4-20位")
    private String password;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
