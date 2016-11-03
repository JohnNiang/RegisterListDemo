package cn.edu.cqjtu.registerlistdemo.bean;

import android.graphics.Bitmap;

/**
 * Created by JohnNiang on 2016/10/31.
 */
public class Student {
    /**
     * A dummy item representing a piece of content.
     */
    private String sno;
    private String sname;
    private String sclass;
    private Bitmap image;
    private Short score;


    public Student() {
        super();
    }

    public Student(String sname, String sclass, Bitmap image, Short score) {
        super();
        this.sname = sname;
        this.sclass = sclass;
        this.image = image;
        this.score = score;
    }

    public Student(String sno, String sname, String sclass, Bitmap image, Short score) {
        super();
        this.sno = sno;
        this.sname = sname;
        this.sclass = sclass;
        this.image = image;
        this.score = score;
    }

    public Student(String sno, Short score) {
        this.sno = sno;
        this.score = score;
    }

    public String getSno() {
        return sno;
    }

    public void setSno(String sno) {
        this.sno = sno;
    }

    public String getSname() {
        return sname;
    }

    public void setSname(String sname) {
        this.sname = sname;
    }

    public String getSclass() {
        return sclass;
    }

    public void setSclass(String sclass) {
        this.sclass = sclass;
    }

    public Bitmap getImage() {
        return image;
    }

    public void setImage(Bitmap image) {
        this.image = image;
    }

    public Short getScore() {
        return score;
    }

    public void setScore(Short score) {
        this.score = score;
    }
}
