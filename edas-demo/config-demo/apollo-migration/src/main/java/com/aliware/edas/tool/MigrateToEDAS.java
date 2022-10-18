package com.aliware.edas.tool;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.aliware.edas.tool.model.NacosConfig;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.FormatType;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.http.ProtocolType;
import com.aliyuncs.profile.DefaultProfile;

import java.util.List;
import java.util.stream.Collectors;

public class MigrateToEDAS {

    /**
     * 阿里云 Access Key，混合云从 ASCM 中获取。
     */
    private static String ALIYUN_AK = readProperty("ALIYUN_AK");

    /**
     * 阿里云 Secret Key，混合云从 ASCM 中获取。
     */
    private static String ALIYUN_SK = readProperty("ALIYUN_SK");

    /**
     * EDAS 配置管理服务域名：
     *
     * 公共云 参考文档：https://help.aliyun.com/document_detail/156108.html
     * 混合云 需要联系现场运维同学，获取现场 dncs-api.console 开头的域名
     */
    private static String API_DOMAIN = readProperty("API_DOMAIN");

    /**
     * 微服务空间 ID：在任何一套 EDAS 的页面中，可通过进入 "资源管理" -> "微服务空间"，然后在
     * "微服务列表中" 处选择对应的微服务空间后，选择第一列的 UUID (三行值分别代表: 展示名称, namespace , ID)。
     */
    private static String NAMESPACE_ID = readProperty("NAMESPACE_ID");

    /**
     * Region ID：在任何一套 EDAS 的页面中，可通过进入 "资源管理" -> "微服务空间"，然后在
     * "微服务列表中" 第一行 "默认"，选择第一列的 第二行 (如：cn-hz-vpaas-01)。
     */
    private static String REGION_ID = readProperty("REGION_ID");

    private static boolean OverwriteWhenConflicts =
            Boolean.parseBoolean(
                    readProperty("overwriteWhenConflicts", "true")
            );

    public static void main(String[] args) {
        String path = readPath(args);
        List<NacosConfig> configs = readNacosConfigs(path);
        importToEDASConfigCenter(configs);
    }

    private static List<NacosConfig> readNacosConfigs(String path) {
        if (path == null || path.length() == 0) {
            fatal("File path is not specified.");
        }

        if (path.endsWith(".zip")) {
            return ZipFileProcessor
                    .getInstance()
                    .visit(path, NAMESPACE_ID);
        }

        if (path.endsWith("/")) {
            return DirectoryProcessor
                    .getInstance()
                    .visit(path, NAMESPACE_ID);
        }

        fatal("File path not supported: %s", path);
        return null;
    }


    private static String readPath(String[] args) {
        String path = readProperty("FILE_PATH");
        if (path != null && ! path.isEmpty()) {
            log("Find path via property/env, %s", path);
            return path;
        }


        path = args.length > 0 ? args[0] : null;

        if (path == null) {
            fatal("Importing path not find, please execute it like: " +
                    "java com.aliware.edas.tool.MigrateToEDAS <ApolloExportedPath>");
        }

        return path;
    }

    private static void importToEDASConfigCenter(List<NacosConfig> configs) {
        IAcsClient client = initAcsClient();

        if (configs == null || configs.isEmpty()) {
            fatal("Config is empty, nothing need to proceed.");
        }

        configs = configs.stream()
                .filter(c -> c.getContent() != null && c.getContent().length() != 0)
                .collect(Collectors.toList());

        log("Found %s configs to be imported", configs.size());

        for (NacosConfig config : configs) {
            importSingleToEDASConfigCenter(client, config);
        }
    }

    private static void importSingleToEDASConfigCenter(IAcsClient client,
                                                       NacosConfig config) {
        if (hasConfig(client, config)) {
            if (OverwriteWhenConflicts) {
                updateSingleConfig(client, config);
            } else {
                log("Config(%s) existed, ignore importing config into EDAS",
                        config.basicInfo());
            }

            return;
        }
        
        createSingleConfig(client, config);
    }

    private static void createSingleConfig(IAcsClient client, NacosConfig config) {
        dealSingleConfig(client, config, false);
    }

    private static void updateSingleConfig(IAcsClient client, NacosConfig config) {
        dealSingleConfig(client, config, true);
    }

    private static void dealSingleConfig(IAcsClient client,
                                         NacosConfig config,
                                         boolean update) {
        CommonRequest request = new CommonRequest();
        request.setMethod(update ? MethodType.PUT : MethodType.POST);
        request.setDomain(API_DOMAIN);
        request.setVersion("2020-02-06");
        request.setProtocol(ProtocolType.HTTPS);
        request.setUriPattern("/diamond-ops/pop/configuration");

        request.putQueryParameter("RegionId", REGION_ID);
        request.putHeadParameter("Content-Type", "application/x-www-form-urlencoded");

        request.setHttpContent(config.toFormData().getBytes(), "utf-8", FormatType.JSON);
        try {
            CommonResponse response = client.getCommonResponse(request);
            log("Import EDAS config(%s) success.", config.basicInfo());
        } catch (Throwable t) {
            fatal("Import EDAS config(%s) error: %s", config.basicInfo(), t.getMessage());
        }
    }

    private static boolean hasConfig(IAcsClient client, NacosConfig config) {
        CommonRequest request = new CommonRequest();
        request.setMethod(MethodType.GET);
        request.setDomain(API_DOMAIN);
        request.setVersion("2020-02-06");
        request.setProtocol(ProtocolType.HTTPS);
        request.setUriPattern("/diamond-ops/pop/configuration");

        request.putQueryParameter("RegionId", REGION_ID);
        request.setUriPattern("/diamond-ops/pop/configuration");
        request.putQueryParameter("DataId", config.getDataId());
        request.putQueryParameter("Group", config.getGroup());
        request.putQueryParameter("NamespaceId", config.getNamespaceId());
        request.putHeadParameter("Content-Type", "application/x-www-form-urlencoded");

        try {
            CommonResponse response = client.getCommonResponse(request);
            if (response.getData() == null) {
                return false;
            }

            JSONObject object = (JSONObject) JSON.parse(response.getData());

            if (object == null) {
                return false;
            }

            if (object.get("Configuration") == null) {
                return false;
            }

            NacosConfig conf = object.getObject("Configuration", NacosConfig.class);

            if (config.sameConfigKey(conf)) {
                return true;
            }
        } catch (ClientException e) {
            if ("ConfigurationNotExists".equals(e.getErrCode())) {
                return false;
            }

            fatal("Import EDAS config(%s) error: %s", config.basicInfo(), e.getMessage());
        } catch (Throwable t) {
            fatal("Import EDAS config(%s) error: %s", config.basicInfo(), t.getMessage());

        }

        return false;
    }

    private static IAcsClient initAcsClient()  {
        DefaultProfile profile =
                DefaultProfile.getProfile(REGION_ID, ALIYUN_AK, ALIYUN_SK);
        return new DefaultAcsClient(profile);
    }

    private static String readProperty(String key) {
        return readProperty(key, "");
    }

    private static String readProperty(String key, String defaultValue) {
        String val = System.getProperty(key);

        if (val != null) {
            return val;
        }

        val = System.getenv(key);

        return val != null ? val : defaultValue;
    }

    public static void fatal(String s, Object... args) {
        System.err.println(String.format(s, args));
        System.exit(-1);
    }

    public static void log(String s, Object... args) {
        System.out.println(String.format(s, args));
    }
}
