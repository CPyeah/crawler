package org.cp.crawler.dao.h2impl;

import lombok.extern.slf4j.Slf4j;
import org.cp.crawler.dao.NewsDao;
import org.cp.crawler.model.News;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;

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
    public void save(News news) throws SQLException {
        String sql = "insert into NEWS (title, content, url, create_time, update_time) values ( ?, ?, ?, ?, ? )";
        PreparedStatement preparedStatement = NewsDaoH2Impl.connection.prepareStatement(sql);
        preparedStatement.setString(1, news.getTitle());
        preparedStatement.setString(2, news.getContent());
        preparedStatement.setString(3, news.getUrl());
        preparedStatement.setTimestamp(4, Timestamp.valueOf(news.getCreateTime()));
        preparedStatement.setTimestamp(5, Timestamp.valueOf(news.getUpdateTime()));
        preparedStatement.executeUpdate();
    }
}