create table user
(
    id          int(11) unsigned auto_increment comment '自增ID'
        primary key,
    create_time datetime         default CURRENT_TIMESTAMP not null comment '记录创建时间',
    modify_time datetime         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '记录修改时间',
    user_phone  varchar(16)      default ''                not null comment '手机号',
    user_name   varchar(50)      default ''                not null comment '用户名',
    user_sex    tinyint unsigned default 0                 not null comment '用户性别（1:男 2:女）'
)
    ENGINE = InnoDB
    AUTO_INCREMENT = 1 comment '用户表';

create table commodity
(
    id              int(11) unsigned auto_increment comment '自增ID'
        primary key,
    create_time     datetime    default CURRENT_TIMESTAMP not null comment '记录创建时间',
    modify_time     datetime    default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '记录修改时间',
    commodity_name  varchar(50) default ''                not null comment '商品名称',
    commodity_type  tinyint(3)  default 0                 not null comment '商品类型',
    commodity_price decimal(10, 2) unsigned               not null default 0.00 comment '商品价格'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1 comment '商品表';

create table order
(
    id             int(11) unsigned auto_increment comment '自增ID'
        primary key,
    create_time    datetime                         default CURRENT_TIMESTAMP not null comment '记录创建时间',
    modify_time    datetime                         default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment '记录修改时间',
    user_id        int(11) unsigned        not null default 0 comment '订单用户',
    order_status   tinyint(3) unsigned     not null default 0 comment '订单状态 0:未支付 1:已支付 2:已取消 3:已退款',
    order_amount   decimal(10, 2) unsigned not null default 0.00 comment '订单金额',
    pay_time       datetime                null comment '付款时间',
    commodity_id   int(11) unsigned        not null default 0 comment '订单商品',
    order_snapshot varchar(200)            not null default '' comment '订单快照'
) ENGINE = InnoDB
  AUTO_INCREMENT = 1 comment '订单表';

create index idx_user_id
    on order (user_id);
