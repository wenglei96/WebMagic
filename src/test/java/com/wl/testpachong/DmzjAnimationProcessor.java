package com.wl.testpachong;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class DmzjAnimationProcessor implements PageProcessor {
    int myid = 0;
    int size =10;
    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(1000).setSleepTime(1000).setCharset("utf8");
    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        Html html = page.getHtml();
        size++;
        myid++;
        int id = myid;

        String hahawebname = html.xpath("//div[@class=\"odd_anim_title_tnew\"]/div[@class=\"tvversion\"]/a/span[@class=\"anim_title_text\"]/h1/text()").get();//得分
        String goal = html.xpath("//div[@class=\"anim_star\"]/ul/li[@id=\"anim_score_info\"]/span[@class=\"points_text\"]/text()").get();//得分
        String mentotalold = html.xpath("//div[@class=\"anim_star\"]/ul/li[@id=\"score_statistics\"]/span[@id=\"score_count_span\"]/text()").get();//人数
        String mentotal = mentotalold.replaceAll("人评分","");
        String content = html.xpath("//div[@class=\"odd_anim_title_mnew\"]/p/span[@id=\"gamedescshort\"]/text()").get();//内容
        String contentdetail = html.xpath("//div[@class=\"odd_anim_title_mnew\"]/p/span[@id=\"gamedescall\"]/text()").get();//内容
        System.out.println("hahawebname: "+ hahawebname);
        System.out.println("goal: "+goal);
        System.out.println("mentotal: "+ mentotal);
        System.out.println("content: "+ content);
        System.out.println("contentdetail: "+ contentdetail);
        List<Selectable> nodes = html.xpath("//div[@class=\"anim_attributenew_text\"]/ul/li").nodes();
        for(Selectable item : nodes){
            String tmp = item.get();
            if(tmp.contains("动画种类")) {//动画种类 : 剧场版
                String antag11 = tmp.replaceAll("</?[^>]+>","");
                String antag = antag11.replaceAll("动画种类 : ","");
                System.out.println(antag);

            }//日文名 : 暂无
            if(tmp.contains("日文名")) {
                String japanname11 = tmp.replaceAll("</?[^>]+>","");
                String japanname = japanname11.replaceAll("日文名 : ","");
                if(japanname.contains("暂无")){japanname = null;}
                System.out.println(japanname);
            }//别名 : 大闹天宫 上下集 / The Monkey King
            if(tmp.contains("别名")) {
                String allname11 = tmp.replaceAll("</?[^>]+>","");
                String allname = allname11.replaceAll("别名 : ","");
                if(allname.contains("暂无")){allname = null;}
                System.out.println(allname);
            }//首播时间 : 暂无
            if(tmp.contains("首播")) {
                String year11 = tmp.replaceAll("</?[^>]+>","");
                String year1111 = year11.replaceAll("首播时间 : ","");
                String year = year1111;
                if(year1111.contains("暂无")){year = null;}
                System.out.println(year);
            }
            if(tmp.contains("播放状态")) {
                String state11 = tmp.replaceAll("</?[^>]+>","");
                String state1111 = state11.replaceAll("播放状态 : ","");
                String state = state1111;
                if(state.contains("暂无")){state = null;}
                System.out.println(state);
            }
            if(tmp.contains("剧情类型")) {
                String tag11 = tmp.replaceAll("</?[^>]+>","");
                String tag1111 = tag11.replaceAll("剧情类型 : ","");
                String tag = tag1111.replaceAll(" "," / ");
                System.out.println(tag);
            }//原作 : 暂无
            if(tmp.contains("原作")) {
                String original11 = tmp.replaceAll("</?[^>]+>","");
                String original1111 = original11.replaceAll("原作 :","");
                String original = original1111;
                if(original.contains("暂无")){original = null;}
                System.out.println(original);
            }//监督 : 万籁鸣 / 唐澄
            if(tmp.contains("监督")) {
                String screenwriter11 = tmp.replaceAll("</?[^>]+>","");
                String screenwriter1111 = screenwriter11.replaceAll("监督 :","");
                String screenwriter = screenwriter1111;
                if(screenwriter.contains("暂无")){screenwriter = null;}
                System.out.println(screenwriter);
            }//制作公司 : 上海美术电影制片厂
            if(tmp.contains("制作公司")) {
                String company11 = tmp.replaceAll("</?[^>]+>","");
                String company1111 = company11.replaceAll("制作公司 :","");
                company1111 = company1111 +" / "+ company1111 +"公司";
                String company = company1111;
                if(company.contains("暂无")){company = null;}
                System.out.println(company);
            }//官方网站 : 暂无
            if(tmp.contains("官方网站")) {
                String website =  tmp.replaceAll(".*?href=|target(.*)","");
                if(website.contains("暂无")){website = null;}
                System.out.println(website);
            }
        }
        String url = "http://donghua.dmzj.com/donghua_info/"+size+".html";
        //new DmzjAnimationDao().add(dmzjAnimation);这个是fangdaomysql数据库的，你可以设置其他形式，比如说txt文件什么的
    }



    public static void main(String[] args) {
        int username = 10;
        DmzjAnimationProcessor my = new DmzjAnimationProcessor();
        long startTime, endTime;
        System.out.println("开始爬取...");

        for(;username<=15000;username++) {
            startTime = System.currentTimeMillis();
            Spider.create(my).addUrl("http://donghua.dmzj.com/donghua_info/" + username + ".html").thread(5).run();
            endTime = System.currentTimeMillis();
            System.out.println("爬取结束，耗时约" + ((endTime - startTime) / 1000) + "秒");
        }
    }
}
