package org.cp.crawler.dao;

import java.util.LinkedList;
import java.util.List;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
public class LinkPoolLocalImpl implements LinkPoolDao {

    private static List<String> linkPool = new LinkedList<String>() {{
        add("https://sina.cn/");
    }};

    @Override
    public boolean isEmpty() {
        return linkPool.isEmpty();
    }

    @Override
    public void remove(String link) {
        linkPool.remove(link);
    }

    @Override
    public int count() {
        return linkPool.size();
    }

    @Override
    public void add(String link) {
        linkPool.add(link);
    }

    @Override
    public String getAndRemove() {
        return linkPool.remove(0);
    }
}
