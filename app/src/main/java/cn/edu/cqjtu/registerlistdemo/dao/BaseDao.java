package cn.edu.cqjtu.registerlistdemo.dao;

import android.content.Context;

import java.util.List;

import cn.edu.cqjtu.registerlistdemo.database.MySqliteOpenHelper;

/**
 * 基础的操作类
 * @author JohnNiang
 *
 * @param <T>
 */
public abstract class BaseDao<T> {

	/**
	 * 自定义数据库帮助类
	 */
	protected MySqliteOpenHelper mySqliteOpenHelper;

	public BaseDao(Context context) {
		// ��ʼ��
		mySqliteOpenHelper = new MySqliteOpenHelper(context);
	}

	/**
	 *
	 * @param bean
	 * @return
     */
	public abstract boolean add(T bean);

	/**
	 *
	 * @param id
	 * @return
     */
	public abstract int delete(String id);

	/**
	 *
	 * @param bean
	 * @return
     */
	public abstract int update(T bean);

	/**
	 *
	 * @return
     */
	public abstract List<T> queryAll();
}
