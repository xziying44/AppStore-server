package com.xziying.appstorecloud.domain.data;

/**
 * PluginConfig 插件数据库
 *
 * @author : xziying
 * @create : 2021-03-31 16:01
 */
public class PluginConfig {
    Integer pcid;
    Integer uid;
    Integer pid;
    String fromQQ;
    String key;
    String value;

    public PluginConfig() {
    }

    public PluginConfig(Integer uid, Integer pid, String fromQQ, String key, String value) {
        this.uid = uid;
        this.pid = pid;
        this.fromQQ = fromQQ;
        this.key = key;
        this.value = value;
    }

    public Integer getPcid() {
        return pcid;
    }

    public void setPcid(Integer pcid) {
        this.pcid = pcid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
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

    public String getFromQQ() {
        return fromQQ;
    }

    public void setFromQQ(String fromQQ) {
        this.fromQQ = fromQQ;
    }

    @Override
    public String toString() {
        return "PluginConfig{" +
                "pcid=" + pcid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", fromQQ='" + fromQQ + '\'' +
                ", key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
