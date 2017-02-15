package com.coffeehouse.common.entity;


import javax.persistence.*;
import java.util.Date;

/**
 * 登陆用户taken实体
 */

@Entity
@Table(name = "coffeer_taken")
public class CoffeerTaken extends BaseEntity<Long> {

    @Column(name = "taken")
    private String taken;

    @Column(name = "create_date")
    private Date createDate;

    @Column(name = "expriation_date")
    private Date expriationDate;

    @Column(name = "status")
    private int status;

    @ManyToOne
    @JoinColumn(name = "coffeer_id")
    private Coffeer coffeer;

    public Coffeer getCoffeer() {
        return coffeer;
    }

    public void setCoffeer(Coffeer coffeer) {
        this.coffeer = coffeer;
    }

    public String getTaken() {
        return taken;
    }

    public void setTaken(String taken) {
        this.taken = taken;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getExpriationDate() {
        return expriationDate;
    }

    public void setExpriationDate(Date expriationDate) {
        this.expriationDate = expriationDate;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
