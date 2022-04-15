/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.itemcenter;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import com.alibaba.dubbo.config.annotation.Reference;
import com.alibaba.dubbo.config.annotation.Service;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
@Component
@Service(group = "HSF", version = "1.0.0")
public class OrderServiceImpl implements OrderService, InitializingBean {

	@Reference(group = "HSF", version = "1.0.0")
	private ItemService itemService;

	@Override
	public List<Item> findItemListByOrderId(final int orderId) {
		System.out.println("findItemListByOrderId : "+ orderId);

		List<Item> results = Arrays.asList(1,2,3,4,5,6,7,8,9,10)
			.stream()
            .map( i -> i + orderId * 10)
			.map( i -> itemService.getItemById(i))
			.collect(Collectors.toList());

		return results;
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		System.out.println(" ==== Dubbo Service Initiated. ==== ");
	}
}
