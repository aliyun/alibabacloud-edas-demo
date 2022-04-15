/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.itemcenter;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public class ItemServiceImpl implements ItemService {

    private MetaService metaService;

    @Override
    public Item getItemById(long id) {
        Item car = new Item();
        car.setItemId(1l);
        car.setItemName("Mercedes Benz" + id + " at " + new Date());
        if (metaService != null) {
            car.setMeta(metaService.getMetaById(id));
        }
        try {
            car.setProcessBy(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException("", e);
        }
        return car;
    }

    @Override
    public Item getItemByName(String name) {
        Item car = new Item();
        car.setItemId(1l);

        try {
            car.setItemName(
                    "from Mercedes Benz" + name + " at " + new Date());
            if (metaService != null) {
                car.setMeta(metaService.getMetaByName(name));
            }
            car.setProcessBy(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException("", e);
        }
        return car;
    }

    public void setMetaService(MetaService metaService) {
        this.metaService = metaService;
    }

}
