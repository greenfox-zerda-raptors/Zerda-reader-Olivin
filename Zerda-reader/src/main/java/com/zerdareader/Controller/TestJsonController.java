package com.zerdareader.Controller;

import com.zerdareader.FeedItem;
import com.zerdareader.FeedService;
import com.zerdareader.Model.TestJson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.atomic.AtomicLong;

/**
 * Created by zoloe on 2017. 01. 18..
 */
@RestController
public class TestJsonController {
    private final AtomicLong counter = new AtomicLong();

    @Autowired
    FeedService feedService;

    @RequestMapping(value = "/list")
    public TestJson myJson(){
        return new TestJson(counter.incrementAndGet(), "valami");
    }

    @RequestMapping(value = "/j")
    public FeedItem feedItemJson(){
        return feedService.getFeedItem(1L);
    }

}
