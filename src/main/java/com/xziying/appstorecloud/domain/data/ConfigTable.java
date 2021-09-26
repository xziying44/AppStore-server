package com.xziying.appstorecloud.domain.data;

/**
 * Configtable
 *
 * @author : xziying
 * @create : 2021-04-14 15:15
 */
public class ConfigTable {
    Integer dcid;
    Integer uid;
    String clazz;
    String fromQQ;
    Integer num;

    public ConfigTable() {
    }

    public ConfigTable(Integer uid, String clazz, String fromQQ) {
        this.uid = uid;
        this.clazz = clazz;
        this.fromQQ = fromQQ;
        num = 0;
    }

    public Integer getDcid() {
        return dcid;
    }

    public void setDcid(Integer dcid) {
        this.dcid = dcid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    public String getFromQQ() {
        return fromQQ;
    }

    public void setFromQQ(String fromQQ) {
        this.fromQQ = fromQQ;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Override
    public String toString() {
        return "ConfigTable{" +
                "dcid=" + dcid +
                ", uid=" + uid +
                ", clazz='" + clazz + '\'' +
                ", fromQQ='" + fromQQ + '\'' +
                ", num=" + num +
                '}';
    }
}
