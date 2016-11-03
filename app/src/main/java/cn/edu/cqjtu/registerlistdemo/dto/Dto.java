package cn.edu.cqjtu.registerlistdemo.dto;

import android.content.Context;

import java.util.List;

import cn.edu.cqjtu.registerlistdemo.bean.Attendence;
import cn.edu.cqjtu.registerlistdemo.bean.Student;
import cn.edu.cqjtu.registerlistdemo.dao.AttendenceDao;
import cn.edu.cqjtu.registerlistdemo.dao.StudentDao;

public class Dto {

    private static Dto DTO = new Dto();

    private Dto() {
    }

    public static Dto getDto() {
        return DTO;
    }

    /*****************
     * Data Block
     *******************/

    private List<Student> mStudentList;

    private List<Attendence> mAttendenceList;

    private Context mContext;

    public static int MAX_ROOL_CALLNUMER = 36;

    private int mRoolCallNumber = 0;

    public static int ASK_FOR_LEAVE_RULE = 5;
    public static int LEAVE_EARLIER_RULE = 10;
    public static int LATE_RULE = 20;
    public static int ABSENT_RULE = 30;

    private StudentDao stuDao;

    private AttendenceDao atteDao;


    /***********************************************/

    /***************
     * Getter and Setter
     *************/

    public List<Student> getmStudentList() {
        return mStudentList;
    }

    public void updateStudentList() {
        if (stuDao != null) {
            mStudentList = stuDao.queryAll();
            System.out.println("queryAll Student");
        }
    }

    public StudentDao getStuDao() {
        return stuDao;
    }

    public void setStuDao(StudentDao dao) {
        this.stuDao = dao;
    }

    public void setAtteDao(AttendenceDao atteDao) {
        this.atteDao = atteDao;
    }

    public AttendenceDao getAtteDao() {
        return atteDao;
    }

    public void setmStudentList(List<Student> list) {
        this.mStudentList = list;
    }

    public List<Attendence> getmAttendenceList() {
        return mAttendenceList;
    }

    public void setmAttendenceList(List<Attendence> mAttendenceList) {
        this.mAttendenceList = mAttendenceList;
    }

    public Context getmContext() {
        return mContext;
    }

    public void setmContext(Context mContext) {
        this.mContext = mContext;
    }

    public int getmRoolCallNumber() {
        return mRoolCallNumber;
    }

    public void setmRoolCallNumber(int mRoolCallNumber) {
        this.mRoolCallNumber = mRoolCallNumber;
    }

    /*********************************************/
}
