package org.cp.crawler.dao.jpa.impl;

import org.cp.crawler.dao.NewsDao;
import org.cp.crawler.model.News;
import org.springframework.data.repository.CrudRepository;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-04-01
 */
public interface NewsRepository extends CrudRepository<News, BigInteger> {

    List<News> findByTitle(String title);


}
