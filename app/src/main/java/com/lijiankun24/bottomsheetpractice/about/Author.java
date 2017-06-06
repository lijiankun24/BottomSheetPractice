package com.lijiankun24.bottomsheetpractice.about;

/**
 * Author.java
 * <p>
 * Created by lijiankun on 17/5/2.
 */

public class Author {

    private String github = null;

    private String weibo = null;

    private String blog = null;

    private String mail = null;

    private String jianshu = null;

    public Author() {
        github = "https://github.com/lijiankun24";
        weibo = "http://weibo.com/lijiankun24";
        blog = "http://lijiankun24.com";
        mail = "jiankunli24@gmail.com";
        jianshu = "http://www.jianshu.com/u/1abe21b7ff5f";
    }

    public String getGithub() {
        return github;
    }

    public String getWeibo() {
        return weibo;
    }

    public String getBlog() {
        return blog;
    }

    public String getMail() {
        return mail;
    }

    public String getJianshu() {
        return jianshu;
    }
}
