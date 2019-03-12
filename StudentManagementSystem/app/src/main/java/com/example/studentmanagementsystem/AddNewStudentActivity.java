package com.example.studentmanagementsystem;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentmanagementsystem.model.Departments;
import com.example.studentmanagementsystem.model.Students;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Date;

public class AddNewStudentActivity extends AppCompatActivity {

    private EditText et_student_id, et_student_name;
    private Button btn_save;
    private Spinner spinner_departments;

    DatabaseReference studentReference = null;

    ArrayList<Departments> deptList = new ArrayList<>();
    ArrayList<String> deptNameList = new ArrayList<>();
    private String firebaseDeptId = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_student);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add New Student");

        initViews();

        //getting the reference of students node
        studentReference = FirebaseDatabase.getInstance().getReference("students");

        DatabaseReference deptReference = FirebaseDatabase.getInstance().getReference("departments");
        deptReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                deptList.clear();
                deptNameList.clear();

                //iterating through all the nodes
                for (DataSnapshot deptSnapshot : dataSnapshot.getChildren()) {
                    //getting departments
                    Departments departments = deptSnapshot.getValue(Departments.class);
                    //adding department to the list
                    deptList.add(departments);
                }

                if(deptList.size() > 0){
                    for(int i=0; i<deptList.size(); i++){
                        deptNameList.add(deptList.get(i).getDeptName());
                    }
                }

                //creating adapter
                ArrayAdapter<String> arrayAdapter = new ArrayAdapter<>(AddNewStudentActivity.this, android.R.layout.simple_list_item_1, deptNameList);
                spinner_departments.setAdapter(arrayAdapter);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveStudent();
            }
        });

        spinner_departments.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                firebaseDeptId = deptList.get(position).getFbDeptId();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void saveStudent() {
        String studentId = et_student_id.getText().toString().trim();
        String studentName = et_student_name.getText().toString().trim();

        if(!TextUtils.isEmpty(studentId) && !TextUtils.isEmpty(studentName)){
            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Student
            String firebaseStudentID = studentReference.push().getKey();

            //creating an Student Object
            Students students = new Students(firebaseStudentID, studentId, studentName, firebaseDeptId);

            //Saving the Dept
            studentReference.child(firebaseStudentID).setValue(students);
            Toast.makeText(AddNewStudentActivity.this, "Data saved successfully. ", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(AddNewStudentActivity.this, "Please data first...", Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        et_student_id = findViewById(R.id.et_student_id);
        et_student_name = findViewById(R.id.et_student_name);
        btn_save = findViewById(R.id.btn_save);
        spinner_departments = findViewById(R.id.spinner_departments);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //attaching value event listener
        studentReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                et_student_id.setText("");
                et_student_name.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        if(item.getItemId() == android.R.id.home){
            finish();
        }

        return super.onOptionsItemSelected(item);
    }
}
