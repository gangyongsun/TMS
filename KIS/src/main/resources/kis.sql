SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS `t_key_info`;

CREATE TABLE `t_key_info` (
  `id` int(11) DEFAULT NULL COMMENT '词条编号，采取自动递增形式',
  `chinese` text COMMENT '中文词条，主键',
  `english` text COMMENT '英文条目',
  `classification1` varchar(512) DEFAULT NULL COMMENT '一级分类',
  `classification2` varchar(512) DEFAULT NULL COMMENT '二级分类',
  `classification3` varchar(512) DEFAULT NULL COMMENT '三级分类',
  `source` varchar(512) DEFAULT NULL COMMENT '文档来源：即词条出现的文档或者文献来源',
  `remark` varchar(45) DEFAULT NULL COMMENT '备注，相关信息说明字段，包含内容43个，分别是：\nGB/T24538\r\nPrefered\r\nPreferred\r\nRejected\r\nStandard\r\n产品型号：DD111S，制造商：IFM\r\n常用\r\n导致合同丧失本意的实质性变更\r\n地脚螺栓安装孔\r\n地名，新疆\r\n地名，新疆北部\r\n动词\r\n动词，形容词\r\n动词；名词\r\n动词短语\r\n发电机\r\n发电机保护罩\r\n非标准件，用于叶片维修、检测\r\n风电场\r\n机舱\r\n介词\r\n旧契约变更为新契约\r\n孔用\r\n轮胎品牌\r\n名词\r\n模拟显示式超声波设备\r\n散热器框架\r\n升降机\r\n通用\r\n推荐\r\n形容词\r\n型号：EL1859，制造商：Beckhoff\r\n型号：PSR-SCP-24DC/TS/M，制造商：菲尼克斯\r\n型号：PSR-SCP-24DC/TS/SDOR4/4x1；制造商：菲尼克斯\r\n修饰形容词\r\n叶轮\r\n叶片\r\n叶片涂料表面缺陷\r\n硬质纸板材质\r\n用于固定螺栓\r\n优利德产品，型号UT331\r\n只改变合同细节而不致合同丧失原貌\r\n轴用\r\n',
  `contributor` varchar(45) DEFAULT NULL COMMENT '词条审核或者贡献者名字',
  `image` varchar(255) DEFAULT NULL COMMENT '词条对应的图片名称或者超链接地址',
  `xremark` varchar(255) DEFAULT NULL COMMENT '历史版本的备注信息，主要包含以下这些内容：\\nD形卸扣或称U形卸扣  \\r\\nearth clamp\\r\\nearth wire\\r\\nhttp://chn.sika.com/zh/group/Aboutus/History.html\\r\\nhttp://en.greencentury.cn/about/\\r\\nhttp://en.ydhrq.com/\\r\\nhttp://info.mar-bal.com/contact-us-mar-bal\\r\\nhttp://roval-group.com/company/index.html\\r\\nhttp://www.bevone.com.cn/index.php?m=content&c=index&a=lists&catid=11\\r\\nhttp://www.bjjingzhe.com/\\r\\nhttp://www.bjmstar.com/\\r\\nhttp://www.bjmyjn.com/\\r\\nhttp://www.bjtm88.com/\\r\\nhttp://www.bjwingain.com/company_7.html\\r\\nhttp://www.bjzh.com.cn/html/kfzx/11.html\\r\\nhttp://www.daoyihe.com/lianxi/\\r\\nhttp://www.dxyhdq.com/\\r\\nhttp://www.ebmpapst.com.cn/en/index.html\\r\\nhttp://www.elektroskandia.com.cn/chinese/contact/contactUs_cn.html\\r\\nhttp://www.euro-me.com/en/\\r\\nhttp://www.exxson.com/eksm/en/home_1.htm\\r\\nhttp://www.goldenocean.cc/eng/gsjh1.asp\\r\\nhttp://www.guanlinlifting.com/\\r\\nhttp://www.kyland.com.cn/aboutus/overview\\r\\nhttp://www.landspacetech.com/\\r\\nhttp://www.lhcable.com/',
  `sentenceCN` text COMMENT '中文参考例句，句子之间使用“$$”进行分割',
  `sentenceCNResource` varchar(256) DEFAULT NULL COMMENT '中文参考例句的来源，同每一个中文句子相对应，中间也使用“$$”进行分割\nTM_DB表示翻译记忆库\nNET_youdao表示网络来源',
  `sentenceEN` text COMMENT '英文参考例句，句子之间使用“$$”进行分割',
  `sentenceENResource` varchar(256) DEFAULT NULL COMMENT '英文参考例句的来源，同每一个英文句子相对应，中间也使用“$$”进行分割\nTM_DB表示翻译记忆库\nNET_youdao表示网络来源',
  `abbreviationCN` varchar(256) DEFAULT NULL COMMENT '中文简称',
  `abbreviationEN` varchar(256) DEFAULT NULL COMMENT '英文简称或者缩写',
  `synonymCN` varchar(512) DEFAULT NULL COMMENT '中文同义词，多个同义词之间使用中文全角分号：“；”进行分割',
  `synonymEN` varchar(512) DEFAULT NULL COMMENT '英文同义词，多个同义词之间使用英文半角：“;”进行分割',
  `definationCN` varchar(1024) DEFAULT NULL COMMENT '中文词条对应的定义内容',
  `definationCNResource` varchar(512) DEFAULT NULL COMMENT '中文定义的来源或者出处',
  `definationEN` varchar(1024) DEFAULT NULL COMMENT '英文词条定义的内容',
  `definationENResource` varchar(512) DEFAULT NULL COMMENT '英文定义的来源或者出处',
  `status` varchar(64) DEFAULT NULL COMMENT '有两种状态：\nreviewed和unreviewed\r\n',
  `totalClick` int(11) DEFAULT NULL COMMENT '词语的点击和搜索次数，用于计算热词使用'
) ENGINE=InnoDB DEFAULT CHARSET=utf8

