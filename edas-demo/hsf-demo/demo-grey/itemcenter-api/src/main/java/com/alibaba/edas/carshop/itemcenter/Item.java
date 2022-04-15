/**
 * Copyright (c) 2022-present Alibaba Group
 *
 * This source code is licensed under the MIT license found in the
 * LICENSE file in the root directory of this source tree.
 */

package com.alibaba.edas.carshop.itemcenter;

/**
 * Alibaba Group EDAS. http://www.aliyun.com/product/edas
 */
public class Item {

    private Long itemId;
    private String itemName;
    private String processBy;
    
    private Meta meta;

    public Long getItemId() {
        return itemId;
    }

    public void setItemId(Long itemId) {
        this.itemId = itemId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }
    
    

    public String getProcessBy() {
        return processBy;
    }

    public void setProcessBy(String processBy) {
        this.processBy = processBy;
    }

    public Meta getMeta() {
        return meta;
    }

    public void setMeta(Meta meta) {
        this.meta = meta;
    }

    @Override
    public String toString() {
        return "Item [itemId=" + itemId + ", itemName=" + itemName + ", <span class=\"highlight\">processBy=" + processBy
                + "</span>\n\t"
                + ", meta=" + meta
                + "]";
    }

    

}
