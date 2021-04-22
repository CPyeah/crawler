package org.cp.crawler.dao.mybatis.impl;

import org.apache.ibatis.session.SqlSession;
import org.cp.crawler.dao.ProcessedLinkDao;
import org.cp.crawler.dao.h2.impl.NewsDaoImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public class ProcessedLinkMybatisImpl implements ProcessedLinkDao {

    private final String tableName = "links_already_processed";

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
    public int count() {
        try (SqlSession session = NewsMybatisImpl.getSqlSessionFactory().openSession()) {
            return session.selectOne("org.cp.crawler.LinkMapper.count", tableName);
        }
    }

    @Override
    public boolean contains(String link) {
        return false;
    }
}
