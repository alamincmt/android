package com.example.studentmanagementsystem;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmanagementsystem.adapter.StudentAdapter;
import com.example.studentmanagementsystem.model.Departments;
import com.example.studentmanagementsystem.model.Students;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final static String TAG = MainActivity.class.getSimpleName();

    private TextView btn_add_dept, btn_add_student;
    private RecyclerView rcv_student_list;
    private LinearLayoutManager linearLayoutManager;

    private Context context;
    private StudentAdapter studentAdapter;
    DatabaseReference studentReference = null;
    DatabaseReference deptReference = null;
    private String deptName = "";

    private List<Students> studentsList = new ArrayList<>();
    private List<Departments> departmentsList = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.context = MainActivity.this;
        setContentView(R.layout.activity_main);

        initViews();

        btn_add_student.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNewStudentOrUpdateActivity.class));
            }
        });

        btn_add_dept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AddNewDeptActivity.class));
            }
        });

        //getting the reference of depts node
        deptReference = FirebaseDatabase.getInstance().getReference("departments");

        //getting the reference of students node
        studentReference = FirebaseDatabase.getInstance().getReference("students");


        linearLayoutManager = new LinearLayoutManager(context);
        rcv_student_list.setLayoutManager(linearLayoutManager);
        rcv_student_list.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration divider = new DividerItemDecoration(rcv_student_list.getContext(), DividerItemDecoration.VERTICAL);
        divider.setDrawable(ContextCompat.getDrawable(getBaseContext(), R.drawable.line_divider));
        rcv_student_list.addItemDecoration(divider);


        // Getting data from departments reference to get the dept name.
        deptReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                departmentsList.clear();

                //iterating through all the nodes
                for (DataSnapshot deptSnapshot : dataSnapshot.getChildren()) {
                    //getting departments
                    Departments departments = deptSnapshot.getValue(Departments.class);
                    //adding students to the list
                    departmentsList.add(departments);
                }

                if(departmentsList.size()> 0){
                    // Getting data from students reference
                    studentReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            studentsList.clear();

                            //iterating through all the nodes
                            for (DataSnapshot studentSnapshot : dataSnapshot.getChildren()) {
                                //getting students
                                Students students = studentSnapshot.getValue(Students.class);
//                                String deptName = getDeptName(students.getStudentDepartment());
                                if(departmentsList.size() > 0){
                                    for(int i=0; i<departmentsList.size(); i++){
                                        Departments departments = departmentsList.get(i);
                                        if(departments.getFbDeptId().equals(students.getStudentDepartment())){
                                            deptName = departments.getDeptName();
                                            break;
                                        }else{
                                            deptName = students.getStudentDepartment();
                                        }
                                    }
                                }
                                students.setStudentDepartment(deptName);

                                //adding students to the list
                                studentsList.add(students);
                            }

                            if(studentsList.size() > 0){
                                studentAdapter = new StudentAdapter(context, studentsList, departmentsList);
                                rcv_student_list.setAdapter(studentAdapter);
                            }else{
                                Toast.makeText(MainActivity.this, "No data found!!!!", Toast.LENGTH_LONG).show();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(MainActivity.this, "Problem occured!!! ", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                // If cancelled handle your action here.
            }
        });
    }

    private String getDeptName(final String firebaseDeptId){
        // Extracting the dept name from departmentList
        if(departmentsList.size() > 0){
            for(int i=0; i<departmentsList.size(); i++){
                Departments departments = departmentsList.get(i);
                if(departments.getFbDeptId().equals(firebaseDeptId)){
                    return  departments.getDeptName();
                }
            }
        }
        return deptName;
    }

    private void initViews() {
        btn_add_dept = findViewById(R.id.btn_add_dept);
        btn_add_student = findViewById(R.id.btn_add_student);
        rcv_student_list = findViewById(R.id.rcv_student_list);
    }
}
