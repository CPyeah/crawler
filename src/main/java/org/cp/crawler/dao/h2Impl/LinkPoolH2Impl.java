package org.cp.crawler.dao.h2impl;

import org.cp.crawler.dao.LinkPoolDao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public class LinkPoolH2Impl implements LinkPoolDao {


    @Override
    public boolean isEmpty() throws SQLException {
        return count() == 0;
    }

    @Override
    public void remove(String link) throws SQLException {
        String sql = "delete from LINKS_TO_BE_PROCESS where link = ?";
        PreparedStatement preparedStatement = NewsDaoH2Impl.connection.prepareStatement(sql);
        preparedStatement.setString(1, link);
        preparedStatement.executeUpdate();
    }

    @Override
    public int count() throws SQLException {
        ResultSet resultSet = getQueryResultSet("select count(*) from LINKS_TO_BE_PROCESS");
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public void add(String link) throws SQLException {
        String sql = "insert into LINKS_TO_BE_PROCESS values ( ? )";
        PreparedStatement preparedStatement = NewsDaoH2Impl.connection.prepareStatement(sql);
        preparedStatement.setString(1, link);
        preparedStatement.executeUpdate();
    }

    @Override
    public String getAndRemove() throws SQLException {
        ResultSet resultSet = getQueryResultSet("select LINK from LINKS_TO_BE_PROCESS limit 1");
        String link = null;
        if (resultSet.next()) {
            link = resultSet.getString(1);
            remove(link);
        }
        return link;
    }

    private ResultSet getQueryResultSet(String sql) throws SQLException {
        PreparedStatement preparedStatement = NewsDaoH2Impl.connection.prepareStatement(sql);
        preparedStatement.executeQuery();
        return preparedStatement.getResultSet();
    }
}
