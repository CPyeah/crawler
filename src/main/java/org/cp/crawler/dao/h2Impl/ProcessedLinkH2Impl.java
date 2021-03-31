package org.cp.crawler.dao.h2impl;

import org.cp.crawler.dao.ProcessedLinkDao;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public class ProcessedLinkH2Impl implements ProcessedLinkDao {


    @Override
    public void add(String link) {

    }

    @Override
    public int count() throws SQLException {
        String sql = "select count(*) from LINKS_ALREADY_PROCESSED";
        PreparedStatement preparedStatement = NewsDaoH2Impl.connection.prepareStatement(sql);
        preparedStatement.executeQuery();
        return preparedStatement.getResultSet().getInt(1);
    }

    @Override
    public boolean contains(String link) {
        return false;
    }
}
