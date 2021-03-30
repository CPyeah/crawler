create table links_to_be_process
(
	link varchar(256) not null
);
comment on table links_to_be_process is '待处理的链接池';
comment on column links_to_be_process.link is '新闻链接';

create table links_already_processed
(
	link varchar(256)
);
comment on table links_already_processed is '已处理过的链接';

create table news
(
	id bigint auto_increment,
	title varchar(56) not null,
	content text,
	url varchar(128) not null,
	create_time timestamp,
	update_time timestamp,
	constraint news_pk
		primary key (id)
);




