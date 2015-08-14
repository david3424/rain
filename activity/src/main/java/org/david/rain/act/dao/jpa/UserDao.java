package org.david.rain.act.dao.jpa;

import org.david.rain.act.entity.TaskJpa;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.RepositoryDefinition;

/**
 * Created by david on 2014/12/29.
 *
 */
@RepositoryDefinition(domainClass = TaskJpa.class, idClass = Long.class)
//public interface  UserDao extends Repository<TaskJpa, Long> {
public interface  UserDao {

     void save(TaskJpa accountInfo);

    // 你需要做的，仅仅是新增如下一行方法声明
    @Query("select a from TaskJpa a where a.id = ?1")
     TaskJpa findById(Long id);
}
