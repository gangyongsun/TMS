SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `t_key_info`;
CREATE TABLE `t_key_info` (
  `id` int(11) NOT NULL AUTO_INCREMENT COMMENT '词条编号，采取自动递增形式',
  `chinese` text NOT NULL COMMENT '中文词条，主键',
  `english` text NOT NULL COMMENT '英文条目',
  `classification1` varchar(512) DEFAULT NULL COMMENT '一级分类',
  `classification2` varchar(512) DEFAULT NULL COMMENT '二级分类',
  `classification3` varchar(512) DEFAULT NULL COMMENT '三级分类',
  `source` varchar(512) DEFAULT NULL COMMENT '文档来源：即词条出现的文档或者文献来源',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注，相关信息说明字段',
  `contributor` varchar(45) DEFAULT NULL COMMENT '词条审核或者贡献者名字',
  `image` varchar(255) DEFAULT NULL COMMENT '词条对应的图片名称或者超链接地址',
  `xremark` varchar(255) DEFAULT NULL COMMENT '历史版本的备注信息',
  `sentenceCN` text COMMENT '中文参考例句，句子之间使用“$$”进行分割',
  `sentenceCNResource` varchar(256) DEFAULT NULL COMMENT '中文参考例句的来源，同每一个中文句子相对应，中间也使用“$$”进行分割\nTM_DB表示翻译记忆库\nNET_youdao表示网络来源',
  `sentenceEN` text COMMENT '英文参考例句，句子之间使用“$$”进行分割',
  `sentenceENResource` varchar(256) DEFAULT NULL COMMENT '英文参考例句的来源，同每一个英文句子相对应，中间也使用“$$”进行分割\nTM_DB表示翻译记忆库\nNET_youdao表示网络来源',
  `abbreviationCN` varchar(256) DEFAULT NULL COMMENT '中文简称',
  `abbreviationEN` varchar(256) DEFAULT NULL COMMENT '英文简称或者缩写',
  `synonymCN` varchar(512) DEFAULT NULL COMMENT '中文同义词，多个同义词之间使用“;”进行分割',
  `synonymEN` varchar(512) DEFAULT NULL COMMENT '英文同义词，多个同义词之间使用“;”进行分割',
  `definationCN` varchar(1024) DEFAULT NULL COMMENT '中文词条对应的定义内容',
  `definationCNResource` varchar(512) DEFAULT NULL COMMENT '中文定义的来源或者出处',
  `definationEN` varchar(1024) DEFAULT NULL COMMENT '英文词条定义的内容',
  `definationENResource` varchar(512) DEFAULT NULL COMMENT '英文定义的来源或者出处',
  `status` varchar(64) DEFAULT NULL COMMENT '有两种状态：\nreviewed和unreviewed\r\n',
  `totalClick` int(11) DEFAULT 0 COMMENT '词语的点击和搜索次数，用于计算热词使用',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8

DROP TABLE IF EXISTS `t_key_info_nonesearch`;
CREATE TABLE `t_key_info_nonesearch` (
  `id` int(11)  not null primary key auto_increment COMMENT  '递增主键',
  `searchContent` varchar(32) COMMENT '搜索内容',
  `searchNumber` int(11)  COMMENT '搜索次数'
) ENGINE=InnoDB DEFAULT CHARSET=utf8
