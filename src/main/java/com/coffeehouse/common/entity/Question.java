package com.coffeehouse.common.entity;

import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import java.util.Date;

/**
 * 问题实体
 */
public class Question extends BaseEntity<Long> {

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @ManyToOne
    @JoinColumn(name = "coffeerid")
    private Coffeer coffeer;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "comment_count")
    private int commentCount;

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

    public Coffeer getCoffeer() {
        return coffeer;
    }

    public void setCoffeer(Coffeer coffeer) {
        this.coffeer = coffeer;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public int getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(int commentCount) {
        this.commentCount = commentCount;
    }
}
