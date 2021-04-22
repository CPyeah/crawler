package org.cp.crawler.dao.mybatis.impl;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.cp.crawler.dao.NewsDao;
import org.cp.crawler.model.News;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-04-09
 */
public class NewsMybatisImpl implements NewsDao {

    public static SqlSessionFactory getSqlSessionFactory() {
        String resource = "mybatis-config.xml";
        InputStream inputStream;
        try {
            inputStream = Resources.getResourceAsStream(resource);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException();
        }
        return new SqlSessionFactoryBuilder().build(inputStream);
    }

    @Override
    public void save(News news) {
        try (SqlSession session = getSqlSessionFactory().openSession(true)) {
            session.insert("org.cp.crawler.NewsMapper.insertOne", news);
        }
    }

    public static News selectOne(Integer id) throws IOException {
        try (SqlSession session = getSqlSessionFactory().openSession()) {
            return (News) session.selectOne("org.cp.crawler.NewsMapper.selectOne", id);
        }
    }

}
