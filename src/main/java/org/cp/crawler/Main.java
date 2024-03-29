package org.cp.crawler;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpHost;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.cp.crawler.dao.LinkPoolDao;
import org.cp.crawler.dao.NewsDao;
import org.cp.crawler.dao.ProcessedLinkDao;
import org.cp.crawler.dao.mybatis.impl.LinkPoolMybatisImpl;
import org.cp.crawler.dao.mybatis.impl.NewsMybatisImpl;
import org.cp.crawler.dao.mybatis.impl.ProcessedLinkMybatisImpl;
import org.cp.crawler.model.News;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.common.xcontent.XContentType;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-29
 */
@Slf4j
public class Main {

    private static LinkPoolDao linkPoolDao = new LinkPoolMybatisImpl();
    private static ProcessedLinkDao processedLinkDao = new ProcessedLinkMybatisImpl();
    private static NewsDao newsDao = new NewsMybatisImpl();

    public static void main(String[] args) throws SQLException {

        startToCrawl();

    }

    private static void startToCrawl() throws SQLException {
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
                String title = titles.get(0).text();
                String content = article.select("p")
                        .stream()
                        .map(Element::text)
                        .collect(Collectors.joining("\n"));
                log.info(link);
                log.info(title);
                News news = News.of(title, content, link);
                newsDao.save(news);
                saveToElasticSearch(news);
            }
        }
    }

    /**
     * 保存导ES 里面
     *
     * @param news 新闻信息
     */
    private static void saveToElasticSearch(News news) {
        try (RestHighLevelClient client = new RestHighLevelClient(
                RestClient.builder(
                        new HttpHost("localhost", 9200, "http")))) {
            IndexRequest request = new IndexRequest("news");
            Map<String, Object> newsMap = news.toMap();
            request.source(newsMap, XContentType.JSON);
            IndexResponse indexResponse = client.index(request, RequestOptions.DEFAULT);
            log.info(indexResponse.status().toString());
        } catch (IOException e) {
            log.error("ES error", e);
        }
    }


    private static void getNewLinks(Document doc) throws SQLException {
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

    private static String getOneLinkFromLinkPool() throws SQLException {
        return linkPoolDao.getAndRemove();
    }

}
