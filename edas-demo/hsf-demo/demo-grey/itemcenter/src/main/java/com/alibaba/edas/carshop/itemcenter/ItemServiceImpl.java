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
import java.util.concurrent.TimeUnit;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public class ItemServiceImpl implements ItemService {

    private MetaService metaService;

    private static int TIMEOUT = 0;

    public static void setTimeout(int timeout) {
        TIMEOUT = timeout;
    }

    @Override
    public Item getItemById(long id) {
        Item car = new Item();
        car.setItemId(1l);
        car.setItemName("Mercedes Benz" + id + " at " + new Date());
        if (metaService != null) {
            car.setMeta(metaService.getMetaById(id));
        }

        if (TIMEOUT > 0) {
            try {
                TimeUnit.SECONDS.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
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

        if (TIMEOUT > 3) {
            try {
                TimeUnit.SECONDS.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (TIMEOUT > 0) {
            throw new IllegalArgumentException("Illegale Args");
        }

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

    @Override
    public void healthCheck() throws HealthCheckException {
        if (TIMEOUT > 1) {
            try {
                TimeUnit.SECONDS.sleep(TIMEOUT);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } else if (TIMEOUT <= -1) {
            throw new HealthCheckException();
        }

        System.out.println("Doing health check.");
    }

    public void setMetaService(MetaService metaService) {
        this.metaService = metaService;
    }

}
