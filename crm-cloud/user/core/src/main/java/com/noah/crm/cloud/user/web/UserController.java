package com.noah.crm.cloud.user.web;

import com.noah.crm.cloud.user.api.dtos.RegisterDto;
import com.noah.crm.cloud.user.api.dtos.UserDto;
import com.noah.crm.cloud.user.domain.User;
import com.noah.crm.cloud.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import static com.noah.crm.cloud.user.api.UserUrl.USER_REGISTER_URL;

/**
 * @author xdw9486
 */
@RestController
@RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = USER_REGISTER_URL, method = RequestMethod.POST)
    public UserDto register(@Valid @RequestBody RegisterDto registerDto) {

        User user = userService.register(registerDto);
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setUsername(user.getUsername());

        return userDto;
    }


}
