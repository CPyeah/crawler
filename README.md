# crawler
使用Java编写的多线程爬虫，完成HTTP请求、模拟登录、Cookie保存、HTML解析的工作。在获得数据之后，会将它存入数据库中，当数据增长到一定规模之后，使用Elasticsearch处理和分析数据，并完成一个简单的搜索引擎。

运行前请先执行 `sh init-data.sh`
#### 使用计数
- java 8
- Maven
- circleci
- 广度优先算法
- Jsoup
- spotbugs
- H2数据库
- Flyway 使用`mvn flyway:migrate`命令初始化数据库
- Mybatis
- spotbugs
- Elasticsearch