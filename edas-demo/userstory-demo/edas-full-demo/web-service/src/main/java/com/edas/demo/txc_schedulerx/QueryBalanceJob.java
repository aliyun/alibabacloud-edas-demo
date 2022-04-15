/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.edas.demo.txc_schedulerx;

import com.alibaba.schedulerx.worker.domain.JobContext;
import com.alibaba.schedulerx.worker.processor.JavaProcessor;
import com.alibaba.schedulerx.worker.processor.ProcessResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class QueryBalanceJob extends JavaProcessor {

    @Autowired
    AccountRepository accQuery;

    @Autowired
    private BalanceCache cache;

    @Override
    public ProcessResult process(JobContext jobContext) throws Exception {
        String moneyA = accQuery.getMoneyA();
        cache.addBalance("A",moneyA);
        String moneyB = accQuery.getMoneyB();
        cache.addBalance("B",moneyB);
        return new ProcessResult(true);
    }
}
