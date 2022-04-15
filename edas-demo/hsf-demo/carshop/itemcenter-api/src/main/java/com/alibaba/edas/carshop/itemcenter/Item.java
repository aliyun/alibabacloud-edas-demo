/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.itemcenter;

import java.io.Serializable;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public class Item implements Serializable  {

	private Long itemId;
	private String itemName;

	public Long getItemId() {
		return itemId;
	}

	public void setItemId( Long itemId ) {
		this.itemId = itemId;
	}

	public String getItemName() {
		return itemName;
	}

	public void setItemName( String itemName ) {
		this.itemName = itemName;
	}
	
	@Override
	public String toString() {
		return "Item[id: " + itemId + ", name: " + itemName + "]";
	}

}
