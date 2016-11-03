package cn.edu.cqjtu.registerlistdemo.dao;

/**
 * Created by JohnNiang on 2016/11/2.
 */

/**
 * 当点名表中的某一项点击的接口
 */
public interface OnCheckListInteractionListener {
    void onCheckListInteraction(String sno, int type);
}
