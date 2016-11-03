package cn.edu.cqjtu.registerlistdemo.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import cn.edu.cqjtu.registerlistdemo.bean.Student;

/**
 * 数据库工具类
 *
 * @author JohnNiang
 */
public class DatabaseUtil {
    private DatabaseUtil() {
    }

    /**
     * 从文件中读取所有学生信息
     *
     * @param fileName 文本文件名
     * @param splitCh  分割字符
     * @return 返回所有的学生信息，如果为null则表示文件转换失败或文件为空
     * @throws FileNotFoundException 文件打开错误
     * @throws IOException           读取文件出错或者输入流关闭错误
     */
    public static List<Student> getStudentsFromFile(Context context, String fileName, char splitCh) {
        List<Student> list = null;

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(context.getResources().getAssets().open(fileName)));
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
                line = line.replace(" ", "");
                if (list == null) {
                    list = new ArrayList<Student>();
                }
                String[] items = line.trim().split(splitCh + "");
                if (items.length == 5) {
                    Student student = new Student();
                    student.setSno(items[0]);
                    student.setSname(items[1]);
                    student.setSclass(items[2]);
                    student.setImage(null);
                    student.setScore(Short.valueOf(items[4]));
                    list.add(student);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("FileNotFoundException!");
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("BufferedReader.readLine() Exception.");
            e.printStackTrace();
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (IOException e) {
                System.err.println("InputStream Close Exception.");
                e.printStackTrace();
            }
        }
        return list;
    }
}
