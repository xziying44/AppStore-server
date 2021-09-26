package com.xziying.appstorecloud.domain.conf;

import com.xziying.appstorecloud.domain.ConfSwitchNormal;

import java.io.Serializable;

/**
 * TestConfSwitch
 *
 * @author : xziying
 * @create : 2021-04-09 23:04
 */
public class ConfSwitch implements ConfSwitchNormal, Serializable {
    Integer csid;       // 配置id
    Integer uid;        // 用户id
    String clazz;
    Integer weight;     // 权值
    Integer state;      // 开关

    public ConfSwitch(Integer csid, Integer uid, String clazz, Integer weight, Integer state) {
        this.csid = csid;
        this.uid = uid;
        this.clazz = clazz;
        this.weight = weight;
        this.state = state;
    }

    public ConfSwitch(Integer uid, String clazz, Integer weight, Integer state) {
        this.uid = uid;
        this.clazz = clazz;
        this.weight = weight;
        this.state = state;
    }
    @Override
    public Integer getCsid() {
        return csid;
    }
    @Override
    public void setCsid(Integer csid) {
        this.csid = csid;
    }
    @Override
    public Integer getUid() {
        return uid;
    }
    @Override
    public void setUid(Integer uid) {
        this.uid = uid;
    }
    @Override
    public Integer getWeight() {
        return weight;
    }
    @Override
    public void setWeight(Integer weight) {
        this.weight = weight;
    }
    @Override
    public Integer getState() {
        return state;
    }
    @Override
    public void setState(Integer state) {
        this.state = state;
    }
    @Override
    public String getClazz() {
        return clazz;
    }
    @Override
    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "TestConfSwitch{" +
                "csid=" + csid +
                ", uid=" + uid +
                ", weight=" + weight +
                ", state=" + state +
                ", clazz='" + clazz + '\'' +
                '}';
    }
}
