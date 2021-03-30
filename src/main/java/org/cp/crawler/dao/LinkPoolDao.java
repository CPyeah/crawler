package org.cp.crawler.dao;

import java.sql.SQLException;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public interface LinkPoolDao {

    boolean isEmpty() throws SQLException;

    void remove(String link) throws SQLException;

    int count() throws SQLException;

    void add(String link);

    String getAndRemove();

}
