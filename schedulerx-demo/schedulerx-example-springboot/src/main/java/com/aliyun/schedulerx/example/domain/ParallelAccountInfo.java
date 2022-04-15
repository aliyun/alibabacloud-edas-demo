/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliyun.schedulerx.example.domain;

import java.util.HashMap;
import java.util.Map;

import com.alibaba.schedulerx.worker.processor.BizSubTask;

/**
 * 并行计算模拟业务对象信息，业务对象需要实现BizSubTask接口
 * @author yaohui
 * @create 2022/3/16 下午6:14
 **/
public class ParallelAccountInfo implements BizSubTask {

    /**
     * 主键
     */
    private long id;

    private String name;

    private String accountId;

    public ParallelAccountInfo(long id, String name, String accountId) {
        this.id = id;
        this.name = name;
        this.accountId = accountId;
    }

    /**
     * 实现labelMap方法，用于设置对应子任务的标签信息
     * @return
     */
    @Override
    public Map<String, String> labelMap() {
        Map<String, String> labelMap = new HashMap();
        labelMap.put("户名", name);
        return labelMap;
    }

}