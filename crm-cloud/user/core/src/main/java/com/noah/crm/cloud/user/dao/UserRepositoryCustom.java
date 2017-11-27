package com.noah.crm.cloud.user.dao;



import com.noah.crm.cloud.common.dao.AbstractRepository;

import java.util.Optional;

/**
 */
public interface UserRepositoryCustom extends AbstractRepository {

    /**
     * 用户名是否存在
     * @param username
     * @param userId
     * @return
     */
    boolean isUsernameExist(String username, Optional<Integer> userId);

}
