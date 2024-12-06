package com.tnqr.connectcserver.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.tnqr.connectcserver.databinding.ActivityLibraryBinding;

import java.io.IOException;
import java.util.ArrayList;

public class LibraryActivity extends AppCompatActivity {
    ActivityLibraryBinding binding;
    ArrayList<String> tables = new ArrayList<>();
    ArrayList<Button> reserveButtons = new ArrayList<>();
    ArrayList<Button> releaseButtons = new ArrayList<>();
    ArrayList<Integer> reserveButtonsIds = new ArrayList<>();
    ArrayList<Integer> releaseButtonIds = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);
       binding = ActivityLibraryBinding.inflate(getLayoutInflater());
       View view = binding.getRoot();

        editButtons();
        Intent intent =getIntent();
        tables= intent.getStringArrayListExtra("table_status");
        updateTables(tables);

       setContentView(view);
    }

    public void reserveButton(View view) throws IOException {
        int clickedButtonId = view.getId();
        for (int i=0; i<reserveButtonsIds.size(); i++){
            if (clickedButtonId == reserveButtonsIds.get(i)){
                System.out.println("tıklanılan Buton ıd"+i);
                String command = "RESERVE "+i;
                MainActivity mainActivity = MainActivity.getInstance();
                mainActivity.sendCommandToServer(command);
                finish();

            }
        }



    }
    public void leaveButton(View view){
        int clickedButtonId = view.getId(); //tıklanılan buton id sini bulur
        for (int i=0; i<releaseButtons.size(); i++){
            if (clickedButtonId == releaseButtonIds.get(i)){
                System.out.println("tıklanılan Buton ıd"+i);
                String command = "LEAVE "+i;
                MainActivity mainActivity = MainActivity.getInstance();
                mainActivity.sendCommandToServer(command);
                finish();

            }
        }

    }
    public void updateTables(ArrayList tables){



       for(int i = 1; i< reserveButtons.size(); i++){

           if(tables.get(i).equals("1")){
               reserveButtons.get(i).setEnabled(false);
               releaseButtons.get(i).setEnabled(true);
           }
           else{
               reserveButtons.get(i).setEnabled(true);
               releaseButtons.get(i).setEnabled(false);
           }


    }


    }
    public void editButtons(){
        reserveButtons.add(0,new Button(getApplicationContext()));
        reserveButtons.add(1,binding.reserveButton1);
        reserveButtons.add(2,binding.reserveButton2);
        reserveButtons.add(3,binding.reserveButton3);
        reserveButtons.add(4,binding.reserveButton4);
        reserveButtons.add(5,binding.reserveButton5);
        reserveButtons.add(6,binding.reserveButton6);
        reserveButtons.add(7,binding.reserveButton7);
        reserveButtons.add(8,binding.reserveButton8);
        reserveButtons.add(9,binding.reserveButton9);
        reserveButtons.add(10,binding.reserveButton10);
        reserveButtons.add(11,binding.reserveButton11);
        reserveButtons.add(12,binding.reserveButton12);
        reserveButtons.add(13,binding.reserveButton13);
        reserveButtons.add(14,binding.reserveButton14);
        reserveButtons.add(15,binding.reserveButton15);
        reserveButtons.add(16,binding.reserveButton16);
        reserveButtons.add(17,binding.reserveButton17);
        reserveButtons.add(18,binding.reserveButton18);
        reserveButtons.add(19,binding.reserveButton19);
        reserveButtons.add(20,binding.reserveButton20);

        for(Button buttonId:reserveButtons){
            reserveButtonsIds.add(buttonId.getId());
        }

        releaseButtons.add(0,new Button(getApplicationContext()));
        releaseButtons.add(1,binding.releaseButton1);
        releaseButtons.add(2,binding.releaseButton2);
        releaseButtons.add(3,binding.releaseButton3);
        releaseButtons.add(4,binding.releaseButton4);
        releaseButtons.add(5,binding.releaseButton5);
        releaseButtons.add(6,binding.releaseButton6);
        releaseButtons.add(7,binding.releaseButton7);
        releaseButtons.add(8,binding.releaseButton8);
        releaseButtons.add(9,binding.releaseButton9);
        releaseButtons.add(10,binding.releaseButton10);
        releaseButtons.add(11,binding.releaseButton11);
        releaseButtons.add(12,binding.releaseButton12);
        releaseButtons.add(13,binding.releaseButton13);
        releaseButtons.add(14,binding.releaseButton14);
        releaseButtons.add(15,binding.releaseButton15);
        releaseButtons.add(16,binding.releaseButton16);
        releaseButtons.add(17,binding.releaseButton17);
        releaseButtons.add(18,binding.releaseButton18);
        releaseButtons.add(19,binding.releaseButton19);
        releaseButtons.add(20,binding.releaseButton20);
        for(Button buttonId:releaseButtons){
            releaseButtonIds.add(buttonId.getId());
        }




    }

}