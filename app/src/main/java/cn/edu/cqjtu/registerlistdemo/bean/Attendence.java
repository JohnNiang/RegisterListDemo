package cn.edu.cqjtu.registerlistdemo.bean;


/**
 * Created by JohnNiang on 2016/10/31.
 */
public class Attendence {

    private Integer atteId;
    private String atteSno;
    private Character atteType;
    private Integer atteNum;

    public Attendence(String atteSno, Character atteType, Integer atteNum) {
        super();
        this.atteSno = atteSno;
        this.atteType = atteType;
        this.atteNum = atteNum;
    }

    public Attendence(Integer atteId, String atteSno, Character atteType, Integer atteNum) {
        super();
        this.atteId = atteId;
        this.atteSno = atteSno;
        this.atteType = atteType;
        this.atteNum = atteNum;
    }

    public Integer getAtteId() {
        return atteId;
    }

    public void setAtteId(Integer atteId) {
        this.atteId = atteId;
    }

    public String getAtteSno() {
        return atteSno;
    }

    public void setAtteSno(String atteSno) {
        this.atteSno = atteSno;
    }

    public Character getAtteType() {
        return atteType;
    }

    public void setAtteType(Character atteType) {
        this.atteType = atteType;
    }

    public Integer getAtteNum() {
        return atteNum;
    }

    public void setAtteNum(Integer atteNum) {
        this.atteNum = atteNum;
    }

}
