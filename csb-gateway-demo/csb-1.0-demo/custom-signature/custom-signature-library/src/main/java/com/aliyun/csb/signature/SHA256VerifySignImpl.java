package com.aliyun.csb.signature;

import com.alibaba.csb.sdk.security.SortedParamList;
import com.alibaba.csb.security.spi.VerifySignService;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.util.Objects;

/**
 * broker 验签示例。本示例利用 HSHA256 算法对请求进行验签
 * @author 泊闻
 */
public class SHA256VerifySignImpl implements VerifySignService {
    private static final Logger logger = LoggerFactory.getLogger(SHA256VerifySignImpl.class);

    @Override
    public boolean verifySignature(SortedParamList paramNodeList, String accessKey, String signature, String algorithm) {
        logger.info("signature in request: " + signature);
        String data = paramNodeList.toString();
        // 需要修改实际解密时使用的密钥，必须与加密时使用的相同，才能保证验签成功
        String secret = "demo";
        try {
            Mac mac = Mac.getInstance("HmacSHA256");
            mac.init(new SecretKeySpec(secret.getBytes(), "HmacSHA256"));
            String verifySig = new String(Base64.encodeBase64(mac.doFinal(data.getBytes())));
            return Objects.equals(signature, verifySig);
        } catch (Exception e) {
            logger.error("验签异常，签名验证失败", e);
            return false;
        }
    }
}
