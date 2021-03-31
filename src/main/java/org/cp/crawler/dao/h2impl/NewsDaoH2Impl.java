package org.cp.crawler.dao.h2impl;

import lombok.extern.slf4j.Slf4j;
import org.cp.crawler.dao.NewsDao;
import org.cp.crawler.model.News;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
@Slf4j
public class NewsDaoH2Impl implements NewsDao {

    public static Connection connection;

    public NewsDaoH2Impl() {
        try {
            connection = DriverManager.getConnection("jdbc:h2:file:/C:/java-workspace/crawler/src/main/resources/news", "root", "oracle");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void save(News news) {

    }
}
