package com.wl.testpachong.Demo01;

import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;


//页面解析
public class RunoobPageProcessor implements PageProcessor {
    private static  String name = null;
    private static String regex = null;

    // 抓取网站的相关配置，包括编码、重试次数、抓取间隔、超时时间、请求消息头、UA信息等
    //addHeader -- 添加消息头
    private Site site= Site.me().setRetryTimes(3).setSleepTime(1000).setTimeOut(3000).addHeader("Accept-Encoding", "/")
            .setUserAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/54.0.2840.59 Safari/537.36");

    @Override
    public Site getSite() {
        return site;
    }


    //此处为处理函数
    @Override
    public void process(Page page) {
        Html html = page.getHtml();

        if (name == null || regex == null){
            //获取指定的url数据
            String url = page.getRequest().getUrl();
            name = url.substring(url.lastIndexOf('/',url.lastIndexOf('/')-1)+1,url.lastIndexOf('/'));
            //System.out.println("name "+name);
            regex = "http://www.runoob.com/"+name+"/.*";
           // System.out.println("regex "+regex);
        }

        //添加访问
        page.addTargetRequests(html.links().regex(regex).all());
        //获取文章主内容
        Document doc = html.getDocument();
        Element article = doc.getElementById("content");

        //获取markdown文本
        String document = Service.markdown(article);
        //System.out.println(document);

        //处理保存操作
        String fileName = article.getElementsByTag("h1").get(0).text().replace("/","").replace("\\","") + ".md";
        page.putField("fileName",fileName);
        page.putField("content",document);
        page.putField("dir",name);
    }
}
