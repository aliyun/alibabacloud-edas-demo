package com.alibabacloud.edas.tool.model;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class NacosConfig {


    private String Group;

    private String NamespaceId;

    private String DataId;

    private String Content;

    public NacosConfig addTag(String tag) {
        if (Tags == null) {
            Tags= new ArrayList<>(2);
            Tags.add(tag);

            return this;
        }

        if (Tags.contains(tag)) {
            return this;
        }

        if (Tags.size() == 5) {
            System.out.println("Warning: ignore tagged " +
                    "this config as tags reached the limit(5), " + DataId);
            return this;
        }

        Tags.add(tag);
        return this;
    }

    private List<String> Tags = null;

    private String Desc = "Imported From Apollo Using EDAS Migration Tool";

    public static NacosConfig createConfig(String tenantId,
                                           String groupId,
                                           String dataId,
                                           String content) {
        NacosConfig config = new NacosConfig();

        config.setNamespaceId(tenantId);
        config.setGroup(groupId);
        config.setDataId(dataId);
        config.setContent(content);

        return config;
    }


    public String getGroup() {
        return Group;
    }

    public void setGroup(String group) {
        this.Group = group;
    }

    public String getNamespaceId() {
        return NamespaceId;
    }

    public void setNamespaceId(String namespaceId) {
        this.NamespaceId = namespaceId;
    }

    public String getDataId() {
        return DataId;
    }

    public void setDataId(String dataId) {
        this.DataId = dataId;
    }

    public String getContent() {
        return Content;
    }

    public void setContent(String content) {
        this.Content = content;
    }

    public String toFormData() {
        String type = "text";
        String dataId = DataId.toLowerCase();
        if (dataId.endsWith("properties")) {
            type = "properties";
        }  else if (dataId.endsWith("yaml") ||
                dataId.endsWith("yml")) {
            type = "yaml";
        }

        String encoded = String.format(
                "NamespaceId=%s&Group=%s&DataId=%s&Content=%s&Type=%s&Desc=%s",
                NamespaceId, Group, DataId,
                preHandleContent(),
                type,
                safeEncode(Desc));

        if (Tags == null) {
            return encoded;
        }

        String tag = Tags.stream().collect(Collectors.joining(","));
        encoded += "&Tags=" + safeEncode(tag);

        return encoded;
    }

    private String preHandleContent() {
        String content = Content
                .replace("\\=", "=")
                .replace("\\:", ":")
                .replace("\\#", "#");
        return safeEncode(content);
    }

    private static String safeEncode(String c) {
        if (c == null) {
            return "";
        }

        try {
            return URLEncoder.encode(c, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Warning, content encoded failed: " + c +
                    ", error: " + e.getLocalizedMessage());
            return c;
        }
    }

    public String basicInfo() {
        return String.format("NamespaceId=%s&Group=%s&DataId=%s", NamespaceId, Group, DataId);
    }

    public boolean sameConfigKey(NacosConfig conf) {

        if (conf == null) {
            return false;
        }

        if (Group == null || ! Group.equals(conf.Group)) {
            return false;
        }

        if (DataId == null || !DataId.equals(conf.DataId)) {
            return false;
        }

//        if (NamespaceId == null || ! NamespaceId.equals(conf.NamespaceId)) {
//            return false;
//        }

        return true;
    }
}
