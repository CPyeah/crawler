package org.cp.crawler.dao;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public interface ProcessedLinkDao {

    void add(String link);

    int count();

    boolean contains(String link);

}
