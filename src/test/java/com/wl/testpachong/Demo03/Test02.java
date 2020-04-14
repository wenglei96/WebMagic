package com.wl.testpachong.Demo03;

import com.wl.testpachong.pojo.PC;
import com.wl.testpachong.utils.dao;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

import java.sql.*;
import java.util.ArrayList;


//小红书网页，浏览器数据加密查看数据不全，需要将数据下载进行解析
public class Test02 implements PageProcessor {
    //解析页面
    @Override
    public void process(Page page) {

        //将获取到的页面数据转换为jsoup格式
        Document document = page.getHtml().getDocument();
        //System.out.println(document);

        //获取文章块
        Elements select = document.select("div[class=note-list dual-column-layout] div[class=note-item note]");
        //System.out.println(select);


        ArrayList<String> h = new ArrayList<>();
        //获取文章地址
         Elements hrefss = select.select("a[class=image-wrapper]");
         for (Element hrefs : hrefss) {
                String href = "https://www.xiaohongshu.com"+hrefs.attr("href");
                h.add(href);
         }

        ArrayList<String> t = new ArrayList<>();
        //获取文章标题
        Elements titless = select.select("div[class=note-content] h3[class=note-title]");
        for (Element titles : titless) {
            String title = titles.text();
            t.add(title);
        }

        ArrayList<String> n = new ArrayList<>();
        ArrayList<String> u = new ArrayList<>();
        //获取作者名称
        Elements nicknamess = select.select("a[class=note-author-nickname]");
        for (Element nicknames : nicknamess) {
            String nickname = nicknames.text();
            String uhref = "https://www.xiaohongshu.com"+nicknames.attr("href");
            n.add(nickname);
            u.add(uhref);
        }

        ArrayList<String> i = new ArrayList<>();
        //获取作者头像
        Elements imgss = select.select("div[class=avatar-img cube-image normal-image] img");
        for (Element imgs : imgss) {
            String img = imgs.attr("src");
            i.add(img);
        }

        //点赞数
        ArrayList<String> l = new ArrayList<>();
        Elements likess = select.select("span[class=note-likes]");
        for (Element likes: likess) {
            String like = likes.text();
            l.add(like);
        }

        dao dao = new dao();
        ArrayList<PC> strings = new ArrayList<>();

        for (int i1 = 0; i1 < h.size(); i1++) {
            PC pc = new PC();
            String Href = h.get(i1);
            String Title = t.get(i1);
            String Img = i.get(i1);
            String Nickname = n.get(i1);
            String Like = l.get(i1);
            String Userhref = u.get(i1);
            //封装对象
            pc.setHref(Href);
            pc.setTitle(Title);
            pc.setImg(Img);
            pc.setNickname(Nickname);
            pc.setLikes(Like);
            pc.setUserhref(Userhref);
            //System.out.println(Nickname+" "+Title+" "+Like+" "+Href+" "+Img+" "+Userhref);
            strings.add(pc);




        }

        for (int i1 = 0; i1 < strings.size(); i1++) {
            page.putField("files",strings);
            try {
                addGoddess(strings.get(i1));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        /*for (PC pc : strings) {
            System.out.println(pc);
        }*/

    }

    //抓取网络相关配置，包括编码，间隔时间，重试次数
    private Site site= Site.me().setRetryTimes(3).setSleepTime(100);
    @Override
    public Site getSite() {
        return site;
    }

    //执行爬虫的主程序
    public static void main(String[] args) {
        Spider.create(new Test02())
                .addUrl("https://www.xiaohongshu.com/explore") //设置爬取路径
                .thread(5)
                .run();                                 //执行爬虫
    }



    public void addGoddess(PC pc) throws SQLException
    {
        // 获得数据库连接
        Connection conn = dao.getConnection();
        //java.sql.SQLException: Incorrect string value: '\xF0\x9F\x92\xB019...' for 错误，解决
        String sqlCharset = "set names utf8mb4";
        Statement statment = conn.createStatement();
        ResultSet resultSet = statment.executeQuery(sqlCharset);




        String sql = "insert into test(Title,Nickname,Likes,Userhref,Href,Img) " +
                "   values(?,?,?,?,?,?)";
        PreparedStatement ptmt = conn.prepareStatement(sql);
        ptmt.setString(1, pc.getTitle());
        ptmt.setString(2, pc.getNickname());
        ptmt.setString(3, pc.getLikes());
        ptmt.setString(4, pc.getUserhref());
        ptmt.setString(5, pc.getHref());
        ptmt.setString(6, pc.getImg());
        ptmt.execute();
    }
}
