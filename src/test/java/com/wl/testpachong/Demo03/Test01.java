package com.wl.testpachong.Demo03;

import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class Test01 implements PageProcessor {

    //解析页面
    @Override
    public void process(Page page) {
        System.out.println(page.getHtml());
        page.putField("div",page.getHtml().css("div.unin_reason_dialog h3").all());

    }

    //抓取网络相关配置，包括编码，间隔时间，重试次数
    private Site site= Site.me().setRetryTimes(3).setSleepTime(100);
    @Override
    public Site getSite() {
        return site;
    }

    //执行爬虫的主程序
    public static void main(String[] args) {
        Spider.create(new Test01())
                    .addUrl("https://blog.csdn.net/nav/ai") //设置爬取路径
                    .run();                                 //执行爬虫
    }
}
