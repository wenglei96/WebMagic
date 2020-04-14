package com.wl.testpachong.Demo01;

import us.codecraft.webmagic.Spider;

public class ScalaPachong {
    public static void main(String[] args) {
        //1.指定爬取路径
        String url ="http://www.runoob.com/scala/scala-tutorial.html";

        //爬虫控制器   添加页面解析
        Spider spider = Spider.create(new RunoobPageProcessor());
        //添加url(request)
        spider.addUrl(url);
        //添加持久化组件
        spider.addPipeline(new MarkdownSavePipeline());
        //创建线程   执行
        spider.thread(1).run();
    }
}
