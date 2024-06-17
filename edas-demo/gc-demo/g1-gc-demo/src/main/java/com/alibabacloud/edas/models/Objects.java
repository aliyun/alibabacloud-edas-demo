package com.alibabacloud.edas.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Objects {
    private byte[] bytes = null;

    public Objects(int size) {
        bytes = new byte[size];
    }


    public static class OneKObject extends Objects {
        public OneKObject() {
            super(1024);
        }

        public static List<OneKObject> toList(int size) {
            if (size < 1) {
                return Collections.emptyList();
            }

            List<OneKObject> lists = new ArrayList<>(size);
            for (int i = 0; i < size; i ++) {
                lists.add(new OneKObject());
            }

            return lists;
        }
    }

    public static class OneMObject extends Objects {
        public OneMObject() {
            super(1024 * 1024);
        }

        public static List<OneMObject> toList(int size) {
            if (size < 1) {
                return Collections.emptyList();
            }

            List<OneMObject> lists = new ArrayList<>(size);
            for (int i = 0; i < size; i ++) {
                lists.add(new OneMObject());
            }

            return lists;
        }

    }

}
