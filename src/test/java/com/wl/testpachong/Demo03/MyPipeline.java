package com.wl.testpachong.Demo03;

import com.wl.testpachong.pojo.PC;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.List;

public class MyPipeline implements Pipeline {
    @Override
    public void process(ResultItems resultItems, Task task) {
        List<PC> files = resultItems.get("files");

        for (PC file : files) {
            System.out.println(file);
        }

    }
}
