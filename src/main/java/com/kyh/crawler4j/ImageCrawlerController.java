package com.kyh.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

/**
 * Created by kongyunhui on 2017/2/22.
 */
public class ImageCrawlerController {
    public static void main(String[] args) throws Exception{
        // args
        String rootFolder = "data/crawl/root";
        int numberOfCralwers = 100;
        String storageFolder = rootFolder + "/image";
        String[] crawlDomains = {"http://www.yuyingshuo.cn/"};

        // config
        CrawlConfig config = new CrawlConfig();
        config.setIncludeBinaryContentInCrawling(true);
        config.setCrawlStorageFolder(rootFolder);
        config.setResumableCrawling(true);

        // create controller
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        // set seed url
        for(String domain : crawlDomains){
            controller.addSeed(domain);
        }

        // start crawler
        ImageCrawler.config(crawlDomains, storageFolder);
        controller.start(ImageCrawler.class, numberOfCralwers);
    }
}
