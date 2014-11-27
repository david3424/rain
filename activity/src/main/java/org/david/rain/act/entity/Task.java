package org.david.rain.act.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.david.rain.act.utils.entity.HdTable;
import org.david.rain.act.utils.entity.IdEntity;

@HdTable("ss_task")
public class Task extends IdEntity {

    private String title;
    private String description;
    private Long user_id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Task(String title, String description, Long user_id) {
        this.title = title;
        this.description = description;
        this.user_id = user_id;
    }

    public Task() {
    }

    public Task(Long id,String title, String description, Long user_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.user_id = user_id;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }
}
