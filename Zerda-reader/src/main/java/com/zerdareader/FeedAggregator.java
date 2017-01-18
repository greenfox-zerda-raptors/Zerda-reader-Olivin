/*
package com.zerdareader;

/*
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.feed.synd.SyndFeedImpl;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.SyndFeedOutput;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class FeedAggregator {

    SyndFeed feed;

    public FeedAggregator() {
        SyndFeed feed = new SyndFeedImpl();
    }

    public SyndFeedOutput createNewRssFeed() {
        feed.setFeedType("");

        feed.setTitle("Olivin test RSS feed");
        feed.setDescription("First test to check if we can create a new RSS feed out of saved feed items in db");
        feed.setAuthor("Zolo, Zsofi");
        feed.setLink("hmm no idea");
/*
        List entries = new ArrayList();
        feed.setEntries(entries);

        for (int i = 1; i < args.length; i++) {
            URL inputUrl = new URL(args[i]);
            SyndFeedInput input = new SyndFeedInput();
            SyndFeed inFeed = input.build(new XmlReader(inputUrl));

            entries.addAll(inFeed.getEntries());

        }

        SyndFeedOutput output = new SyndFeedOutput();
        output.output(feed, new PrintWriter(System.out));

        ok = true;
    } catch(
    Exception ex)

    {
        System.out.println("ERROR: " + ex.getMessage());
    }
}

 */
/* //      if(!ok){
                System.out.println();
                System.out.println("FeedAggregator aggregates different feeds into a single one.");
                System.out.println("The first parameter must be the feed type for the aggregated feed.");
                System.out.println(" [valid values are: rss_0.9, rss_0.91, rss_0.92, rss_0.93, ]");
                System.out.println(" [                  rss_0.94, rss_1.0, rss_2.0 & atom_0.3  ]");
                System.out.println("The second to last parameters are the URLs of feeds to aggregate.");
                System.out.println();
                }
                }
*//*

//    public static void main(String[] args) {
//        boolean ok = false;
//        if (args.length >= 2) {
//            try {
//                String outputType = args[0];
//
//                SyndFeed feed = new SyndFeedImpl();
//                feed.setFeedType(outputType);
//
//                feed.setTitle("Olivin test RSS feed");
//                feed.setDescription("First test to check if we can create a new RSS feed out of saved feed items in db");
//                feed.setAuthor("Zolo, Zsofi");
//                feed.setLink("hmm no idea");
//
//                List entries = new ArrayList();
//                feed.setEntries(entries);
//
//                for (int i = 1; i < args.length; i++) {
//                    URL inputUrl = new URL(args[i]);
//
//                    SyndFeedInput input = new SyndFeedInput();
//                    SyndFeed inFeed = input.build(new XmlReader(inputUrl));
//
//                    entries.addAll(inFeed.getEntries());
//
//                }
//
//                SyndFeedOutput output = new SyndFeedOutput();
//                output.output(feed, new PrintWriter(System.out));
//
//                ok = true;
//            } catch (Exception ex) {
//                System.out.println("ERROR: " + ex.getMessage());
//            }
//        }
//
//        if (!ok) {
//            System.out.println();
//            System.out.println("FeedAggregator aggregates different feeds into a single one.");
//            System.out.println("The first parameter must be the feed type for the aggregated feed.");
//            System.out.println(" [valid values are: rss_0.9, rss_0.91, rss_0.92, rss_0.93, ]");
//            System.out.println(" [                  rss_0.94, rss_1.0, rss_2.0 & atom_0.3  ]");
//            System.out.println("The second to last parameters are the URLs of feeds to aggregate.");
//            System.out.println();
//        }
//    }

                }
*/
