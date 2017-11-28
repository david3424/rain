package com.noah.crm.cloud.user.web;

import com.noah.crm.cloud.apis.api.ApisErrorCode;
import com.noah.crm.cloud.user.api.UserErrorCode;
import com.noah.crm.cloud.user.api.UserUrl;
import com.noah.crm.cloud.user.api.dtos.RegisterDto;
import com.noah.crm.cloud.user.api.dtos.UserDto;
import com.noah.crm.cloud.user.test.UserBaseControllerTest;
import com.noah.crm.cloud.utils.TestUtils;
import org.apache.commons.text.RandomStringGenerator;
import org.junit.Test;
import org.springframework.web.client.RestTemplate;

import static com.noah.crm.cloud.utils.TestUtils.createJsonEntity;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.notNullValue;
import static org.junit.Assert.assertThat;

/**
 */
public class UserControllerTest extends UserBaseControllerTest {

//    @Autowired
//    private UserService userService;

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testRegister() {

        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(new RandomStringGenerator.Builder().withinRange('0', 'z').build().generate(8));
        registerDto.setPassword(new RandomStringGenerator.Builder().withinRange('0', 'z').build().generate(8));

        UserDto userDto = restTemplate.postForObject(buildRequestUrl(UserUrl.USER_REGISTER_URL),
                createJsonEntity(registerDto), UserDto.class);

        assertThat(userDto, notNullValue());
        assertThat(userDto.getId(), notNullValue());
        assertThat(userDto.getUsername(), notNullValue());

    }

    /**
     * 测试用户名已存在的异常处理(AppBusinessException)
     */
    @Test
    public void testRegisterWithExistUsername() {

        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(new RandomStringGenerator.Builder().withinRange('0', 'z').build().generate(8));
        registerDto.setPassword(new RandomStringGenerator.Builder().withinRange('0', 'z').build().generate(8));

        UserDto userDto = restTemplate.postForObject(buildRequestUrl(UserUrl.USER_REGISTER_URL),
                createJsonEntity(registerDto), UserDto.class);

        assertThat(userDto, notNullValue());
        assertThat(userDto.getId(), notNullValue());
        assertThat(userDto.getUsername(), notNullValue());

        TestUtils.assertServerError(
                () -> restTemplate.postForObject(buildRequestUrl(UserUrl.USER_REGISTER_URL),
                        createJsonEntity(registerDto), UserDto.class),
                error -> {
                    assertThat(error.getCode(), is(UserErrorCode.UsernameExist.getRespCode()));
                    assertThat(error.getUrl(), notNullValue());
                }
        );

    }


    /**
     * 测试密码为空, 密码长度过长的异常处理(MethodArgumentNotValidException)
     */
    @Test
    public void testRegisterWithInvalidParam() {

        RegisterDto registerDto = new RegisterDto();
        registerDto.setUsername(new RandomStringGenerator.Builder().withinRange('0', 'z').build().generate(8));

        TestUtils.assertServerError(
                () -> restTemplate.postForObject(buildRequestUrl(UserUrl.USER_REGISTER_URL),
                        createJsonEntity(registerDto), Object.class),
                error -> {
                    assertThat(error.getCode(), is(ApisErrorCode.BAD_REQUEST.getRespCode()));
                    assertThat(error.getUrl(), notNullValue());
                }
        );

        registerDto.setPassword(new RandomStringGenerator.Builder().withinRange('0', 'z').build().generate(8));
        TestUtils.assertServerError(
                () -> restTemplate.postForObject(buildRequestUrl(UserUrl.USER_REGISTER_URL),
                        createJsonEntity(registerDto), Object.class),
                error -> {
                    assertThat(error.getCode(), is(ApisErrorCode.BAD_REQUEST.getRespCode()));
                    assertThat(error.getUrl(), notNullValue());
                }
        );


    }

//    /**
//     * 测试访问不存在页面的异常处理(404)
//     */
//    @Test
//    public void testServer404Error() {
//
//        TestUtils.assertServerError(
//                () -> restTemplate.getForObject(buildRequestUrl("/A_URL_NOT_EXIST"), Object.class),
//                error -> {
//                    System.out.println(error);
//                    assertThat(error.getCode(), is(CommonErrorCode.NOT_FOUND.getCode()));
//                    assertThat(error.getMessage(), notNullValue());
//                }
//        );
//
//    }


}
