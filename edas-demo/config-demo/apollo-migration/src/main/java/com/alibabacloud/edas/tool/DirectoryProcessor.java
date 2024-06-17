package com.alibabacloud.edas.tool;

import com.alibabacloud.edas.tool.model.NacosConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DirectoryProcessor implements FileProcessor {

    private static DirectoryProcessor INSTANCE = new DirectoryProcessor();

    public static DirectoryProcessor getInstance() {
        return INSTANCE;
    }

    @Override
    public List<NacosConfig> visit(String path, String namespaceId) {
        return readNacosConfigs(path, namespaceId);
    }

    private static List<NacosConfig> readNacosConfigs(String path, String namespace) {
        File dir = new File(path);

        if (! dir.isDirectory()) {
            MigrateToEDAS.fatal("%s is not a valid directory", path);
        }

        return Arrays.asList(dir.listFiles())
                .stream()
                .map(file -> readConfigFromFile(file, namespace))
                .collect(Collectors.toList());
    }

    private static NacosConfig readConfigFromFile(File file, String namespace) {
        String name = file.getName();

        String[] args = name.split("\\+");
        String group = args[1];
        if ("default".equals(group)) {
            group = "DEFAULT_GROUP";
        }

        String dataId = args[2];
        String content = null;
        try {
            content = new String(Files.readAllBytes(file.toPath()), "UTF-8");
        } catch (IOException e) {
            MigrateToEDAS.fatal("Reading content from file(%s) error(%s)", name, e.getMessage());
        }

        return NacosConfig.createConfig(namespace, group, dataId, content);
    }
}
