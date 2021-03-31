package org.cp.crawler.dao;

import org.cp.crawler.model.News;

import java.sql.SQLException;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public interface NewsDao {

    void save(News news) throws SQLException;

}
