package com.example.fragments;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class MainActivity extends FragmentActivity implements Fragment1.OnButtonClickListener {

    //lab 03
    private int[] frames;
    private boolean hidden;

    //lab 02
    //private FragmentManager fragmentManager;
    //private Fragment fragment1, fragment2, fragment3, fragment4;

    //lab 01
    public static final String EXTRA_MESSAGE = "Hello there! :)";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //lab03
        if(savedInstanceState==null){
            frames=new int[]{R.id.Frame1,R.id.Frame2,R.id.Frame3,R.id.Frame4};
            hidden=false;

            Fragment[] fragments =new Fragment[]{new Fragment1(),new Fragment2(),new Fragment3(), new Fragment4()};
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction transaction = fragmentManager.beginTransaction()
                    .setCustomAnimations(
                            R.anim.fade_in,
                            R.anim.fade_out
                    );
            for (int i=0;i<4;i++){
                transaction.add(frames[i],fragments[i]);
            }
            transaction.addToBackStack(null);
            transaction.commit();

        }else{
            frames=savedInstanceState.getIntArray("FRAMES");
            hidden=savedInstanceState.getBoolean("HIDDEN");
        }

        //lab 02
        /*fragment1 = new Fragment1();
        fragment2 = new Fragment2();
        fragment3 = new Fragment3();
        fragment4 = new Fragment4();
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.Frame1, fragment1);
        transaction.add(R.id.Frame2, fragment2);
        transaction.add(R.id.Frame3, fragment3);
        transaction.add(R.id.Frame4, fragment4);
        transaction.addToBackStack(null);
        transaction.commit();*/
    }

    //lab 03
    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putIntArray("FRAMES",frames);
        outState.putBoolean("HIDDEN",hidden);
    }

    @Override
    public void onButtonClickShuffle(){
        //Toast.makeText(getApplicationContext(), "Shuffle", Toast.LENGTH_SHORT).show();
        List<Integer> list= new ArrayList<>(Arrays.asList(frames[0], frames[1], frames[2], frames[3]));
        Collections.shuffle(list);

        for (int i=0;i<4;i++) frames[i] = list.get(i);

        newFragments();
    }
    @Override
    public void onButtonClickClockwise(){
        //Toast.makeText(getApplicationContext(), "Clockwise", Toast.LENGTH_SHORT).show();
        int t = frames[0];
        frames[0] = frames[1];
        frames[1] = frames[2];
        frames[2] = frames[3];
        frames[3] = t;

        newFragments();
    }
    @Override
    public void onButtonClickHide(){
        //Toast.makeText(getApplicationContext(), "Hide", Toast.LENGTH_SHORT).show();
        if(hidden) return;

        FragmentManager fragmentManager = getSupportFragmentManager();

        for(Fragment f:fragmentManager.getFragments()){
            if(f instanceof  Fragment1) continue;
            FragmentTransaction transaction=fragmentManager.beginTransaction();
            transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
            transaction.hide(f);
            transaction.addToBackStack(null);
            transaction.commit();
        }

        hidden=true;
    }
    @Override
    public void onButtonClickRestore(){
        //Toast.makeText(getApplicationContext(), "Restore", Toast.LENGTH_SHORT).show();
        if(!hidden) return;
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction=fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        for(Fragment f:fragmentManager.getFragments()){
            if(f instanceof  Fragment1) continue;
            transaction.show(f);
        }
        transaction.addToBackStack(null);
        transaction.commit();

        hidden=false;
    }
    @Override
    public void onAttachFragment(@NonNull Fragment fragment) {
        super.onAttachFragment(fragment);

        if(fragment instanceof Fragment1) {
            ((Fragment1) fragment).setOnButtonClickListener(this);
        }
    }

    private void newFragments() {
        Fragment[] newFragments = new Fragment[]{new Fragment1(),new Fragment2(),new Fragment3(),new Fragment4()};
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.setCustomAnimations(R.anim.fade_in,R.anim.fade_out);
        for(int i=0;i<4;i++) {
            transaction.replace(frames[i], newFragments[i]);
            if (hidden && !(newFragments[i] instanceof Fragment1))
                transaction.hide(newFragments[i]);
        }
        transaction.addToBackStack(null);
        transaction.commit();
    }

    /*
    //lab 01
    public void sendMessage(View view) {
        Intent intent = new Intent(this, DisplayMessageActivity.class);
        EditText editText = (EditText) findViewById(R.id.editTextTextPersonName);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);
    }*/
}

//Laboratorium adres:
//https://tomasznowicki.gitbook.io/pam/