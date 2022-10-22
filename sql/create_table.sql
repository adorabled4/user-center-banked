-- auto-generated definition
create table user
(
    user_id       bigint auto_increment
        primary key,
    user_name     varchar(256)                           null,
    user_account  varchar(256)                           not null,
    avatar_url    varchar(512)                           null,
    gender        tinyint      default 0                 null,
    user_status   int                                    null,
    phone         varchar(256)                           null,
    email         varchar(256)                           null,
    user_password varchar(512)                           not null,
    create_time   timestamp    default CURRENT_TIMESTAMP null,
    updateTime   datetime default CURRENT_TIMESTAMP null on update CURRENT_TIMESTAMP,
    is_delete     tinyint      default 0                 null comment '0表示可用,1表示逻辑删除',
    user_role     int          default 0                 null comment '0表示普通用户,1表示管理员',
    planet_code   varchar(256) default '1082'            not null comment '星球编号'
);

