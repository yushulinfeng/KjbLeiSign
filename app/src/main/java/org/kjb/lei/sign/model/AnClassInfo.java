package org.kjb.lei.sign.model;

import java.util.List;

/**
 * 课程信息，包含姓名和课表
 */
public class AnClassInfo {
    private String name;//姓名
    private List<AnClass> table;//课表

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<AnClass> getTable() {
        return table;
    }

    public void setTable(List<AnClass> table) {
        this.table = table;
    }
}
