/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.domain;

/**
*
* @author xiaomeng.hxm
*/
public class PageTask {
    private String tableName;
    private long startId;
    private long endId;

    public PageTask(String tableName, long startId, long endId) {
        this.tableName = tableName;
        this.startId = startId;
        this.endId = endId;
    }

    public String getTableName() {
        return tableName;
    }

    public long getStartId() {
        return startId;
    }

    public long getEndId() {
        return endId;
    }
}
