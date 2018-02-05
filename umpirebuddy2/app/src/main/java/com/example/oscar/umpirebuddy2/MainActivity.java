package com.example.oscar.umpirebuddy2;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.Preference;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;
import android.preference.PreferenceManager;

public class MainActivity extends AppCompatActivity {



    //counters
    int strike_cnt = 0;
    int ball_cnt = 0;
    int strike_outs = 0;

    public void clear(){
        //clear all values
        strike_cnt =0;
        ball_cnt = 0;
        //reset text to 0
        final TextView scnt = findViewById(R.id.strike_count);
        final TextView bcnt = findViewById(R.id.ball_count);
        scnt.setText("0");
        bcnt.setText("0");
    }

    public void open(CharSequence Value){
        //create alert dialog
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        //set message to the value given
        alertDialogBuilder.setMessage(Value);
        //set button message to ok
        //when clicked run clear() to reset the count
        alertDialogBuilder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        SharedPreferences shared = getSharedPreferences("out", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = shared.edit();

                        editor.putInt("out", strike_outs);
                        editor.apply();
                        clear();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create(); //create box
        alertDialog.show();//show it
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SharedPreferences shared = getSharedPreferences("out", Context.MODE_PRIVATE);
        final TextView outs = findViewById(R.id.outs);
        strike_outs = shared.getInt("out", 0);
        outs.setText(String.valueOf(strike_outs));


        //buttons and text view
        final TextView scnt = findViewById(R.id.strike_count);

        final Button strike = findViewById(R.id.strike_button);
        //listen for button
        strike.setOnClickListener(new View.OnClickListener() {
            //on click check for the count and add to it
            //or display message
            public void onClick(View v) {
                strike_cnt += 1;
                if(strike_cnt < 3){
                    scnt.setText(String.valueOf(strike_cnt));
                }
                else{
                    strike_outs++;
                    scnt.setText(String.valueOf(strike_cnt));
                    outs.setText(String.valueOf(strike_outs));
                    open("Out!");


                }

            }
        });

        //buttons and text view
        final TextView bcnt = findViewById(R.id.ball_count);
        final Button ball = findViewById(R.id.ball_button);
        ball.setOnClickListener(new View.OnClickListener() {
            //on click check for the count and add to it
            //or display message
            public void onClick(View v) {
                ball_cnt += 1;
                if(ball_cnt < 4){
                    bcnt.setText(String.valueOf(ball_cnt));
                }
                else{
                    bcnt.setText(String.valueOf(ball_cnt));
                    open("Walk!");

                }

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.about) {
            Intent intent = new Intent(this, about.class);
            startActivity(intent);

            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
