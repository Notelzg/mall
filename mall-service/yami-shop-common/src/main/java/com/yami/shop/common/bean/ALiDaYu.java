/*
 * Copyright (c) 2018-2999 广州亚米信息科技有限公司 All rights reserved.
 *
 * https://www.gz-yami.com/
 *
 * 未经允许，不可做商业用途！
 *
 * 版权所有，侵权必究！
 */

package com.yami.shop.common.bean;

import lombok.Data;

/**
 * 阿里大鱼配置信息
 * @author LGH
 */
@Data
public class ALiDaYu {

	private String accessKeyId;
	
	private String accessKeySecret;
	
	private String signName;
}
