/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.aliware.edas;

import com.alibaba.boot.hsf.annotation.HSFConsumer;
import com.alibaba.boot.hsf.annotation.HSFProvider;
import com.taobao.hsf.remoting.service.GenericService;
import com.taobao.pandora.boot.test.junit4.DelegateTo;
import com.taobao.pandora.boot.test.junit4.PandoraBootRunner;
import junit.framework.TestCase;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;


@RunWith(PandoraBootRunner.class)
@DelegateTo(SpringJUnit4ClassRunner.class)
// 加载测试需要的类，一定要加入 Spring Boot 的启动类，其次需要加入本类
@SpringBootTest(classes = {HSFProviderApplication.class, EchoServiceTest.class })
@Component
public class EchoServiceTest {


    /**
     * 当这里使用 @HSFConsumer 时，一定要在 @SpringBootTest 类加载中，加载本类，通过本类来注入对象，否则当做泛化时，会报类转换异常
     */

    @HSFConsumer(generic = true)
    EchoService echoService;


    /**
     * 测试普通调用
     */
    @Test
    public void testInvoke() {
        TestCase.assertEquals("hello world", echoService.echo("hello world"));
    }


    /**
     * 测试泛化调用
     */
    @Test
    public void testGenericInvoke() {
        GenericService service = (GenericService) echoService;
        Object result = service.$invoke("echo", new String[] {"java.lang.String"}, new Object[] {"hello world"});
        TestCase.assertEquals("hello world", result);
    }

    /**
     * 测试Mock返回值
     */
    @Test
    public void testMock() {
    }
}
