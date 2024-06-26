package com.alibabacloud.edas.tool;

import com.alibabacloud.edas.tool.model.ConfigPathList;
import com.alibabacloud.edas.tool.model.NacosConfig;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public class ZipFileProcessor implements FileProcessor {

    private static ZipFileProcessor INSTANCE = new ZipFileProcessor();

    public static ZipFileProcessor getInstance() {
        return INSTANCE;
    }

    private void walk(String zipFile) {
        if (zipFile == null || ! zipFile.endsWith(".zip")) {
            throw new IllegalArgumentException("Invalid zip file: " + zipFile);
        }

        try {
            FileSystems.newFileSystem(Paths.get(zipFile),
                    ZipFileProcessor.class.getClassLoader())
                    .getRootDirectories()
                    .forEach(this::visitZipRoot);
        } catch (IOException e) {
            System.err.println("Invalid zip file: " + e.getMessage());
            System.exit(-1);
        }
    }

    private void visitZipRoot(Path root) {
        try {
            Files.walk(root).forEach(this::visitPropertyFile);
        } catch (IOException e) {
            System.err.println("Invalid property file: " + e.getMessage());
            System.exit(-1);
        }
    }

    private void visitPropertyFile(Path p) {
        if (! p.toString().endsWith(".properties")) {
            return;
        }

        ConfigPathList.append(p);
    }

    public static void main(String[] args) {

        System.out.println("Done");
    }

    @Override
    public List<NacosConfig> visit(String path, String namespaceId) {
        walk(path);

        return ConfigPathList
                .resolveNacosConfigs(namespaceId);
    }
}
