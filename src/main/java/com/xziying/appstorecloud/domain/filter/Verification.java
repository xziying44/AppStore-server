package com.xziying.appstorecloud.domain.filter;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Verification
 *
 * @author : xziying
 * @create : 2021-04-12 19:58
 */
public class Verification {
    Integer fvid;
    Integer uid;
    String clazz;
    String fromQQ;
    Long start;
    Long end;

    public Verification() {
    }

    public Verification(Integer uid, String clazz, String fromQQ, Long start, Long end) {
        this.uid = uid;
        this.clazz = clazz;
        this.fromQQ = fromQQ;
        this.start = start;
        this.end = end;
    }

    public Integer getFvid() {
        return fvid;
    }

    public void setFvid(Integer fvid) {
        this.fvid = fvid;
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

    public Long getStart() {
        return start;
    }

    public void setStart(Long start) {
        this.start = start;
    }

    public Long getEnd() {
        return end;
    }

    public void setEnd(Long end) {
        this.end = end;
    }

    public String getFormatStart(){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(start));
        } catch (Throwable e){
            return "N/A";
        }
    }

    public String getFormatEnd(){
        try {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(end));
        } catch (Throwable e){
            return "N/A";
        }
    }

    @Override
    public String toString() {
        return "Verification{" +
                "fvid=" + fvid +
                ", uid=" + uid +
                ", clazz='" + clazz + '\'' +
                ", fromQQ='" + fromQQ + '\'' +
                ", start=" + start +
                ", end=" + end +
                '}';
    }
}
