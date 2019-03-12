package com.example.studentmanagementsystem;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.studentmanagementsystem.model.Departments;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.Date;

public class AddNewDeptActivity extends AppCompatActivity {

    private EditText et_dept_id, et_deptt_name;
    private Button btn_save;

    DatabaseReference deptDBReference = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_department);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Add New Dept");

        initViews();

        //getting the reference of departments node
        deptDBReference = FirebaseDatabase.getInstance().getReference("departments");

        btn_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveDept();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        //attaching value event listener
        deptDBReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                et_dept_id.setText("");
                et_deptt_name.setText("");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    private void saveDept() {
        String deptId = et_dept_id.getText().toString().trim();
        String deptName = et_deptt_name.getText().toString().trim();

        if(!TextUtils.isEmpty(deptId) && !TextUtils.isEmpty(deptName)){
            //getting a unique id using push().getKey() method
            //it will create a unique id and we will use it as the Primary Key for our Dept
            String firebaseDeptID = deptDBReference.push().getKey();

            //creating an Department Object
            Departments departments = new Departments(firebaseDeptID, deptId, deptName, new Date());

            //Saving the Dept
            deptDBReference.child(firebaseDeptID).setValue(departments);
            Toast.makeText(AddNewDeptActivity.this, "Data saved successfully. ", Toast.LENGTH_LONG).show();

        }else{
            Toast.makeText(AddNewDeptActivity.this, "Please data first...", Toast.LENGTH_LONG).show();
        }
    }

    private void initViews() {
        et_dept_id = findViewById(R.id.et_dept_id);
        et_deptt_name = findViewById(R.id.et_dept_name);
        btn_save = findViewById(R.id.btn_save);
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
