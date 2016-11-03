package cn.edu.cqjtu.registerlistdemo;

import android.app.AlertDialog;
import android.app.SearchManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import cn.edu.cqjtu.registerlistdemo.bean.Student;
import cn.edu.cqjtu.registerlistdemo.content.AttendenceContent;
import cn.edu.cqjtu.registerlistdemo.content.StudentContent;
import cn.edu.cqjtu.registerlistdemo.dto.Dto;


public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, StudentFragment.OnListFragmentInteractionListener {


    private boolean firstClicked = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Dto.getDto().setmContext(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 初始化主界面
        getSupportFragmentManager().beginTransaction().replace(R.id.content_main, StudentFragment.newInstance(StudentFragment.TYPE_CLASS_ALL)).commit();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        getMenuInflater().inflate(R.menu.main, menu);
        SearchView searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // TODO: 2016/10/31 完成搜索提交功能
                Toast.makeText(MainActivity.this, "提交成功", Toast.LENGTH_SHORT).show();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // TODO: 2016/10/31 完成搜索文本改变的功能
                Toast.makeText(MainActivity.this, "文本改变", Toast.LENGTH_SHORT).show();
                return false;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_search) {
            Toast.makeText(this, "搜索", Toast.LENGTH_SHORT).show();
        }else if(id == R.id.action_add_student){
            // TODO: 2016/11/3 添加学生
            Intent intent =new Intent(AddStudentActivity.ACTION_NAME);
            startActivity(intent);
            Toast.makeText(this, "添加学生 ", Toast.LENGTH_SHORT).show();
        }
        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        int type = -1;
        switch (id) {
            case R.id.nav_class_all:
                type = StudentFragment.TYPE_CLASS_ALL;
                break;
            case R.id.nav_class_one:
                type = StudentFragment.TYPE_CLASS_ONE;
                break;
            case R.id.nav_class_two:
                type = StudentFragment.TYPE_CLASS_TWO;
                break;
            case R.id.nav_class_three:
                type = StudentFragment.TYPE_CLASS_THREE;
                break;
            case R.id.nav_class_four:
                type = StudentFragment.TYPE_CLASS_FOUR;
                break;
        }
        if (type != -1) {
            getSupportFragmentManager().beginTransaction().replace(R.id.content_main, StudentFragment.newInstance(type)).commit();
        }
        if (id == R.id.nav_check) {
            Intent intent = new Intent(CheckActivity.ACTION);
            startActivity(intent);
        } else if (id == R.id.nav_setting) {
            showSettingDialog();
        } else if (id == R.id.nav_about) {
            Intent intent = new Intent(AboutActivity.ACTION);
            startActivity(intent);
        }
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void showSettingDialog() {
        // 获取数据库中最大的课程次
        final int maxNum = AttendenceContent.getMaxRoolCallNumberInDB();
        View view = LayoutInflater.from(this).inflate(R.layout.setting_dialog, null);
        final EditText editText = (EditText) view.findViewById(R.id.etNumber);
        new AlertDialog.Builder(this).setTitle("重置最大的课程次数 [当前: "+Dto.MAX_ROOL_CALLNUMER+"]").setView(view).setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                String input = editText.getText().toString().trim();
                if (!input.isEmpty()) {
                    int num = Integer.parseInt(input);
                    if (num >= maxNum) {
                        Dto.MAX_ROOL_CALLNUMER = num;
                        Toast.makeText(MainActivity.this, "重置成功!\n您修改课程次为: " + num, Toast.LENGTH_SHORT).show();
                        // 刷新考勤内容
                        AttendenceContent.refreshData();
                    } else {
                        Toast.makeText(MainActivity.this, "重置失败!\n你输入的课程次"+num+"小于数据库中最大的课程次"+maxNum, Toast.LENGTH_SHORT).show();
                    }
                    dialogInterface.dismiss();
                }
            }
        }).setNegativeButton("取消", null).create().show();

    }

    @Override
    public void onListFragmentInteraction(Student item) {
        if (firstClicked) {
            Toast.makeText(this, "点击头像可换头像\n滑动考勤可查看所有考勤", Toast.LENGTH_SHORT).show();
            firstClicked = false;
        }
        Intent intent = new Intent(StudentInfoActivity.ACTION);
        intent.putExtra(StudentInfoActivity.ARG_STR, item.getSno());
        startActivity(intent);
    }


    @Override
    protected void onRestart() {
        super.onRestart();
        StudentContent.refreshData();
    }

}
