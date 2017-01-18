package com.zerdareader.Controller;

import com.zerdareader.Model.TestJson;
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
@RequestMapping(value = "/list")
    public TestJson myJson(){
        return new TestJson(counter.incrementAndGet(), "valami");
    }
}
