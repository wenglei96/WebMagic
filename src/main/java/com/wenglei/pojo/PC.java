package com.wenglei.pojo;

public class PC {

    private String href;
    private String title;
    private String nickname;
    private String img;
    private String likes;
    private String userhref;

    public PC() {
    }

    public PC(String href, String title, String nickname, String img, String likes, String userhref) {
        this.href = href;
        this.title = title;
        this.nickname = nickname;
        this.img = img;
        this.likes = likes;
        this.userhref = userhref;
    }

    public String getHref() {
        return href;
    }

    public String getTitle() {
        return title;
    }

    public String getNickname() {
        return nickname;
    }

    public String getImg() {
        return img;
    }

    public String getLikes() {
        return likes;
    }

    public String getUserhref() {
        return userhref;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public void setLikes(String likes) {
        this.likes = likes;
    }

    public void setUserhref(String userhref) {
        this.userhref = userhref;
    }

    @Override
    public String toString() {
        return "PC{" +
                "href='" + href + '\'' +
                ", title='" + title + '\'' +
                ", nickname='" + nickname + '\'' +
                ", img='" + img + '\'' +
                ", like='" + likes + '\'' +
                ", userhref='" + userhref + '\'' +
                '}';
    }
}
