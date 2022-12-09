package com.asuni.assignment.adapters;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.asuni.assignment.R;
import com.asuni.assignment.db.entity.EmpModal;
import com.asuni.assignment.views.Home;


public class EmpRVAdapter extends ListAdapter<EmpModal, EmpRVAdapter.ViewHolder> {

    private OnItemClickListener listener;
    Home mainActivity;


    private static final DiffUtil.ItemCallback<EmpModal> DIFF_CALLBACK = new DiffUtil.ItemCallback<EmpModal>() {
        @Override
        public boolean areItemsTheSame(EmpModal oldItem, EmpModal newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(EmpModal oldItem, EmpModal newItem) {

            return
                    oldItem.getEmployee_name().equals(newItem.getEmployee_name()) &&
                            oldItem.getEmployee_age() == (newItem.getEmployee_age()  ) &&
                            oldItem.getEmployee_salary() == (newItem.getEmployee_salary()  );
        }
    };

    public EmpRVAdapter(Home mainActivity) {
        super(DIFF_CALLBACK);
        this.mainActivity=mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.lay_emp_data, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        EmpModal model = getCourseAt(position);


        try {

            holder.id.setText("id : "+ String.valueOf(model.getId()) );

            holder.name.setText( "name : "+ model.getEmployee_name());

            holder.salary.setText( "salary : "+ String.valueOf( model.getEmployee_salary()));

            holder.age.setText( "age : "+ String.valueOf( model.getEmployee_age() ) );

            if( model.getProfile_image().trim().equals("") || model.getProfile_image() != null )
                holder.image.setBackground( mainActivity.getResources().getDrawable(R.drawable.ic_person1) );
            else
                holder.image.setImageBitmap(getBitmap( model.getProfile_image().trim() ));


            holder.layout.setOnClickListener(new View.OnClickListener() {
                @RequiresApi(api = Build.VERSION_CODES.O)
                @Override
                public void onClick(View view) {

                    Toast.makeText(mainActivity,"Click Emp id : "+model.getId() ,Toast.LENGTH_LONG).show();

                }
            });


        } catch (Exception e) {
            System.out.println("create job view ex:" + e);
        }


    }

    public static Bitmap getBitmap(String string) {

        byte[] encodeByte = android.util.Base64.decode(string, android.util.Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);

    }


    public EmpModal getCourseAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public ImageView image;
        public TextView id;
        public TextView name;
        public TextView salary;
        public TextView age;
        LinearLayout layout;


        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.id = itemView.findViewById(R.id.id);
            this.name = itemView.findViewById(R.id.name);
            this.salary = itemView.findViewById(R.id.salary);
            this.age = itemView.findViewById(R.id.age);
            this.image = itemView.findViewById(R.id.image);
            this.layout = itemView.findViewById(R.id.layout);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int position = getAdapterPosition();
                    if (listener != null && position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(getItem(position));
                    }
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(EmpModal model);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}

