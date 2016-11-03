package cn.edu.cqjtu.registerlistdemo;

import android.app.SearchManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class SearchActivity extends AppCompatActivity {

    public static final String ACTION = "cn.edu.cqjtu.registerlistdemo.intent.action.SearchActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        String searchContent = getIntent().getStringExtra(SearchManager.QUERY);
        System.out.println("content:"+searchContent);
    }
}
