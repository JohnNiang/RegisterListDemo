package cn.edu.cqjtu.registerlistdemo;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import cn.edu.cqjtu.registerlistdemo.adapter.MyCheckRecyclerViewAdapter;
import cn.edu.cqjtu.registerlistdemo.bean.Attendence;
import cn.edu.cqjtu.registerlistdemo.bean.Student;
import cn.edu.cqjtu.registerlistdemo.content.AttendenceContent;
import cn.edu.cqjtu.registerlistdemo.content.StudentContent;
import cn.edu.cqjtu.registerlistdemo.dao.AttendenceDao;
import cn.edu.cqjtu.registerlistdemo.dao.OnCheckListInteractionListener;
import cn.edu.cqjtu.registerlistdemo.dao.StudentDao;
import cn.edu.cqjtu.registerlistdemo.dto.Dto;
import cn.edu.cqjtu.registerlistdemo.util.StringUtil;

public class CheckActivity extends AppCompatActivity implements OnCheckListInteractionListener {

    public static final String ACTION = "cn.edu.cqjtu.registerlistdemo.intent.action.CheckActivity";

    private RecyclerView mRecyclerView;

    private Dialog mLoadingDialog;

    private Dialog mSuccessDialog;

    private Dialog mFailedDialog;

    public static Map<String, Integer> ATTENDENCE_MAP = new HashMap<>();

    private boolean submited = false;

