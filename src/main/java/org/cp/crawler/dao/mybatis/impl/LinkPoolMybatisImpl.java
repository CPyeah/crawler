package org.cp.crawler.dao.mybatis.impl;

import org.apache.ibatis.session.SqlSession;
import org.cp.crawler.dao.LinkPoolDao;

import java.util.HashMap;
import java.util.Map;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public class LinkPoolMybatisImpl implements LinkPoolDao {

    private final String tableName = "links_to_be_process";

    @Override
    public boolean isEmpty() {
        return count() == 0;
    }

    @Override
    public void remove(String link) {
        try (SqlSession session = NewsMybatisImpl.getSqlSessionFactory().openSession(true)) {
            Map<String, Object> param = new HashMap<>(4);
            param.put("table", tableName);
            param.put("link", link);
            session.delete("org.cp.crawler.LinkMapper.remove", param);
        }
    }

    @Override
    public int count() {
        try (SqlSession session = NewsMybatisImpl.getSqlSessionFactory().openSession()) {
            return session.selectOne("org.cp.crawler.LinkMapper.count", tableName);
        }
    }

    @Override
    public void add(String link) {
        try (SqlSession session = NewsMybatisImpl.getSqlSessionFactory().openSession(true)) {
            Map<String, Object> param = new HashMap<>(4);
            param.put("table", tableName);
            param.put("link", link);
            session.insert("org.cp.crawler.LinkMapper.insert", param);
        }
    }

    @Override
    public String getAndRemove() {
        try (SqlSession session = NewsMybatisImpl.getSqlSessionFactory().openSession(true)) {
            String link = session.selectOne("org.cp.crawler.LinkMapper.selectOne", tableName);
            Map<String, Object> param = new HashMap<>(4);
            param.put("table", tableName);
            param.put("link", link);
            session.delete("org.cp.crawler.LinkMapper.remove", param);
            return link;
        }
    }
}
