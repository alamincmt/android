package com.example.studentmanagementsystem.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.studentmanagementsystem.R;
import com.example.studentmanagementsystem.model.Students;

import java.util.List;

public class StudentAdapter extends RecyclerView.Adapter<StudentAdapter.SuraViewHolder> {

    private Context context;
    private List<Students> studentsList;
    private Students students;


    @NonNull
    @Override
    public SuraViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_students, parent, false);
        return new SuraViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull SuraViewHolder holder, final int position) {
        students = studentsList.get(position);

        holder.tvStudentName.setText("Student Name: " + students.getStudentName());
        holder.tvStudentID.setText("ID: " + students.getStudentId());
        holder.tvStudentDept.setText( "Dept: "+students.getStudentDepartment());

        holder.lblList.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
//                showBookmarkDeleteConfirmationDialog("আপনি কি এই বুকমার্কটি মুছে ফেলতে চান?", position, R.style.dialog_animation);
//                notifyDataSetChanged();
//                notifyItemRemoved(position);
                return true;
            }
        });
    }



    private void showBookmarkDeleteConfirmationDialog(String message, int position, int animation) {
        /*Dialog dialog = new Dialog(context);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.dialog_hadis_delete_confrimation, null, false);

        Button btn_cancel, btn_exit;
        btn_cancel = view.findViewById(R.id.btn_cancel);
        btn_exit = view.findViewById(R.id.btn_exit);
        TextView tvTitle = view.findViewById(R.id.tv_dialog_title);
        tvTitle.setText(message);

        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(dbOperations.removeBookmark(studentsList.get(position).getId())){
                    utils.showToastLong("বুকমার্ক সফলভাবে মুছে ফেলা হয়েছে।");
                    studentsList.remove(position);
                    notifyDataSetChanged();
                    notifyItemChanged(position);
                }
                dialog.dismiss();
            }
        });

        dialog.setContentView(view);
        final Window window = dialog.getWindow();
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(android.graphics.Color.TRANSPARENT));
        window.setGravity(Gravity.CENTER);
        window.getAttributes().windowAnimations = animation;
        dialog.show();*/
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

    public StudentAdapter(Context context, List<Students> studentsList){
        this.context = context;
        this.studentsList = studentsList;
    }

}
