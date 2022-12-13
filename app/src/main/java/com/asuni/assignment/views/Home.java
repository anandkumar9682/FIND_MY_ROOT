package com.asuni.assignment.views;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;

import android.graphics.Color;
import android.os.Bundle;

import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asuni.assignment.R;

import com.asuni.assignment.adapters.LocRVAdapter;
import com.asuni.assignment.db.entity.LocModel;
import com.asuni.assignment.viewmodels.ViewModal;
import com.google.android.material.snackbar.Snackbar;

import java.util.List;

public class Home extends AppCompatActivity {

  public ViewModal viewmodal;

  LocRVAdapter adapter;

  LinearLayout loader;

  boolean isAscending=true;

  public static LocRVAdapter adapter1;


  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView( R.layout.home );
    super.onCreate(savedInstanceState);


    viewmodal = ViewModelProviders.of(this).get( ViewModal.class );

    Toolbar toolbar = findViewById( R.id.toolbar );
    loader = findViewById( R.id.loader );
    setSupportActionBar( toolbar ) ;

    ((TextView) findViewById(R.id.headerTitle) ).setText( getApplicationContext().getString (R.string.app_name ));

    findViewById(R.id.menu_btn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

      }
    });

    findViewById(R.id.sortListBTN).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        sortList();
      }
    });



    View.OnClickListener view = new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        changeLayout( null );
      }
    };

    findViewById(R.id.addPOI).setOnClickListener( view );
    findViewById(R.id.addPOI1).setOnClickListener( view );



    findViewById(R.id.getDirectionBTN).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        loader.setVisibility(View.VISIBLE);

        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            changeLayout1(  );

          }
        },0);

        loader.setVisibility(View.GONE);

      }
    });

    RecyclerView recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);

    adapter = new LocRVAdapter( this );
    adapter1 = adapter;

    recyclerView.setAdapter(adapter);

    if( adapter.getCurrentList().size() == 0 ){
      layout(false);
    }else{
      layout(true);
    }

    fetchList();

    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        viewmodal.delete(adapter.getLocAt( viewHolder.getAdapterPosition() ));
        Toast.makeText( getApplicationContext() , "Place deleted from RoomDB", Toast.LENGTH_SHORT).show();
      }
    }).attachToRecyclerView( recyclerView );

    adapter.setOnItemClickListener(new LocRVAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(LocModel model) {
        Toast.makeText(getApplicationContext(),"Click Location id : "+model.getId() ,Toast.LENGTH_LONG).show();
      }
    });

  }

  public void changeLayout( LocModel locModel ){
    Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
    intent.putExtra( "model" , locModel );
    startActivityForResult( intent , 1 );
  }

  @Override
  protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
    super.onActivityResult(requestCode, resultCode, data);

    loader.setVisibility(View.VISIBLE);
    new Handler().postDelayed(new Runnable() {
      @Override
      public void run() {
        fetchList();
        loader.setVisibility(View.GONE);
      }
    },2000);

  }

  public void changeLayout1(  ){
    Intent intent = new Intent(getApplicationContext(),MapsActivity.class);
    intent.putExtra( "flag" , true );
    startActivity( intent );
  }

  public void deletePopup(int i) {

    AlertDialog.Builder builder1 = new AlertDialog.Builder( Home.this );
    ViewGroup viewGroup = findViewById(android.R.id.content);

    View dialogView = LayoutInflater.from( Home.this ) .inflate(R.layout.delete_popup_layout, viewGroup, false);
    builder1.setView(dialogView);
    builder1.setCancelable(false);

    Button yes = dialogView.findViewById(R.id.yes);
    Button no = dialogView.findViewById(R.id.no);
    AlertDialog ad = builder1.create();

    yes.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {

        viewmodal.delete( adapter.getLocAt( i ) );

        loader.setVisibility(View.VISIBLE);
        new Handler().postDelayed(new Runnable() {
          @Override
          public void run() {
            fetchList();
            loader.setVisibility(View.GONE);
          }
        },2000);

        ad.dismiss();

      }
    });

    no.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
        ad.dismiss();
      }
    });

    ad.show();

  }

  public  void layout( boolean flag ){
    if( flag ){
      findViewById(R.id.listLayout).setVisibility(View.VISIBLE);
      findViewById(R.id.messageLayout).setVisibility(View.GONE);
    }else{
      findViewById(R.id.messageLayout).setVisibility(View.VISIBLE);
      findViewById(R.id.listLayout).setVisibility(View.GONE);
    }
  }

  public  void fetchList(){

    if( isAscending ){

      viewmodal.getAllLocsAsc().observe(this, new Observer<List<LocModel>>() {
        @Override
        public void onChanged(List<LocModel> models) {

          if( models.size() > 1 )
            findViewById(R.id.getDirectionBTN).setVisibility(View.VISIBLE);
          else
            findViewById(R.id.getDirectionBTN).setVisibility(View.GONE);

          adapter.submitList(models);

          if( adapter.getItemCount() == 0 ){
            layout(false);
          }else{
            layout(true);
          }

        }
      });

    }else{

      viewmodal.getAllLocsDesc().observe(this, new Observer<List<LocModel>>() {
        @Override
        public void onChanged(List<LocModel> models) {

          if( models.size() > 1 )
            findViewById(R.id.getDirectionBTN).setVisibility(View.VISIBLE);
          else
            findViewById(R.id.getDirectionBTN).setVisibility(View.GONE);

          adapter.submitList(models);

          if( adapter.getItemCount() == 0 ){
            layout(false);
          }else{
            layout(true);
          }

        }
      });

    }

  }

  @SuppressLint("MissingInflatedId")
  public void sortList() {

    Snackbar snackbar = Snackbar.make(  findViewById(android.R.id.content) , "", Snackbar.LENGTH_INDEFINITE);

    View customSnackView = getLayoutInflater().inflate(R.layout.sorting_list_layout, null);

    snackbar.getView().setBackgroundColor(Color.TRANSPARENT);

    Snackbar.SnackbarLayout snackbarLayout = (Snackbar.SnackbarLayout) snackbar.getView();

    snackbarLayout.setPadding(0, 0, 0, 0);

    customSnackView.findViewById(R.id.closePopup).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        snackbar.dismiss();
      }
    });


    ((RadioGroup)customSnackView.findViewById(R.id.sortRadioBtn) ).setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener()
    {
      @Override
      public void onCheckedChanged(RadioGroup group, int checkedId) {

        if( checkedId == R.id.ascending ){
          isAscending=true;
          fetchList();
        }else{
          isAscending=false;
          fetchList();
        }

      }
    });

    snackbarLayout.addView(customSnackView, 0);

    snackbar.show();

  }

  public void update( int position ) {
    changeLayout( adapter.getLocAt( position ) );
  }

}
