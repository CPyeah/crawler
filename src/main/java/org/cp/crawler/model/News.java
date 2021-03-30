package org.cp.crawler.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
@Getter
@Builder
public class News {

    private BigInteger id;

    private String title;

    private String content;

    private String url;

    private LocalDateTime createTime;

    private LocalDateTime updateTime;

}
