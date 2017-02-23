package com.kyh.crawler4j;

import com.google.common.io.Files;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.BinaryParseData;
import edu.uci.ics.crawler4j.url.WebURL;

import java.io.File;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * Created by kongyunhui on 2017/2/22.
 */
public class ImageCrawler extends WebCrawler{
    private static final Pattern FILTER = Pattern.compile(".*(\\.(css|js|mid|mp2|mp3|mp4|wav|avi|mov|mpeg|ram|m4v|pdf" +
            "|rm|smil|wmv|swf|wma|zip|rar|gz))$");

    private static final Pattern imgPatterns = Pattern.compile(".*(\\.(bmp|gif|jpe?g|png|tiff?))$");

    private static File storageFolder;
    private static String[] crawlDomains;

    public static void config(String[] domain, String storageFolderName){
        crawlDomains = domain;

        storageFolder = new File(storageFolderName);
        if(!storageFolder.exists()){
            storageFolder.mkdir();
        }
    }

    @Override
    public boolean shouldVisit(Page referringPage, WebURL url) {
        String href = url.getURL().toLowerCase();

        if(!FILTER.matcher(href).matches()){
            return true;
        }
        if(imgPatterns.matcher(href).matches()){
            return true;
        }
        for(String domain : crawlDomains){
            if(href.startsWith(domain)){
                return true;
            }
        }

        return false;
    }

    @Override
    public void visit(Page page) {
        String url = page.getWebURL().getURL();
        System.out.println("visit url: " + url);

        // We are only interested in processing images which are bigger than 10k
        if(!imgPatterns.matcher(url).matches() ||
                !( (page.getParseData() instanceof BinaryParseData) ||
                        (page.getContentData().length < (1024*10))) ){
            return;
        }

        // get a unique name for storing this image
        String extension = url.substring(url.lastIndexOf("."));
        String hashedName = UUID.randomUUID() + extension;

        // store image
        String fileName = storageFolder.getAbsolutePath() + "/" + hashedName;
        try {
            Files.write(page.getContentData(), new File(fileName));
            logger.info("Store: {}", url);
        }catch(Exception e){
            logger.error("Failed to write file:{}, url: {}, error: {}", fileName, url, e.getMessage());
        }
    }
}
