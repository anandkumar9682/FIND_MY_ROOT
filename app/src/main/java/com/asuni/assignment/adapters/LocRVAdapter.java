package com.asuni.assignment.adapters;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
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
import com.asuni.assignment.db.entity.LocModel;
import com.asuni.assignment.views.Home;


public class LocRVAdapter extends ListAdapter<LocModel, LocRVAdapter.ViewHolder> {

    private OnItemClickListener listener;
    Home mainActivity;


    private static final DiffUtil.ItemCallback<LocModel> DIFF_CALLBACK = new DiffUtil.ItemCallback<LocModel>() {
        @Override
        public boolean areItemsTheSame(LocModel oldItem, LocModel newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(LocModel oldItem, LocModel newItem) {

            return
                    oldItem.getName().equals(newItem.getName()) &&
                            oldItem.getAddress().equals( newItem.getAddress() ) &&
                            oldItem.getLog().equals (newItem.getLog()  ) &&
                            oldItem.getLat().equals (newItem.getLat()  );
        }
    };

    public LocRVAdapter(Home mainActivity) {
        super(DIFF_CALLBACK);
        this.mainActivity=mainActivity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View item = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_data_show_list_layout, parent, false);
        return new ViewHolder(item);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {

        LocModel model = getLocAt(position);

        try {

            holder.name.setText( model.getName() );

            if( model.getPrioriry() <= 0 ) {

                holder.prioriry.setText("Primary");
                holder.prioriry.setVisibility(View.VISIBLE);

            } else {

                holder.prioriry.setVisibility(View.GONE);

            }


            holder.address.setText( String.valueOf( model.getAddress() ) );

            holder.distance.setText( "Distance : "+model.getDistance()+"-KM" );

            holder.editBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.update( position );
                }
            });

            holder.deleteBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.deletePopup( position );
                }
            });


        } catch (Exception e) {
            System.out.println("create job view ex:" + e);
        }

    }


    public LocModel getLocAt(int position) {
        return getItem(position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public Button editBTN;
        public Button deleteBTN;
        public TextView name;
        public TextView prioriry;
        public TextView address;
        public TextView distance;
        LinearLayout layout;

        ViewHolder(@NonNull View itemView) {
            super(itemView);

            this.name = itemView.findViewById(R.id.name);
            this.prioriry = itemView.findViewById(R.id.priority);
            this.address = itemView.findViewById(R.id.address);
            this.distance= itemView.findViewById(R.id.distance);
            this.editBTN= itemView.findViewById(R.id.editBTN);
            this.deleteBTN= itemView.findViewById(R.id.deleteBTN);
            this.layout = itemView.findViewById(R.id.layout);


        }
    }

    public interface OnItemClickListener {

        void onItemClick(LocModel model);

    }
    public void setOnItemClickListener(OnItemClickListener listener) {

        this.listener = listener;

    }


}

