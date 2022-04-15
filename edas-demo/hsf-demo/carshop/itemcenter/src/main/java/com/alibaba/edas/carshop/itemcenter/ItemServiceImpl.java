/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.itemcenter;

import com.taobao.eagleeye.EagleEye;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public class ItemServiceImpl implements ItemService {

	@Override
	public Item getItemById( long id ) {
		Item car = new Item();
		car.setItemId( id );
		car.setItemName( "漏油车	: " + EagleEye.getTraceId());
		return car;
	}

	@Override
	public Item getItemByName( String name ) {
		Item car = new Item();
		car.setItemId( 1l );
		car.setItemName( name + ":" + EagleEye.getTraceId());
		return car;
	}
}
