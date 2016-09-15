package org.kjb.lei.sign.model;

/**
 * 一节课的数据
 */
public class AnClass {
    private int week;//周次，1-7
    private int index;//节次，1-5
    private String name;//课名
    private String place;//地点
    private String teacher;//教师
    private String id;//课程ID
    private int colors = 0;//颜色索引（默认0，无颜色）

    public AnClass(String id, String name, String place, String time) {
        this.setId(id);
        this.setName(name);
        this.setPlace(place);
        this.setTime(time);
    }

    public AnClass(String id, String name, String place, int time,
                   String teacher) {
        this.setId(id);
        this.setName(name);
        this.setPlace(place);
        this.setTime(time);
        this.setTeacher(teacher);
    }

    public int getWeek() {
        return week;
    }

    public void setWeek(int week) {
        this.week = week;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace() {
        return place;
    }

    public void setPlace(String place) {
        this.place = place;
    }

    public String getTeacher() {
        return teacher;
    }

    public void setTeacher(String teacher) {
        this.teacher = teacher;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // MINE

    public int getColor() {
        return colors;
    }

    public void setColor(int color) {
        this.colors = color;
    }

    public int getTime() {
        return (week - 1) * 5 + index;//1-35
    }

    public void setTime(String time) {// 给原生课表使用
        String[] times = time.split("-");
        this.week = Integer.parseInt(times[0]);
        this.index = Integer.parseInt(times[1]);
    }

    public void setTime(int time) {// 给数据库数据使用
        this.week = time / 5 + 1;
        this.index = time % 5;
    }
}
