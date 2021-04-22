package org.cp.crawler.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * 新闻
 *
 * @author chengpeng[OF3832]
 * @company qianmi.com
 * @date 2021-03-30
 */
@Getter
@Builder
@Entity()
@Table(name = "news")
@NoArgsConstructor
@AllArgsConstructor
public class News {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private BigInteger id;

    @Column
    private String title;

    @Column
    private String content;

    @Column
    private String url;

    @Column
    private LocalDateTime createTime;

    @Column
    private LocalDateTime updateTime;

    public static News of(String title, String content, String link) {
        return News.builder()
                .title(title)
                .content(content)
                .url(link)
                .createTime(LocalDateTime.now())
                .updateTime(LocalDateTime.now())
                .build();
    }

    @Override
    public String toString() {
        return "News{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", url='" + url + '\'' +
                '}';
    }

    public Map<String, Object> toMap() {
        return new HashMap<String, Object>(10) {{
           put("id", id);
           put("title", title);
           put("content", content);
           put("url", url);
           put("createTime", createTime);
           put("updateTime", updateTime);
        }};
    }
}
