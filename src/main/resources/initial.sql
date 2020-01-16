drop table miaosha_user
create table miaosha_user
(
    id bigint not null comment '用户ID,手机号码',
    nickname varchar(255) not null,
    password varchar(32) default null comment 'MD5(MD5(pass明文_固定salt)+salt)',
    salt varchar(10) default null,
    head varchar(128) default null comment '头像,云存储的ID',
    register_date datetime default null comment '注册时间',
    last_login_date datetime default null comment '上次登录时间',
    login_count int default 0 null comment '登录次数',
    constraint miaosha_user_pk
        primary key (id)
);

drop table goods
create table goods
(
    id bigint auto_increment comment '商品ID',
    goods_name varchar(16) null comment '商品名称',
    goods_title varchar(64) null comment '商品标题',
    goods_img varchar(64) null comment '商品图片',
    goods_detail longtext null comment '商品详情',
    goods_price decimal(10,2) default 0.00 null comment '商品单价',
    goods_stock int default 0 null comment '商品库存，-1表示没有限制',
    constraint goods_pk
        primary key (id)
) AUTO_INCREMENT=3;

drop table miaosha_goods
create table miaosha_goods
(
    id bigint auto_increment comment '秒杀的商品表',
    goods_id bigint null comment '商品id',
    miaosha_price decimal(10,2) default 0.00 null comment '秒杀价',
    stock_count int null comment '库存数量',
    start_date datetime null comment '秒杀开始时间',
    end_date datetime null comment '秒杀结束时间',
    constraint miaosha_goods_pk
        primary key (id)
) auto_increment=3;

drop table order_info
create table order_info
(
	id bigint auto_increment,
	user_id bigint null comment '用户ID',
	goods_id bigint null comment '商品ID',
	delivery_addr_id bigint null comment '收货地址ID',
	goods_name varchar(16) null comment '冗余的商品名称',
	goods_count int default 0 null comment '商品数量',
	goods_price decimal(10,2) default 0.00 null comment '商品单价',
	order_channel tinyint default 0 null comment '1pc, 2android, 3ios',
	status tinyint default 0 null comment '订单状态：0新建未支付，1已支付，2已发货，3已收获，4已退款，5已完成',
	create_date datetime null comment '订单创建时间',
	pay_date datetime null comment '支付时间',
	constraint order_info_pk
		primary key (id)
) auto_increment=12;

drop table miaosha_order
create table miaosha_order
(
	id bigint auto_increment,
	user_id bigint null comment '用户ID',
	order_id bigint null comment '订单ID',
	goods_id bigint null comment '商品ID',
	constraint miaosha_order_pk
		primary key (id)
) auto_increment=3;

create unique index u_uid_gid
	on miaosha.miaosha_order (user_id, goods_id);

