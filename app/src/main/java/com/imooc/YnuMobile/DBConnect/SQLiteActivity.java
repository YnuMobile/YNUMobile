package com.imooc.YnuMobile.DBConnect;

import android.app.Activity;
import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;
import com.imooc.YnuMobile.R;

public class SQLiteActivity extends Activity implements View.OnClickListener {

    Button createDatabase,updateDatabase,insert,update,query,delete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite);
        initView();
    }

    private void initView() {
        createDatabase= (Button) findViewById(R.id.createDatabase);
        updateDatabase= (Button) findViewById(R.id.updateDatabase);
        insert= (Button) findViewById(R.id.insert);
        update= (Button) findViewById(R.id.update);
        query= (Button) findViewById(R.id.query);
        delete= (Button) findViewById(R.id.delete);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.createDatabase:
                DatabaseHelper dbHelper1=new DatabaseHelper(SQLiteActivity.this,"myDb");
                SQLiteDatabase db1=dbHelper1.getReadableDatabase();
                break;
            case R.id.updateDatabase:
                DatabaseHelper dbHelper2=new DatabaseHelper(SQLiteActivity.this,"myDb",2);
                SQLiteDatabase db2=dbHelper2.getReadableDatabase();
        }
    }
}
