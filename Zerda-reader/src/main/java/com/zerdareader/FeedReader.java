package com.zerdareader;

import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.IOException;


/**
 * Created by ${rudolfps} on 2017.01.18..
 */

@Component
@AllArgsConstructor
@Getter
@NoArgsConstructor
public class FeedReader {

    public TempSyndFeedStorage getSyndFeedStorageFromRssUrl(TempSyndFeedStorage tempSyndFeedStorage) throws IOException, FeedException {
        SyndFeedInput input = new SyndFeedInput();
        tempSyndFeedStorage.setSyndFeed(input.build(new XmlReader(tempSyndFeedStorage.createUrl())));
        return tempSyndFeedStorage;
    }
}