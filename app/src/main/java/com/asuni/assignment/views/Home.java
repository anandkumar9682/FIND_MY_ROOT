package com.asuni.assignment.views;

import android.content.Intent;

import android.net.Uri;
import android.os.Bundle;

import android.view.MenuItem;
import android.view.View;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import androidx.appcompat.app.ActionBarDrawerToggle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.asuni.assignment.R;

import com.asuni.assignment.adapters.EmpRVAdapter;
import com.asuni.assignment.databinding.HomeBinding;
import com.asuni.assignment.db.entity.EmpModal;
import com.asuni.assignment.viewmodels.ViewModal;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

public class Home extends AppCompatActivity {

  private ViewModal viewmodal;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    setContentView( R.layout.home );
    super.onCreate(savedInstanceState);

    viewmodal = ViewModelProviders.of(this).get( ViewModal.class );

    DrawerLayout drawerLayout = findViewById( R.id.drawer );
    ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout , R.string.nav_open , R.string.nav_close );

    drawerLayout.addDrawerListener( actionBarDrawerToggle ) ;
    actionBarDrawerToggle.syncState() ;

    Toolbar toolbar = findViewById( R.id.toolbar );
    setSupportActionBar( toolbar ) ;

    drawerLayout.close();

    findViewById(R.id.menu_btn).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        drawerLayout.open();
      }
    });

    findViewById(R.id.ref).setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {

        viewmodal.retroFetch();

      }
    });


    NavigationView navigationView=findViewById(R.id.navigationView);
    navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        drawerLayout.close();

        switch ( item.getItemId() ) {

          case R.id.contact_us: {
            startActivity(new Intent(getApplicationContext(),ContactUS.class));
            return true;
          }

          case R.id.show_us_love: {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.playstoreurl))));
            return true;
          }

        }
        return true;
      }
    });



    RecyclerView recyclerView = findViewById(R.id.recyclerView);
    recyclerView.setLayoutManager(new LinearLayoutManager(this));
    recyclerView.setHasFixedSize(true);

    final EmpRVAdapter adapter = new EmpRVAdapter( this );
    recyclerView.setAdapter(adapter);

    viewmodal.getAllCourses().observe(this, new Observer<List<EmpModal>>() {
      @Override
      public void onChanged(List<EmpModal> models) {

        adapter.submitList(models);

      }
    });

    new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
      @Override
      public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        return false;
      }

      @Override
      public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        viewmodal.delete(adapter.getCourseAt(viewHolder.getAdapterPosition()));
        Toast.makeText( getApplicationContext() , "Employee deleted from RoomDB", Toast.LENGTH_SHORT).show();
      }
    }).attachToRecyclerView( recyclerView );

    adapter.setOnItemClickListener(new EmpRVAdapter.OnItemClickListener() {
      @Override
      public void onItemClick(EmpModal model) {
        Toast.makeText(getApplicationContext(),"Click Emp id : "+model.getId() ,Toast.LENGTH_LONG).show();
      }
    });

  }


  @Override
  protected void onStart() {
    super.onStart();
  }




}
