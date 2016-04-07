package org.david.rain.webservice;

/**
 * Created by user on 2016/4/7.
 *
 */
public interface ServiceInterface {


    int sendEmailToRole(String username, int zoneid, long roleid, int prizeid, String emailTile, String emailText);

    int verifyRole(String username, Integer zoneid, long roleid);
}
