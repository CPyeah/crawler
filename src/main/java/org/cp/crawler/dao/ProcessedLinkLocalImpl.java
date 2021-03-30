package org.cp.crawler.dao;

import java.util.HashSet;
import java.util.Set;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public class ProcessedLinkLocalImpl implements ProcessedLinkDao {

    private static Set<String> processedLinks = new HashSet<>();

    @Override
    public void add(String link) {
        processedLinks.add(link);
    }

    @Override
    public int count() {
        return processedLinks.size();
    }

    @Override
    public boolean contains(String link) {
        return processedLinks.contains(link);
    }
}
