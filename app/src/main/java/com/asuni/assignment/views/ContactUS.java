package com.asuni.assignment.views;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.os.Bundle;

import com.asuni.assignment.R;
import com.asuni.assignment.databinding.ContactUsBinding;
import com.asuni.assignment.viewmodels.ContactUsVM;

public class ContactUS extends AppCompatActivity {

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);

    ContactUsBinding actContactUsBinding = DataBindingUtil.setContentView( this , R.layout.contact_us);
    actContactUsBinding.setViewModel( new ContactUsVM(this) );
  }



}
