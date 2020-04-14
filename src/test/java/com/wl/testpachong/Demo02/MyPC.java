package com.wl.testpachong.Demo02;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;
import us.codecraft.webmagic.selector.Selectable;

import java.util.List;

public class MyPC  implements PageProcessor {

    public static void main(String[] args) {
        Spider spider = Spider.create(new MyPC());
        spider.addUrl("https://www.xiaohongshu.com/discovery/item/5e92ee430000000001009ccd");
        spider.addPipeline(new ConsolePipeline()).run();
    }


    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(1000).setSleepTime(1000).setCharset("utf-8");

    @Override
    public Site getSite() {
        return site;
    }



    @Override
    public void process(Page page) {
        //获取html二进制数据
        Html html = page.getHtml();
        System.out.println(html);

        //章节
       // Selectable xpath = html.xpath("/html/body/div[2]/div[2]/div[1]/div[3]/div/p[11]");
        //System.out.println(xpath);



    }


}
