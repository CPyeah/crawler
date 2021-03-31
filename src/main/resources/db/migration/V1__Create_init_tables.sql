create table LINKS_ALREADY_PROCESSED
(
    LINK VARCHAR(256)
);
comment on table LINKS_ALREADY_PROCESSED is '已处理过的链接';

create table LINKS_TO_BE_PROCESS
(
    LINK VARCHAR(256) not null
);
comment on table LINKS_TO_BE_PROCESS is '待处理的链接池';
comment on column LINKS_TO_BE_PROCESS.LINK is '新闻链接';

create table NEWS
(
    ID          BIGINT auto_increment,
    TITLE       VARCHAR(56)  not null,
    CONTENT     TEXT,
    URL         VARCHAR(128) not null,
    CREATE_TIME TIMESTAMP(26, 6),
    UPDATE_TIME TIMESTAMP(26, 6),
    constraint NEWS_PK
        primary key (ID)
);

