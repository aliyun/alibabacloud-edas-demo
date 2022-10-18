package com.aliware.edas.tool.model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ConfigPathList {

    private static Pattern DirNamePattern = Pattern.compile("^/apollo/([^/]+)/.*");

    private static Pattern IFELC_APP = Pattern.compile("(ifelc.*?)-ms(.*?)-api(.*)");

    private static Collection SHARED_CONFIG = Arrays.asList(
            "basic-properties",
            "datasource-account-manager",
            "datasource-manager"
    );

    private static Map<String, ConfigPathList> ROOT = new HashMap<>();

    private String dirName;

    private boolean sharedConfig;

    private List<Path> paths = new ArrayList<>(8);

    public ConfigPathList(String dirName, boolean sharedConfig) {
        this.dirName = dirName;
        this.sharedConfig = sharedConfig;
    }

    public static List<NacosConfig> resolveNacosConfigs(String namespaceId) {
        return ROOT.values()
                .stream()
                .map(p -> p.resolve(namespaceId))
                .collect(Collectors.toList());
    }

    public NacosConfig resolve(String namespaceId) {
        if (paths == null || paths.isEmpty()) {
            return null;
        }

        String mergedContent = resolveContent();
        String dataId = resolveDataId();
        String group = "DEFAULT_GROUP";

        return NacosConfig.createConfig(namespaceId, group, dataId, mergedContent);
    }

    public static void append(Path path) {

        String pathName = path.toString();
        Matcher m = DirNamePattern.matcher(pathName);

        if (! m.matches()) {
            System.out.println("Ignore unmatched path: " + pathName);
            return;
        }

        String appName = m.group(1);

        // 如果应用名是配置好的 共享配置，则每一项对应一个单独的配置。否则一个应用下所有内容会 Merge 成一份。
        boolean isSharedConfig = SHARED_CONFIG.contains(appName) ;
        String key = isSharedConfig ? pathName : appName;

        // 共享配置忽略 "application.properties"
        if (isSharedConfig && pathName.endsWith("application.properties")) {
            return;
        }

        ConfigPathList configPath = ROOT.get(key);
        if (configPath == null) {
            configPath = new ConfigPathList(appName, isSharedConfig);
            ROOT.put(key, configPath);
        }

        configPath.paths.add(path);
    }

    public String resolveDataId() {
        Matcher m = IFELC_APP.matcher(dirName);

        /*
         * Case 2: 满足 IFELC 命名规范的应用 ，去除 "-ms" "-api" 作为应用配置
         *
         * 2）ifelc 下的应用私有配置导入时，会根据一定的规则进行导入，
         * 规则举例：ifelc-ms-iasd-api+default+application.properties
         * 变成 ifelc-iasd.properties(去掉 -ms 和 -api) 。
         */
        if (m.matches()) {
            return String.format("%s%s%s.properties", m.group(1), m.group(2), m.group(3));
        }

        /*
         * Case 5: ISV 应用处理
         *
         * 非 ifelc 的应用(ieo-*, mfps-*, opt-* 三类)，为 ISV 的应用，需要额外进行确认 应用私有配置的命名规则。
         *
         * > 补充回复： 直接把里面对应的application配置也转换为对应的目录名称就可以了
         */
        if (! sharedConfig) {
            return String.format("%s.properties", dirName);
        }

        /*
         * Case 3: 公共配置，配置条数 一一对应 ，对应取 "+" 切分之后的 字段，如：
         *  basic-properties/FAT/basic-properties+default+femsp.redis.properties
         *   取
         *  femsp.redis.properties
         */
        String pathName = paths
                .get(0)
                .toString()
                .split("\\+")[2]
                ;


        return pathName;
    }

    private String resolveContent() {
        return paths.stream().sorted().map((x) -> {
            try {
                byte[] bytes = Files.readAllBytes(x);
                return new String(bytes, "utf-8");
            } catch (IOException e) {
                System.err.println("Reading content error: " +e.getMessage());
                return null;
            }
        }).reduce("", (result, element) -> {
            if (element == null) {
                return result;
            }

            if (result == null || result.length() == 0) {
                return element;
            }

            return result + "\r\n" + element;
        });
    }

}
