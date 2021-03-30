package org.cp.crawler;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.cp.crawler.dao.LinkPoolDao;
import org.cp.crawler.dao.NewsDao;
import org.cp.crawler.dao.h2Impl.LinkPoolH2Impl;
import org.cp.crawler.dao.h2Impl.NewsDaoH2Impl;
import org.cp.crawler.dao.h2Impl.ProcessedLinkH2Impl;
import org.cp.crawler.dao.localImpl.LinkPoolLocalImpl;
import org.cp.crawler.dao.ProcessedLinkDao;
import org.cp.crawler.dao.localImpl.ProcessedLinkLocalImpl;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.SQLException;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-29
 */
@Slf4j
public class Main {

    private static LinkPoolDao linkPoolDao = new LinkPoolH2Impl();
    private static ProcessedLinkDao processedLinkDao = new ProcessedLinkH2Impl();
    private static NewsDao newsDao = new NewsDaoH2Impl();

    public static void main(String[] args) throws SQLException {

        while (!linkPoolDao.isEmpty()) {
            String link = getOneLinkFromLinkPool();
            if (hasProcessed(link) || !isCorrectLink(link)) {
                linkPoolDao.remove(link);
                continue;
            }
            handleLink(link);
        }

    }

    private static void handleLink(String link) throws SQLException {
        link = linkPreHandle(link);
        //发http请求，获取到html
        String htmlString = invokeHttpGetHtml(link);
        if (!StringUtils.hasText(htmlString)) {
            return;
        }
        //解析html
        resolveHtml(htmlString, link);

        //加入到已处理的池子中
        processedLinkDao.add(link);
    }

    private static void resolveHtml(String htmlString, String link) throws SQLException {
        Document doc = Jsoup.parse(htmlString);
        getNewLinks(doc);
        storagePage(doc, link);
    }

    private static void storagePage(Document doc, String link) throws SQLException {
        Elements articles = doc.select("article");
        if (articles.isEmpty()) {
            return;
        }
        for (Element article : articles) {
            Elements titles = article.getElementsByClass("art_tit_h1");
            if (titles != null && !titles.isEmpty()) {
                log.info(link);
                log.info(titles.get(0).text());
                log.info(processedLinkDao.count() + "/" + linkPoolDao.count());
            }
        }
    }


    private static void getNewLinks(Document doc) {
        Elements linkTags = doc.select("a");
        for (Element linkTag : linkTags) {
            String href = linkTag.attr("href");
            if (isCorrectLink(href)) {
                linkPoolDao.add(href);
            }
        }
    }

    private static String invokeHttpGetHtml(String link) {
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            HttpGet httpGet = new HttpGet(link);
            httpGet.addHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.90 Safari/537.36");
            return httpClient.execute(httpGet, httpResponse -> {
                HttpEntity entity = httpResponse.getEntity();
                return entity != null ? EntityUtils.toString(entity) : null;
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        return null;
    }

    private static String linkPreHandle(String link) {
        if (link.startsWith("//")) {
            return "https:" + link;
        }
        return link;
    }

    private static boolean isCorrectLink(String link) {
        if (!StringUtils.hasText(link)) {
            return false;
        }
        if ("https://sina.cn/".equals(link)) {
            return true;
        }
        return link.contains("news.sina.cn");
    }

    private static boolean hasProcessed(String link) {
        return processedLinkDao.contains(link);
    }

    private static String getOneLinkFromLinkPool() {
        return linkPoolDao.getAndRemove();
    }

}
