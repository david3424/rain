package org.david.rain.boot.start.service.crm;

import org.david.rain.boot.start.pojo.KoTask;

import java.util.Map;

public interface IAccountModify {


    Map<String, String> createAccount(KoTask mobileDto, String encrypt_mobile);

}
