package org.cp.crawler.dao.h2Impl;

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
        String sql = "select count(*) from LINKS_TO_BE_PROCESS";
        PreparedStatement preparedStatement = NewsDaoH2Impl.connection.prepareStatement(sql);
        preparedStatement.executeQuery();
        ResultSet resultSet = preparedStatement.getResultSet();
        if (resultSet.next()) {
            return resultSet.getInt(1);
        }
        return 0;
    }

    @Override
    public void add(String link) {

    }

    @Override
    public String getAndRemove() {
        return null;
    }
}
