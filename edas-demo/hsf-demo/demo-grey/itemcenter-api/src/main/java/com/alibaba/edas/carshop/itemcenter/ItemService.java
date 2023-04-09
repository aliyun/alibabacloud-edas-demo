/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.itemcenter;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public interface ItemService {

	public Item getItemById( long id );
	
	public Item getItemByName( String name );

	public void healthCheck() throws HealthCheckException;
	
	
}
