package com.xziying.appstorecloud.domain;

/**
 * ConfSwitchNormal
 *
 * @author : xziying
 * @create : 2021-04-09 20:31
 */
public interface ConfSwitchNormal {
    Integer getCsid();

    void setCsid(Integer csid);

    Integer getUid();

    void setUid(Integer uid);

    Integer getWeight();

    void setWeight(Integer weight);

    Integer getState();

    void setState(Integer state);

    String getClazz();

    void setClazz(String clazz);
}
