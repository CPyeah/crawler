package org.cp.crawler.dao.mybatis.impl;

import org.cp.crawler.dao.ProcessedLinkDao;
import org.cp.crawler.dao.h2.impl.NewsDaoImpl;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public class ProcessedLinkMybatisImpl implements ProcessedLinkDao {

    @Override
    public void add(String link) throws SQLException {

    }

    @Override
    public int count() throws SQLException {
        return 0;
    }

    @Override
    public boolean contains(String link) {
        return false;
    }
}
