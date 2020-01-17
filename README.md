# Java秒杀

## 第一章 项目框架搭建
    1. Spring Boot环境搭建
    2. 集成Thymeleaf, Result结果封装
    3. 集成Mybatis + Durid
    4. 集成Jedis + Redis安装 + 通用缓存Key封装

## 第二章 实现登录功能
    1. 数据库设计
    2. 明文密码两次MD5处理
        * 用户端：PASS = MD5(明文 + 固定Salt) 防止密码明文传输
        * 服务端：PASS = MD5(用户输入 + 随机Salt) 防止撞库攻击
    3. JSR303参数校验 + 全局异常处理器
    4. 分布式Session

## 第三章 实现秒杀功能
    1. 数据库设计
    2. 商品列表页
    3. 商品详情页
    4. 订单详情页

## 第四章 JMeter压测
    1. JMeter入门
    2. 自定义变量模拟多用户
    3. JMeter命令行使用
    4. Redis压测工具redis_benchmark
    5. Spring Boot打war包

## 第五章 页面优化技术
    1. 页面缓存，URL缓存，对象缓存
    2. 页面静态化，前后端分离
    3. 静态资源优化
        * JS/CSS压缩，减少流量
        * 多个JS/CSS组合，减少连接数
        * CDN就近访问
    4. CDN(Content Delivery Network 内容分发网络)优化
    
## 第六章 接口优化
    1. Redis预减库存减少数据库访问
    2. 内存标记减少Redis访问
    3. 请求先入队缓冲，异步下单，增强用户体验
    4. RabbitMQ安装与Spring Boot集成
    5. Nginx水平扩展
    6. 压测