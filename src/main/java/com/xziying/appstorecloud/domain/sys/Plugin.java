package com.xziying.appstorecloud.domain.sys;

/**
 * Plugin
 *
 * @author : xziying
 * @create : 2021-03-31 15:38
 */
public class Plugin {
    Integer pid;
    String name;
    String author;
    String version;
    Integer score; // 1 - 50
    String clazz;
    String link;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getClazz() {
        return clazz;
    }

    public void setClazz(String clazz) {
        this.clazz = clazz;
    }

    @Override
    public String toString() {
        return "Plugin{" +
                "pid=" + pid +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", version='" + version + '\'' +
                ", score=" + score +
                ", clazz='" + clazz + '\'' +
                ", link='" + link + '\'' +
                '}';
    }
}
