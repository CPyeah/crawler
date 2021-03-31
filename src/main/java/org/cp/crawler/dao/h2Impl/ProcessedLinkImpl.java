package org.cp.crawler.dao.h2impl;

import org.cp.crawler.dao.ProcessedLinkDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public class ProcessedLinkImpl implements ProcessedLinkDao {


    @Override
    public void add(String link) throws SQLException {
        String sql = "insert into LINKS_ALREADY_PROCESSED values ( ? )";
        PreparedStatement preparedStatement = NewsDaoImpl.connection.prepareStatement(sql);
        preparedStatement.setString(1, link);
        preparedStatement.executeUpdate();
    }

    @Override
    public int count() throws SQLException {
        String sql = "select count(*) from LINKS_ALREADY_PROCESSED";
        PreparedStatement preparedStatement = NewsDaoImpl.connection.prepareStatement(sql);
        preparedStatement.executeQuery();
        return preparedStatement.getResultSet().getInt(1);
    }

    @Override
    public boolean contains(String link) {
        return false;
    }
}
