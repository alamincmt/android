package com.example.studentmanagementsystem.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.AlertDialogLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.studentmanagementsystem.AddNewStudentOrUpdateActivity;
import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.model.Departments;
import com.example.studentmanagementsystem.model.Students;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.SuraViewHolder> {

    private Context context;
    private List<Students> studentsList;
    private List<Departments> departmentsList;
    private Students students;


    @NonNull
    @Override
    public SuraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_students, parent, false);
        return new SuraViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(final @NonNull SuraViewHolder holder, final int position) {
        students = studentsList.get(holder.getAdapterPosition());

        Log.d("StudentData: ", "Dept: "+ students.getStudentDepartment().toString() + " \n");
        System.out.println("\n");

        if(students.getStudentName().equals("")){
            holder.tvStudentName.setText("Student Name: " + "--");
        }else{
            holder.tvStudentName.setText("Student Name: " + students.getStudentName());
        }

        if(students.getStudentId().equals("")){
            holder.tvStudentID.setText("ID: " + "--");
        }else{
            holder.tvStudentID.setText("ID: " + students.getStudentId());
        }

        holder.tvStudentDept.setText( "Dept: " + "--");
        if(students.getStudentDepartment().equals("")){
            holder.tvStudentDept.setText( "Dept: " + "--");
        }else{
            holder.tvStudentDept.setText( "Dept: "+students.getStudentDepartment());
        }

        holder.lblList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                showUpdateDeleteDialog(studentsList.get(holder.getAdapterPosition()));
                notifyDataSetChanged();
                return true;
            }
        });
    }



    private void showUpdateDeleteDialog(final Students students) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Options - ");
        builder.setMessage("What do you want to do?");
        builder.setPositiveButton("Update", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(context, "Update selected. ", Toast.LENGTH_LONG).show();
                context.startActivity(new Intent(context, AddNewStudentOrUpdateActivity.class).putExtra("Update", true)
                .putExtra("firebaseStudentId", students.getFbStudentId())
                .putExtra("studentName", students.getStudentName())
                .putExtra("studentDept", students.getStudentDepartment())
                .putExtra("studentId", students.getStudentId()));
                builder.create().dismiss();
            }
        });

        builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                // Deleting student data from students reference.
                DatabaseReference studentReference = FirebaseDatabase.getInstance().getReference("students").child(students.getFbStudentId());
                studentReference.removeValue();
                Toast.makeText(context, "Selected item deleted successfully. ", Toast.LENGTH_LONG).show();

                builder.create().dismiss();
            }
        });

        builder.create().show();
    }

    @Override
    public int getItemCount() {
        return studentsList.size();
    }

    public class SuraViewHolder extends RecyclerView.ViewHolder{

        public LinearLayout lblList;
        public TextView tvStudentName, tvStudentID, tvStudentDept;

        public SuraViewHolder(View itemView) {
            super(itemView);
            lblList = itemView.findViewById(R.id.lblList);
            tvStudentName = itemView.findViewById(R.id.tvStudentName);
            tvStudentID = itemView.findViewById(R.id.tvStudentId);
            tvStudentDept = itemView.findViewById(R.id.tvDept);

        }
    }

    public StudentAdapter(Context context, List<Students> studentsList, List<Departments> departmentsList){
        this.context = context;
        this.studentsList = studentsList;
        this.departmentsList = departmentsList;
    }

}
