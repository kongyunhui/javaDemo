package com.kyh.crawler4j;

import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;

public class BasicCrawlerController {
    public static void main(String[] args) throws Exception {
        int numberOfCrawlers = 7; // the number of concurrent threads

        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder("data/crawl/root"); // set folder
        config.setPolitenessDelay(1000); // 1000 milliseconds between requests
        config.setMaxDepthOfCrawling(2); // set the maximum crawl depth, default unlimited
        config.setMaxPagesToFetch(1000); // set the maximum number of pages to crawl
        config.setIncludeBinaryContentInCrawling(false); // example: pdf、images
//        config.setProxyHost("proxyserver.example.com"); // set a proxy
//        config.setProxyPort(8080);
//        config.setProxyUsername(username);
//        config.setProxyPassword(password);
        config.setResumableCrawling(true); // resumable feature (Note: need to delete the contents of rootFolder)

        // Instantiate the controller for this crawl.
        PageFetcher pageFetcher = new PageFetcher(config);
        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);
        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);

        /*
         * For each crawl, you need to add some seed urls. These are the first
         * URLs that are fetched and then the crawler starts following links
         * which are found in these pages
         *
         * 仅访问以这些开头的url （url过滤机制）
         */
        controller.addSeed("http://www.ics.uci.edu/");
        controller.addSeed("http://www.ics.uci.edu/~lopes/");
        controller.addSeed("http://www.ics.uci.edu/~welling/");

        controller.start(BasicCrawler.class, numberOfCrawlers);
    }
}