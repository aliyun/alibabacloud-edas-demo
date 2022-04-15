/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliware.edas;

import com.aliware.edas.async.AsyncEchoService;
import com.aliware.edas.async.AsyncEchoServiceImpl;
import com.taobao.hsf.app.api.util.HSFApiProviderBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

@Service
public class BatchRegisterService implements InitializingBean {
    public void init() {
    }

    private void initProviders(String group) {
        HSFApiProviderBean hsfApiProviderBean = new HSFApiProviderBean();

        hsfApiProviderBean.setServiceInterfaceClass(AsyncEchoService.class);

        hsfApiProviderBean.setServiceGroup(group);

        hsfApiProviderBean.setTarget(new AsyncEchoServiceImpl());

        hsfApiProviderBean.setServiceVersion("1.0.0." + group);

        try {
            System.out.println("==================== start to register hsf services ........... " + group);
            hsfApiProviderBean.init();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        int count = getHSFCount();
        System.out.println("Will publish " + count + " HSF Services ....");
        for (int i = 0; i < count; i ++) {
            String prefix = getServiceGroup();
            initProviders(prefix + "-" + i);
        }

    }

    private String getServiceGroup() {
        String groupId = System.getProperty("project.name",
            "default-service-group");

        int endIndex = Math.min(groupId.length(), 16);
        return groupId.substring(0, endIndex - 1);
    }

    private int getHSFCount() {
        String count = System.getProperty(
            "service.publish.count", "10");
        try {
            return Integer.parseInt(count);
        } catch (Throwable t) {
            return 10000;
        }
    }
}
