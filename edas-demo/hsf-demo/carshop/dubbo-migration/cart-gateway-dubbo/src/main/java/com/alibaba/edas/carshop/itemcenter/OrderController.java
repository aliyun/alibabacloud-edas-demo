/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.itemcenter;

import java.io.IOException;
import java.util.List;

import com.alibaba.dubbo.common.json.JSON;
import com.alibaba.dubbo.config.annotation.Reference;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
@Controller
public class OrderController {

	private static final long serialVersionUID = -112210702214857712L;

	@Reference(group="HSF", version = "1.0.0")
	private OrderService orderService;


	@RequestMapping(value="/order/{id}",
		produces = "application/json;charset=UTF-8")
	@ResponseBody
	public List<Item> getOrderInfo(@PathVariable("id") int orderId) {

		return orderService.findItemListByOrderId(orderId);
	}
}
