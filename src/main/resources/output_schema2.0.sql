SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `output_goods_category`;
CREATE TABLE `output_goods_category`  (
                                          `category_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '分类id',
                                          `category_level` tinyint(4) NOT NULL DEFAULT 0 COMMENT '分类级别(1-一级分类 2-二级分类)',
                                          `parent_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '父分类id',
                                          `category_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '分类名称',
                                          `category_rank` int(11) NOT NULL DEFAULT 0 COMMENT '排序值(字段越大越靠前)',
                                          `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
                                          `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                          `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '创建者id',
                                          `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                          `update_user` int(11) NULL DEFAULT 0 COMMENT '修改者id',
                                          PRIMARY KEY (`category_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 107 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `output_goods_category` VALUES (1, 1, 0, '外设', 100, 0, '2019-09-11 18:45:40', 0, '2019-09-11 18:45:40', 0);
INSERT INTO `output_goods_category` VALUES (2, 1, 0, '周边', 100, 0, '2019-09-11 18:45:40', 0, '2019-09-11 18:45:40', 0);
INSERT INTO `output_goods_category` VALUES (10, 2, 1, '键盘', 10, 0, '2019-09-11 18:46:32', 0, '2019-09-11 18:46:32', 0);
INSERT INTO `output_goods_category` VALUES (11, 2, 1, '鼠标', 9, 0, '2019-09-11 18:46:43', 0, '2019-09-11 18:46:43', 0);



DROP TABLE IF EXISTS `output_goods_info`;
CREATE TABLE `output_goods_info`  (
                                      `goods_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品表主键id',
                                      `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
                                      `goods_intro` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品简介',
                                      `goods_category_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '关联分类id',
                                      `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '商品主图',
                                      `goods_detail_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品详情',
                                      `original_price` int(11) NOT NULL DEFAULT 1 COMMENT '商品价格',
                                      `selling_price` int(11) NOT NULL DEFAULT 1 COMMENT '商品实际售价',
                                      `stock_num` int(11) NOT NULL DEFAULT 0 COMMENT '商品库存数量',
                                      `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品标签',
                                      `goods_sell_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '商品上架状态 0-下架 1-上架',
                                      `create_user` int(11) NOT NULL DEFAULT 0 COMMENT '添加者主键id',
                                      `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品添加时间',
                                      `update_user` int(11) NOT NULL DEFAULT 0 COMMENT '修改者主键id',
                                      `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '商品修改时间',
                                      PRIMARY KEY (`goods_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10896 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `output_goods_info` (`goods_id`, `goods_name`, `goods_intro`, `goods_category_id`, `goods_cover_img`, `goods_detail_content`, `original_price`, `selling_price`, `stock_num`, `tag`, `goods_sell_status`, `create_user`, `create_time`, `update_user`, `update_time`)
VALUES
    (10003,'output键盘1','滋润型 400ml',10,'/goods-img/87446ec4-e534-4b49-9f7d-9bea34665284.jpg','<p>一款键盘</p>',100,100,1000,'',1,0,'2019-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (10004,'output键盘2','120g',10,'/goods-img/45854bdd-2ca5-423c-a609-3d336d9322b4.jpg','<p>一款键盘.</p>',45,45,999,'',0,0,'2019-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (10005,'output键盘3','120g',10,'/goods-img/45854bdd-2ca5-423c-a609-3d336d9322b4.jpg','<p>一款键盘.</p>',45,45,999,'',0,0,'2019-09-18 13:18:47',0,'2020-10-13 10:41:59'),
    (10006,'output键盘4','120g',10,'/goods-img/45854bdd-2ca5-423c-a609-3d336d9322b4.jpg','<p>一款键盘.</p>',45,45,999,'',0,0,'2019-09-18 13:18:47',0,'2020-10-13 10:41:59');

DROP TABLE IF EXISTS `output_order`;
CREATE TABLE `output_order`  (
                                 `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单表主键id',
                                 `order_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单号',
                                 `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户主键id',
                                 `total_price` int(11) NOT NULL DEFAULT 1 COMMENT '订单总价',
                                 `pay_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '支付状态:0.未支付,1.支付成功,-1:支付失败',
                                 `pay_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0.无 1.支付宝支付 2.微信支付',
                                 `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
                                 `order_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭',
                                 `extra_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单body',
                                 `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人姓名',
                                 `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人手机号',
                                 `user_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人收货地址',
                                 `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
                                 `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
                                 `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
                                 PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `output_order` VALUES (1, '15688187285093508', 1, 2492, 1, 2, '2019-09-18 23:00:18', -1, '', '', '', 'xafsdufhpwe', 0, '2019-09-18 22:53:07', '2019-09-18 22:55:32');
INSERT INTO `output_order` VALUES (2, '15688188616936181', 1, 135, 1, 1, '2019-09-18 23:01:06', 1, '', '', '', 'xafsdufhpwe', 0, '2019-09-18 22:55:20', '2019-09-18 23:01:06');
INSERT INTO `output_order` VALUES (3, '15689089426956979', 1, 15487, 1, 1, '2019-09-20 00:16:03', 3, '', '', '', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, '2019-09-19 23:56:40', '2019-09-20 00:10:39');
INSERT INTO `output_order` VALUES (4, '15689090398492576', 1, 8499, 0, 0, NULL, 0, '', '', '', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, '2019-09-19 23:58:18', '2019-09-19 23:58:18');
INSERT INTO `output_order` VALUES (5, '15689096266448452', 1, 115, 1, 2, '2019-09-20 00:13:52', 1, '', '', '', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, '2019-09-20 00:08:04', '2019-09-20 00:13:52');

DROP TABLE IF EXISTS `output_order`;
CREATE TABLE `output_order`  (
  `order_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单表主键id',
  `order_no` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单号',
  `user_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '用户主键id',
  `total_price` int(11) NOT NULL DEFAULT 1 COMMENT '订单总价',
  `pay_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '支付状态:0.未支付,1.支付成功,-1:支付失败',
  `pay_type` tinyint(4) NOT NULL DEFAULT 0 COMMENT '0.无 1.支付宝支付 2.微信支付',
  `pay_time` datetime(0) NULL DEFAULT NULL COMMENT '支付时间',
  `order_status` tinyint(4) NOT NULL DEFAULT 0 COMMENT '订单状态:0.待支付 1.已支付 2.配货完成 3:出库成功 4.交易成功 -1.手动关闭 -2.超时关闭 -3.商家关闭',
  `extra_info` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '订单body',
  `user_name` varchar(30) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人姓名',
  `user_phone` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人手机号',
  `user_address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货人收货地址',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
  PRIMARY KEY (`order_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 21 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `output_order` VALUES (1, '15688187285093508', 1, 2492, 1, 2, '2019-09-18 23:00:18', -1, '', '', '', 'xafsdufhpwe', 0, '2019-09-18 22:53:07', '2019-09-18 22:55:32');
INSERT INTO `output_order` VALUES (2, '15688188616936181', 1, 135, 1, 1, '2019-09-18 23:01:06', 1, '', '', '', 'xafsdufhpwe', 0, '2019-09-18 22:55:20', '2019-09-18 23:01:06');
INSERT INTO `output_order` VALUES (3, '15689089426956979', 1, 15487, 1, 1, '2019-09-20 00:16:03', 3, '', '', '', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, '2019-09-19 23:56:40', '2019-09-20 00:10:39');
INSERT INTO `output_order` VALUES (4, '15689090398492576', 1, 8499, 0, 0, NULL, 0, '', '', '', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, '2019-09-19 23:58:18', '2019-09-19 23:58:18');
INSERT INTO `output_order` VALUES (5, '15689096266448452', 1, 115, 1, 2, '2019-09-20 00:13:52', 1, '', '', '', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, '2019-09-20 00:08:04', '2019-09-20 00:13:52');

DROP TABLE IF EXISTS `output_order_item`;
CREATE TABLE `output_order_item`  (
  `order_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '订单关联购物项主键id',
  `order_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '订单主键id',
  `goods_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '关联商品id',
  `goods_name` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单时商品的名称(订单快照)',
  `goods_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '下单时商品的主图(订单快照)',
  `selling_price` int(11) NOT NULL DEFAULT 1 COMMENT '下单时商品的价格(订单快照)',
  `goods_count` int(11) NOT NULL DEFAULT 1 COMMENT '数量(订单快照)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  PRIMARY KEY (`order_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 35 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `output_order_item` VALUES (1, 1, 10003, 'Apple AirPods 配充电盒', '/goods-img/64768a8d-0664-4b29-88c9-2626578ffbd1.jpg', 1246, 2, '2019-09-18 22:53:07');
INSERT INTO `output_order_item` VALUES (2, 2, 10004, 'MUJI 羽毛 靠垫', '/goods-img/0f701215-b782-40c7-8bbd-97b51be56461.jpg', 65, 1, '2019-09-18 22:55:20');
INSERT INTO `output_order_item` VALUES (3, 2, 10005, '无印良品 女式粗棉线条纹长袖T恤', 'http://localhost:28089/goods-img/5488564b-8335-4b0c-a5a4-52f3f03ee728.jpg', 70, 1, '2019-09-18 22:55:20');
INSERT INTO `output_order_item` VALUES (4, 3, 10004, '华为 HUAWEI P30 Pro', '/goods-img/dda1d575-cdac-4eb4-a118-3834490166f7.jpg', 5488, 1, '2019-09-19 23:56:40');
INSERT INTO `output_order_item` VALUES (5, 3, 10005, 'Apple iPhone 11 Pro', '/goods-img/0025ad55-e260-4a00-be79-fa5b8c5ac0de.jpg', 9999, 1, '2019-09-19 23:56:40');


DROP TABLE IF EXISTS `output_shopping_cart_item`;
CREATE TABLE `output_shopping_cart_item`  (
  `cart_item_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '购物项主键id',
  `user_id` bigint(20) NOT NULL COMMENT '用户主键id',
  `goods_id` bigint(20) NOT NULL DEFAULT 0 COMMENT '关联商品id',
  `goods_count` int(11) NOT NULL DEFAULT 1 COMMENT '数量(最大为5)',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '删除标识字段(0-未删除 1-已删除)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '最新修改时间',
  PRIMARY KEY (`cart_item_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 69 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


DROP TABLE IF EXISTS `output_user`;
CREATE TABLE `output_user`  (
  `user_id` bigint(20) NOT NULL AUTO_INCREMENT COMMENT '用户主键id',
  `nick_name` varchar(50) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '用户昵称',
  `login_name` varchar(11) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '登陆名称(默认为手机号)',
  `password_md5` varchar(32) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT 'MD5加密后的密码',
  `introduce_sign` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '个性签名',
  `address` varchar(100) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '收货地址',
  `is_deleted` tinyint(4) NOT NULL DEFAULT 0 COMMENT '注销标识字段(0-正常 1-已注销)',
  `locked_flag` tinyint(4) NOT NULL DEFAULT 0 COMMENT '锁定标识字段(0-未锁定 1-已锁定)',
  `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '注册时间',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 9 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;

INSERT INTO `output_user` VALUES (1, '热寂', '15070455790', 'e10adc3949ba59abbe56e057f20f883e', '朝闻道', '湖北武汉 中国地质大学 15070455790', 0, 0, '2023-04-22 08:44:57');
INSERT INTO `output_user` VALUES (6, '测试用户1', '13711113333', 'dda01dc6d334badcd031102be6bee182', '测试用户1', '上海浦东新区XX路XX号 999 137xxxx7797', 0, 0, '2019-08-29 10:51:39');
INSERT INTO `output_user` VALUES (7, '测试用户2测试用户2测试用户2测试用户2', '13811113333', 'dda01dc6d334badcd031102be6bee182', '测试用户2', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, 0, '2019-08-29 10:55:08');
INSERT INTO `output_user` VALUES (8, '测试用户3', '13911113333', 'dda01dc6d334badcd031102be6bee182', '测试用户3', '杭州市西湖区xx小区x幢419 十三 137xxxx2703', 0, 0, '2019-08-29 10:55:16');


DROP TABLE IF EXISTS `output_news`;
CREATE TABLE `output_news`  (
                                      `news_id` bigint(20) UNSIGNED NOT NULL AUTO_INCREMENT COMMENT '商品表主键id',
                                      `news_title` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品名',
                                      `news_cover_img` varchar(200) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '/admin/dist/img/no-img.png' COMMENT '动态主图',
                                      `news_content` text CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL COMMENT '商品详情',
                                      `tag` varchar(20) CHARACTER SET utf8 COLLATE utf8_general_ci NOT NULL DEFAULT '' COMMENT '商品标签',
                                      `create_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '添加时间',
                                      `update_time` datetime(0) NOT NULL DEFAULT CURRENT_TIMESTAMP COMMENT '修改时间',
                                      PRIMARY KEY (`news_id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 10896 CHARACTER SET = utf8 COLLATE = utf8_general_ci ROW_FORMAT = Dynamic;


INSERT INTO `output_news` (`news_id`, `news_title`,`news_cover_img`,`news_content`,`tag` , `create_time`, `update_time`)
VALUES
    (20003,'黛苏玫瑰发布帖子','/goods-img/87446ec4-e534-4b49-9f7d-9bea34665284.jpg','使用双动圈 + 动铁 三单元发声 ／ 均衡自然声音 高保真石墨烯振膜 ／ 25 道工序打磨 ／ 弹力磨砂线材 Pro 小米圈铁耳机 孕育万物的天空和大地，时刻传达着声音的释放与组合，更是寻找灵感的源头，鸟鸣、流水、雷响、风啸不同的声音互相交融，共同演奏出自然的本真。 小米圈铁耳机 Pro 使用双“动圈”单元+“动铁”单元，将三个单元共同融入到同一个耳机中，双“动圈”的醇厚低音，让声音更加扎实稳重，石墨烯材料的加入，则让声音的细节更为丰富。“动铁”的高音透亮，稳定自然，感受三频均衡的本色声音。随着声音的流淌，仿佛置身自然，听见这些细节，让声音一开始就感动内心。 双动圈+动铁，三单元发声，听见更多细节 为了可以真正实现高、中、低三频均衡，小米圈铁耳机 Pro  加入了双“动圈”单元，大动圈负责中低频，小动圈负责高频。在“动铁”单元的配合下，耳机的低频下潜深，中频声音扎实，而高频的细节展现更为丰富。那些刚刚好的声音，听在耳里，都在心里。 三频更均衡，声音更自然 我们听到的绝大多数乐器、人声，都在中低频段。为了让这部分声音更均衡、有感染力，我们都交由采用了石墨烯振膜的双动圈单元来负责，中低频更扎实，兼具丰富细节表现力。 石墨烯是目前自然界已知材料中轻薄、强度更高的材料，对声音的传导速度快，将它用作振膜材质，高频延展性能更好，细节丰富，声音清澈自然，更富穿透力。同时强度又是钢铁的100倍， 可以尽可能还原出电流信号， 真正发出高保真的好声音。 石墨烯振膜，让双动圈更有实力 小米圈铁耳机 Pro 的“动铁”单元依然采用自主研发的 \"衔铁＋驱动杆\" 结构，让声音细腻真实，更为稳定，在电容分频器的作用下，让高中低音衔接更好，失真更少。不论当你听何种音乐，细腻的感情都会被准确还原，听每首歌就像读每个故事，时刻感动自己。 动铁单元设计，高频解析好，细节不失真 好的音乐人将情感与生活用真实的方式，转化为音乐传递给每个人，每首歌都是一个故事，铭刻在各自的记忆中，为了让故事更好的表达，小米圈铁耳机 Pro 在科学客观调音的基础上，再次邀请到荣获 4 次格莱美大奖的 Luca Bignardi，为小米圈铁耳机 Pro 进行主观调音，为的就是让每个喜爱音乐的人能够真切的感受到每一个故事，跟随内心，娓娓道来... 多种科学调音，让声音更鲜活，更温暖 当耳机真正为声音服务时，设计将不再只是修饰耳机外观的道具，它将会成为辅助声音的一部分，小米圈铁耳机 Pro 采用圆润的设计风格，45° 斜角入耳设计，在满足舒适的同时更保证了声音的完整呈现。精密金属音腔设计，让音乐沉于耳畔，更有声音质感，弹力 TPE 磨砂线材的选用，让耳机线更为坚固耐用，确保耳机长久使用。一副好耳机，让声音和外表一起美好。 全新的外观设计，和声音一起美好 好的设计需要灵感，而灵感源于生活，为了锁住声音的灵感，小米圈铁耳机 Pro 将耳塞设计成45°斜角式入耳，贴合耳道，满足佩戴舒适感的同时尽可能减少外界声音干扰，毫无保留地听自己爱的音乐。 45°斜角入耳，舒适佩戴 小米圈铁耳机 Pro 的线控麦克风从耳机整体设计风格出发，金属磨砂弹头造型，精致小巧，指压按键圆润舒适，听歌的同时，更能感知指尖上的金属质感。 小米圈铁耳机 Pro 的耳机线材选取 TPE 材质，作为一种具有橡胶的高弹性材质， 触感柔软、耐温等特性，用它做成耳机线，将更为抗拉、耐用并且不易缠绕。让好音乐的陪伴更长久。 小米圈铁耳机 Pro 的耳塞选取奶嘴级硅胶材质，触感柔软顺滑，减少了耳塞对皮肤的刺激，让肌肤倍感亲密，同时提供四对不同尺寸的耳塞套，让佩戴者根据不同需求选择，带上它，向自己喜爱的音乐问好！ 用匠心打磨每一件产品，即使过程艰难复杂，也依然充满斗志，小米圈铁耳机 Pro 的诞生过程就是这样。25 道工序打造的金属音腔，每一处细节都精心打磨，一体成型钻石切割、细密 CD 纹雕刻、锆石喷砂、阳极氧化，千锤百炼，不放过每个细节，将金属打磨成入耳的艺术品，这就是小米圈铁耳机 Pro 对音乐执着，对好产品更要执着。 小米圈铁耳机 Pro 是铝合金音腔，采用了 CNC 钻石切割一刀成型工艺，加工精度高达0.01mm，这种工艺在对铝合金加工前都要进行工艺分析，选择合适的刀具及切削用量，将打磨成型，让耳机具有更细腻润泽的手感。 小米圈铁耳机 Pro 运用精密的 CD 纹处理，纹理细至 0.14mm，散发金属光泽，就像耳机的指纹一样。如此的精密打磨，只为让小米圈铁耳机 Pro 更具质感，让金属更光辉熠熠。 选用精细锆石喷砂，赋予小米圈铁耳机 Pro 细致均匀的外观，有效保证了耳机表面硬度，不易刮伤。出厂时，会在小米圈铁耳机 Pro 表层增加阳极处理，保证了美观程度和耐磨性，6μ的阳极厚度，坚固、耐磨，做传达好声音的艺术品。 拥有超过 700 项高于行业标准的苛刻测试，每一种测试都见证了小米圈铁耳机 Pro 的高品质， 从音乐品质到设计创新，再到匠心工艺，集合好耳机的所有亮点，都只为带给用户更好的音乐体验和使用感受，好的声音，一定需要千锤百炼 。','','2019-09-18 13:18:47','2020-10-13 10:41:59'),
    (20004,'童话发布帖子','/goods-img/87446ec4-e534-4b49-9f7d-9bea34665284.jpg','this is order','','2022-09-18 13:18:47','2022-10-13 10:41:59');
