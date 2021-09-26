package com.xziying.appstorecloud.domain.data;

/**
 * UserConfig
 *
 * @author : xziying
 * @create : 2021-04-14 15:18
 */
public class UserConfig {
    Integer duid;       // 记录id
    Integer dcid;       // 虚拟数据库对象id
    String key;         // key
    String value;       // value
    Boolean noobj;      // 非对象

    public UserConfig(Integer dcid, String key, String value, Boolean noobj) {
        this.dcid = dcid;
        this.key = key;
        this.value = value;
        this.noobj = noobj;
    }

    public UserConfig() {
    }

    public Integer getDuid() {
        return duid;
    }

    public void setDuid(Integer duid) {
        this.duid = duid;
    }

    public Integer getDcid() {
        return dcid;
    }

    public void setDcid(Integer dcid) {
        this.dcid = dcid;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Boolean getNoobj() {
        return noobj;
    }

    public void setNoobj(Boolean noobj) {
        this.noobj = noobj;
    }

    @Override
    public String toString() {
        return "UserConfig{" +
                "duid=" + duid +
                ", dcid=" + dcid +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                ", noobj=" + noobj +
                '}';
    }
}
