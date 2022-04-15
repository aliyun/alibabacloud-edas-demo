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
public class MetaServiceImpl implements MetaService {

    @Override
    public Meta getMetaById(long id) {
        Meta meta = new Meta();
        meta.setMetaId(1l);
        meta.setMetaName("Meta Name Car " + id + " at " + new Date());
        try {
            meta.setProcessBy(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException("", e);
        }
        return meta;
    }

    @Override
    public Meta getMetaByName(String name) {
        Meta car = new Meta();
        car.setMetaId(1l);

        try {
            car.setMetaName("Meta Name Car " + name + " at " + new Date());
            car.setProcessBy(InetAddress.getLocalHost().getHostAddress());
        } catch (UnknownHostException e) {
            throw new RuntimeException("", e);
        }
        return car;
    }
}
