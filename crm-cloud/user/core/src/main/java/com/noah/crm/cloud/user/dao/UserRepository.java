package com.noah.crm.cloud.user.dao;

import com.noah.crm.cloud.user.domain.User;
import org.springframework.data.repository.PagingAndSortingRepository;

/**
 * @author xdw9486
 */
public interface UserRepository extends PagingAndSortingRepository<User, Long>, UserRepositoryCustom {



}
