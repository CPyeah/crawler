package org.cp.crawler.dao.mybatis.impl;

import org.cp.crawler.dao.LinkPoolDao;
import org.cp.crawler.dao.h2.impl.NewsDaoImpl;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public class LinkPoolMybatisImpl implements LinkPoolDao {

    @Override
    public boolean isEmpty() throws SQLException {
        return false;
    }

    @Override
    public void remove(String link) throws SQLException {

    }

    @Override
    public int count() throws SQLException {
        return 0;
    }

    @Override
    public void add(String link) throws SQLException {

    }

    @Override
    public String getAndRemove() throws SQLException {
        return null;
    }
}