    private boolean changed = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        ActionBar actionBar = getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        notifyToChoose();
        init();
    }

    private void init() {
        View view = findViewById(R.id.list);
        if (view instanceof RecyclerView) {
            System.out.println("init" + view.getId());
            mRecyclerView = (RecyclerView) view;
            mRecyclerView.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));
            mRecyclerView.setAdapter(new MyCheckRecyclerViewAdapter(StudentContent.ITEMS, this));
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_submit) {
            // TODO: 2016/11/2 提示是否确认提交
            if (!submited) {
                submited = true;
                // TODO: 2016/11/2 提示选择课程次
                // 如果内容已经改变，则进行写入数据库
                int num = Dto.getDto().getmRoolCallNumber();
                AttendenceDao dao = Dto.getDto().getAtteDao();
                List<Attendence> atteList = new ArrayList<>();
                List<Student> stuList = new ArrayList<>();
                for (String key : ATTENDENCE_MAP.keySet()) {
                    int value = ATTENDENCE_MAP.get(key);
                    int score = StudentContent.ITEM_MAP.get(key).getScore();
                    switch (value) {
                        case 2:
                            score -= Dto.ASK_FOR_LEAVE_RULE;
                            break;
                        case 3:
                            score -= Dto.LEAVE_EARLIER_RULE;
                            break;
                        case 4:
                            score -= Dto.LATE_RULE;
                            break;
                        case 5:
                            score -= Dto.ABSENT_RULE;
                            break;
                    }
                    if (score < 0) {
                        score = 0;
                    }
                    stuList.add(new Student(key, (short) score));
                    char type = String.valueOf(value).charAt(0);
                    Attendence newAtte = new Attendence(key, type, num);
                    atteList.add(newAtte);
                }
                MyTaskParams params = new MyTaskParams(atteList, stuList);
                // TODO: 2016/11/2 异步操作插入数据库
                InsertDataTask task = new InsertDataTask();
                task.execute(params);
            } else {
                Toast.makeText(this, "您未修改过", Toast.LENGTH_SHORT).show();
            }
        } else if (id == android.R.id.home) {
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private class MyTaskParams {
        List<Attendence> mAtteList;
        List<Student> mStuList;

        public MyTaskParams(List<Attendence> mAtteList, List<Student> mStuList) {
            this.mAtteList = mAtteList;
            this.mStuList = mStuList;
        }
    }

    private class InsertDataTask extends AsyncTask<MyTaskParams, Void, Void> {

        private boolean successed = false;

        @Override
        protected Void doInBackground(MyTaskParams... params) {
            List<Attendence> atteList = params[0].mAtteList;
            List<Student> stuList = params[0].mStuList;
            int num = Dto.getDto().getmRoolCallNumber();
            AttendenceDao dao = Dto.getDto().getAtteDao();
            StudentDao stuDao = Dto.getDto().getStuDao();
            if (dao.addAll(atteList) && stuDao.updateScore(stuList)) {
                // 如果插入成功则进行以下操作
                AttendenceContent.refreshData();
                // 成功
                successed = true;
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            if (successed) {
                showSuccessDialog();
            } else {
                showFailedDialog();
            }
            try {
                TimeUnit.SECONDS.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            closeDialog();
            closeSuccessDialog();
            closeFailedDialog();
            onBackPressed();
            super.onPostExecute(aVoid);
        }

        @Override
        protected void onPreExecute() {
            //            showLoadingDialog();
            //            showSuccessDialog();
            showFailedDialog();
        }
    }

    @Override
    public void onBackPressed() {
        if (!changed || submited) {
            super.onBackPressed();
        } else {
            // TODO: 2016/11/2 提示用户是否退出
            checkBeforBack();
        }
    }

    private void notifyToChoose() {
        AttendenceContent.refreshData();
        // 已经选择了的课程次的集合
        Set<Integer> set = AttendenceContent.ITEM_MAP.keySet();
        List<String> remain = new ArrayList<String>();
        for (int i = 1; i <= Dto.MAX_ROOL_CALLNUMER; i++) {
            if (!set.contains(i)) {
                remain.add(i + "");
            }
        }
        final String[] items = StringUtil.listToArray(remain);
        if (items != null) {
            new AlertDialog.Builder(this).setTitle("温馨提示:请选择课程次").setItems(items, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    // 初始化课程次
                    Dto.getDto().setmRoolCallNumber(Integer.valueOf(items[i]));
                    Toast.makeText(CheckActivity.this, "请注意：您选择了：第" + items[i] + " 次课程", Toast.LENGTH_SHORT).show();
                }
            }).setNegativeButton("取消", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.dismiss(); // 关闭该对话框
                    CheckActivity.this.finish();
                }
            }).show();
        }
    }

    /**
     * 在退出此界面前提示是否提交
     */
    private void checkBeforBack() {
        new AlertDialog.Builder(this).setTitle("温馨提示").setMessage("您修改的内容还未提交，是否确认退出").setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        }).setNegativeButton("返回", null).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.check_submit_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public void onCheckListInteraction(String sno, int type) {
        // TODO: 2016/11/2 进行保存选中的数据，课程次在Dto中进行选择
        if (ATTENDENCE_MAP.containsKey(sno) && ATTENDENCE_MAP.get(sno) != type) {
            ATTENDENCE_MAP.remove(sno);
        }
        changed = true;
        ATTENDENCE_MAP.put(sno, type);
    }

    /**
     * 显示Dialog
     */
    private void showLoadingDialog() {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog.createLoadingDialog(this, "正在保存中...");
            mLoadingDialog.show();
        }
    }

    private void showSuccessDialog() {
        if (mSuccessDialog == null) {
            mSuccessDialog = LoadingDialog.createSuccessDialog(this, "保存成功");
            mSuccessDialog.show();
        }
    }

    private void showFailedDialog() {
        if (mFailedDialog == null) {
            mFailedDialog = LoadingDialog.createSuccessDialog(this, "保存成功");
            mFailedDialog.show();
        }
    }

    /**
     * 关闭Dialog
     */
    private void closeDialog() {
        if (mLoadingDialog != null) {
            mLoadingDialog.dismiss();
            mLoadingDialog = null;
        }
    }

    private void closeSuccessDialog() {
        if (mSuccessDialog != null) {
            mSuccessDialog.dismiss();
            mSuccessDialog = null;
        }
    }

    private void closeFailedDialog() {
        if (mFailedDialog != null) {
            mFailedDialog.dismiss();
            mFailedDialog = null;
        }
    }
}

