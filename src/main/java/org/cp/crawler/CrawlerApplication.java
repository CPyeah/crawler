package org.cp.crawler;

import lombok.extern.slf4j.Slf4j;
import org.cp.crawler.dao.jpa.impl.NewsRepository;
import org.cp.crawler.model.News;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.math.BigInteger;
import java.util.Optional;

/**
 * @author cp
 */
@Slf4j
@SpringBootApplication
public class CrawlerApplication {

    public static void main(String[] args) {
        SpringApplication.run(CrawlerApplication.class, args);
    }

    @Bean
    public CommandLineRunner jpaDemo(NewsRepository repository) {
        return (args) -> {
            // save a few newss
            repository.save(News.of("title", "content_1", "link_1"));
            repository.save(News.of("title", "content_2", "link_2"));
            repository.save(News.of("title_3", "content_3", "link_3"));
            repository.save(News.of("title_4", "content_4", "link_4"));
            repository.save(News.of("title_5", "content_5", "link_5"));

            // fetch all newss
            log.info("Newss found with findAll():");
            log.info("-------------------------------");
            for (News news : repository.findAll()) {
                log.info(news.toString());
            }
            log.info("");

            // fetch an individual news by ID
            Optional<News> news = repository.findById(BigInteger.ONE);
            log.info("News found with findById(1L):");
            log.info("--------------------------------");
            log.info(news.toString());
            log.info("");

            // fetch newss by last name
            log.info("News found with findByLastName('Bauer'):");
            log.info("--------------------------------------------");
            repository.findByTitle("title").forEach(bauer -> {
                log.info(bauer.toString());
            });
            log.info("");
        };
    }

}
