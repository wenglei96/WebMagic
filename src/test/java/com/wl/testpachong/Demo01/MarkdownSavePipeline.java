package com.wl.testpachong.Demo01;

import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;

public class MarkdownSavePipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        try {
            String fileName = resultItems.get("fileName");
            String document = resultItems.get("content");
            String dir = resultItems.get("dir");
            Service.saveFile(document,fileName,dir);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}