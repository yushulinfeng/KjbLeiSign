package org.kjb.lei.sign.model;

import android.graphics.Color;
import android.text.TextUtils;

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

    public static int[] colors = {Color.argb(0, 0, 0, 0), Color.rgb(205, 205, 255),
            Color.rgb(207, 237, 247), Color.rgb(168, 207, 222), Color.rgb(218, 240, 176),
            Color.rgb(240, 240, 176), Color.rgb(247, 207, 182), Color.rgb(255, 177, 177)
    };

    public static void initTableColor(List<AnClass> table) {// 颜色匹配(目前仅设定了7种颜色)
        if (table == null || table.size() == 0) return;
        int color = 0;
        for (int i = 0; i < table.size(); i++) {
            AnClass a = table.get(i);
            if (TextUtils.isEmpty(a.getName()))//无课
                continue;
            if (a.getColor() == 0)//未处理
                a.setColor(color++ % 6 + 1);
            for (int j = 0; j < table.size(); j++) {
                AnClass b = table.get(j);
                if (b.getColor() != 0)
                    continue;
                else if (a.getName().equals(b.getName()))
                    b.setColor(a.getColor());
            }
        }
    }

}
