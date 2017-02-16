package com.greenfox.zerdaReader.service;

import com.greenfox.zerdaReader.ZerdaReaderApplication;
import org.junit.runner.RunWith;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Created by zoloe on 2017. 02. 16..
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = ZerdaReaderApplication.class)
@DataJpaTest
public class SubscriptionServiceTest {
//Todo: ezeket a teszteket meg legalabb meg kene nezni
    public void generateResponseForSubsciptionTest(){
        // existing feed
        // non existind feed
        // wrong url
    }

    public void getIdForBrandNewFeedTest(){

    }

}
