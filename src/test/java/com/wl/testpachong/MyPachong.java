package com.wl.testpachong;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.processor.PageProcessor;


//WebMagic框架
public class MyPachong implements PageProcessor {

    // 抓取网站的相关配置，包括编码、抓取间隔、重试次数等
    private Site site = Site.me().setRetryTimes(3).setSleepTime(100);

    @Override
    public Site getSite() {
        return site;
    }

    @Override
    public void process(Page page) {
        if (!page.getUrl().regex("https://www.cnblogs.com/[a-z 0-9 -]+/p/[0-9]{7}.html").match()) {
            page.addTargetRequests(
                    page.getHtml().xpath("//*[@id="+"mainContent]/div/div/div[@class="+"postTitle"+"]/a/@href").all());
        }else {
            page.putField(page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/text()").toString(),
                    page.getHtml().xpath("//*[@id=\"cb_post_title_url\"]/@href").toString());
        }


    }


    public static void main(String[] args) {
        Spider spider = Spider.create(new MyPachong());
        spider.addUrl("https://www.cnblogs.com/justcooooode/p/7913365.html");
        spider.addPipeline(new ConsolePipeline()).run();
    }
}



