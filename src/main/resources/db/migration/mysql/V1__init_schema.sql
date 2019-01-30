
create database IF NOT EXISTS fileserver_boot;

/*==============================================================*/
/* Table: pub_user                                              */
/*==============================================================*/
create table if not exists pub_user
(
   id                   bigint not null auto_increment,
   gmt_create           datetime not null,
   gmt_modified         datetime,
   version              bigint not null default 0,
   is_deleted           tinyint not null,
   ucode                varchar(64),
   username             varchar(100) not null,
   password             varchar(100) not null,
   salt                 varchar(100),
   nickname             varchar(100),
   is_enabled           tinyint,
   last_login_time      datetime,
   primary key (id)
);


/*==============================================================*/
/* Table: file_category                                         */
/*==============================================================*/
create table if not exists file_category
(
   id                   bigint not null auto_increment,
   gmt_create           datetime not null,
   gmt_modified         datetime,
   version              bigint not null default 0,
   is_deleted           tinyint unsigned not null,
   code                 varchar(32) not null,
   pcode                varchar(32),
   pcodes               varchar(256),
   name                 varchar(100) not null,
   storage_type         varchar(32),
   remarks              varchar(500),
   primary key (id),
   unique key uk_file_category_code (code)
);

alter table file_category comment '文件类别表主要用在文件分类管理';

/*==============================================================*/
/* Table: file_storage                                          */
/*==============================================================*/
create table if not exists file_storage
(
   id                   bigint not null auto_increment,
   gmt_create           datetime not null,
   gmt_modified         datetime,
   version              bigint not null default 0,
   is_deleted           tinyint unsigned not null,
   file_code            varchar(64) not null,
   category_code        varchar(64) not null,
   file_key             varchar(500) not null,
   description          varchar(500),
   original_name        varchar(256) not null,
   ext                  varchar(32) not null,
   file_size            bigint unsigned not null comment '单位 byte',
   storage_name         varchar(256) not null,
   temp_expires_at      datetime not null comment '单位秒',
   storage_type         varchar(32) not null comment 'sys db',
   create_by            varchar(64),
   is_enabled           tinyint unsigned not null default false,
   primary key (id),
   unique key uk_file_category_code (file_key)
);

alter table file_storage comment '文件储存表';


