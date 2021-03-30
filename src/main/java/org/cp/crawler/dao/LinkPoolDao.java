package org.cp.crawler.dao;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public interface LinkPoolDao {

    boolean isEmpty();

    void remove(String link);

    int count();

    void add(String link);

    String getAndRemove();

}
