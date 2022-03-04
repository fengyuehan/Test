package com.example.jetpack.room;

import android.content.Context;
import android.content.DialogInterface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.jetpack.MainActivity;
import com.example.jetpack.R;

import java.util.ArrayList;
import java.util.List;

public class RoomActivity extends AppCompatActivity {
    private Button button;
    private RecyclerView recyclerView;
    private Context context;
    private static StudentAdapter adapter;
    private static List<Student> data;
    private static MyDatabase myDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_room);
        context = this;
        initView(context);
    }

    private void initView(final Context context) {
        data = new ArrayList<>();
        myDatabase = MyDatabase.getInstance(this);
        button = findViewById(R.id.btn_insert_student);
        recyclerView = findViewById(R.id.rv);
        data.clear();
        new Thread(new Runnable() {
            @Override
            public void run() {
                data.addAll(myDatabase.studentDao().getStudentList());
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        adapter.notifyDataSetChanged();
                    }
                });
            }
        }).start();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDataDialog(context);
            }
        });
        initRecyclerView();
    }

    private void initRecyclerView() {
        adapter = new StudentAdapter(data);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                Student student = (Student) adapter.getData().get(position);
                updateOrDeleteDialog(student);
            }
        });
    }

    private void updateOrDeleteDialog(final Student student) {
        String[] options = new String[]{"更新","删除"};
        new AlertDialog.Builder(this).setTitle("")
                .setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                updataStudentDialog(student);
                                break;
                            case 1:
                                new DeleteStudentTask(student).execute();
                                break;
                        }
                    }
                }).show();
    }

    private void updataStudentDialog(final Student student) {
        if (student == null) {
            return;
        }
        View customView = this.getLayoutInflater().inflate(R.layout.dialog_layout_student, null);
        final EditText etName = customView.findViewById(R.id.etName);
        final EditText etAge = customView.findViewById(R.id.etAge);
        etName.setText(student.name);
        etAge.setText(student.age);
        final AlertDialog.Builder builder = new AlertDialog.Builder(RoomActivity.this);
        AlertDialog dialog = builder.create();
        dialog.setTitle("Update Student");
        dialog.setButton(DialogInterface.BUTTON_POSITIVE, "OK", new DialogInterface.OnClickListener()
        {

            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                if (TextUtils.isEmpty(etName.getText().toString()) || TextUtils.isEmpty(etAge.getText().toString())) {
                    Toast.makeText(RoomActivity.this, "输入不能为空", Toast.LENGTH_SHORT).show();
                } else {
                    new UpdateStudentTask(student.id, etName.getText().toString(), etAge.getText().toString()).execute();
                }
            }
        });
        dialog.setButton(DialogInterface.BUTTON_NEGATIVE, "CANCEL", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialog.setView(customView);
        dialog.show();
    }

    private void addDataDialog(final Context context) {
        View view = getLayoutInflater().inflate(R.layout.dialog_layout_student,null);
        final EditText etName = view.findViewById(R.id.etName);
        final EditText etAge = view.findViewById(R.id.etAge);
        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle("Add Student");
        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (TextUtils.isEmpty(etName.getText().toString().trim()) || TextUtils.isEmpty(etAge.getText().toString().trim())){
                    Toast.makeText(context,"输入不能为空",Toast.LENGTH_LONG).show();
                }else {
                    /**
                     * 数据库查询插入都需要时间在这采用AsyncTask操作，也可以开一个子线程
                     */
                    new InsertStudentTask(etName.getText().toString().trim(),etAge.getText().toString().trim()).execute();
                }
            }
        });
        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.dismiss();
            }
        });
        alertDialog.setView(view);
        alertDialog.show();

    }

    private static class InsertStudentTask extends AsyncTask<Void,Void,Void>{

        String name;
        String age;

        public InsertStudentTask(String name, String age) {
            this.name = name;
            this.age = age;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDatabase.studentDao().insertStudent(new Student(name,age));
            data.clear();
            data.addAll(myDatabase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }

    private class UpdateStudentTask extends AsyncTask<Void,Void,Void> {
        private int id;
        private String name;
        private String age;

        public UpdateStudentTask(int id, String name, String age) {
            this.id = id;
            this.name = name;
            this.age = age;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            myDatabase.studentDao().updataStudent(new Student(id,name,age));
            data.clear();
            data.addAll(myDatabase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            adapter.notifyDataSetChanged();
        }
    }

    private class DeleteStudentTask extends AsyncTask<Void, Void, Void> {
        Student student;

        public DeleteStudentTask(Student student) {
            this.student = student;
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            myDatabase.studentDao().deleteStudent(student);
            data.clear();
            data.addAll(myDatabase.studentDao().getStudentList());
            return null;
        }

        @Override
        protected void onPostExecute(Void result)
        {
            super.onPostExecute(result);
            adapter.notifyDataSetChanged();
        }
    }
}
