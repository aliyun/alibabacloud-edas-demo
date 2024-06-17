package com.alibabacloud.edas.tool;

import com.alibabacloud.edas.tool.model.NacosConfig;

import java.util.List;

public interface FileProcessor {
    /**
     * Visit a file path, return the NacosConfig List.
     *
     * @param path
     * @return
     */
    List<NacosConfig> visit(String path, String namespaceId);
}
