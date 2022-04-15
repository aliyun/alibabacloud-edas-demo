/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliware.edas.async;

import com.alibaba.boot.hsf.annotation.HSFProvider;
import com.aliware.edas.async.AsyncEchoService;
import com.taobao.hsf.app.api.util.HSFApiProviderBean;

@HSFProvider(serviceInterface = AsyncEchoService.class, serviceVersion = "1.0.0")
public class AsyncEchoServiceImpl implements AsyncEchoService {


    @Override
    public String future(String string) {
        return "ASYNC Future: " + string;
    }

    @Override
    public String callback(String string) {
        return "ASYNC Callback: " + string;
    }
}
