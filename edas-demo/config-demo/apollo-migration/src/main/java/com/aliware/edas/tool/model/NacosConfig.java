package com.aliware.edas.tool.model;

import java.net.URLEncoder;

public class NacosConfig {
    private String Group;

    private String NamespaceId;

    private String DataId;

    private String Content;

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
        return String.format("NamespaceId=%s&Group=%s&DataId=%s&Content=%s&Type=text&Desc=%s",
                getNamespaceId(), getGroup(), getDataId(), URLEncoder.encode(getContent()), Desc);
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
