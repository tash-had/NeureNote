package neurenote.primarycode.com.neurenote;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.analytics.tracking.android.EasyTracker;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends Activity {

    EditText et1;
    EditText et2;
    String pccheck;
    String pin;
    String pinv;
    String note;
    SharedPreferences ftsp;
    SharedPreferences.Editor spedit;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button b1 = (Button)findViewById(R.id.showb);
        Button b2 = (Button)findViewById(R.id.newb);
        Button b3 = (Button)findViewById(R.id.editbutton);
        tf = Typeface.createFromAsset(getAssets(),"fonts/cdreams.ttf");

        et1 = (EditText)findViewById(R.id.newet);
        et2 = (EditText)findViewById(R.id.passet);
        ftsp = getSharedPreferences("spg", MODE_PRIVATE);
        spedit = ftsp.edit();
        pccheck = ftsp.getBoolean("use", false);

        b1.setText("View Notes");
        b1.setTypeface(tf);
        b1.setTextSize(15);

        b2.setText("Add");
        b2.setTypeface(tf);
        b2.setTextSize(15);

        b3.setText("Edit");
        b3.setTypeface(tf);
        b3.setTextSize(15);

        et1.setHint("New Note");
        et1.setGravity(Gravity.CENTER);
        et1.setTextSize(25);
        et1.setTypeface(tf);

        et2.setHint("Enter PIN");
        et2.setGravity(Gravity.CENTER);
        et2.setTextSize(20);
        et2.setTypeface(tf);

        if(pccheck == false){
            Intent i = new Intent(this, SetPin.class);
            startActivityForResult(i, 1);
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        EasyTracker.getInstance(this).activityStart(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EasyTracker.getInstance(this).activityStart(this);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) {
            if(resultCode == RESULT_OK){
                String passcode=data.getStringExtra("passcode");
                spedit.putString("password", passcode);
                spedit.putBoolean("use", true );
                spedit.apply();
            }
        }
    }

    public void addoc(View view){
        int nl;
        String current;
        String newLine;
        String notenew;
        String datetime;
        SimpleDateFormat sdf;
        Date d;

        sdf = new SimpleDateFormat("MM/dd/yy HH:mm");
        d = new Date();
        datetime = sdf.format(d);

        notenew = et1.getText().toString();
        pin = et2.getText().toString();
        pinv = ftsp.getString("password", "");
        newLine = System.getProperty("line.separator");
        current = ftsp.getString("data", "");
        note = datetime + newLine + notenew + newLine + newLine + current;

        nl = notenew.length();

        if(nl>1){
            if(pin.equals(pinv)){
                spedit.putString("data", note);
                spedit.apply();
                Toast.makeText(this, "Note Saved" , Toast.LENGTH_SHORT).show();
                et1.setText("");
            }

            else{
                Toast.makeText(this, "Invalid PIN. Try again.",Toast.LENGTH_SHORT).show();
                et2.setText("");
            }
        }
        else{
        Toast.makeText(this, "Enter a new note first.", Toast.LENGTH_SHORT).show();
        }
    }

    public void showoc(View view){

        Button b4;
        TextView tv2;
        InputMethodManager imm;


        pin = et2.getText().toString();
        pinv = ftsp.getString("password", "");
        note = ftsp.getString("data", "");
        imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);

        if(pin.equals(pinv)){
            setContentView(R.layout.activity_view);
            tv2 = (TextView)findViewById(R.id.viewtv);
            imm.hideSoftInputFromWindow(tv2.getWindowToken(), 0);
            tv2.setMovementMethod(new ScrollingMovementMethod());
            tv2.setText(note);
            tv2.setTextColor(Color.BLACK);
            tv2.setTypeface(tf);
            b4 = (Button)findViewById(R.id.backb);
            b4.setText("Back");
            b4.setTextSize(15);
            b4.setTypeface(tf);
        }
        else{
            Toast.makeText(this, "Invalid PIN. Try again.", Toast.LENGTH_SHORT).show();
            et2.setText("");
        }
    }

    public void editoc(View view){

        EditText et5;
        Button b5;
        InputMethodManager imm;


        pin = et2.getText().toString();
        pinv = ftsp.getString("password", "");
        note = ftsp.getString("data", "");
        imm = (InputMethodManager) getSystemService(Activity.INPUT_METHOD_SERVICE);



        if(pin.equals(pinv)){
            setContentView(R.layout.activity_edit);
            et5 = (EditText)findViewById(R.id.edittext);
            imm.hideSoftInputFromWindow(et5.getWindowToken(), 0);
            et5.setText(note);
            et5.setTextColor(Color.BLACK);
            et5.setTypeface(tf);
            b5 = (Button)findViewById(R.id.updateb);
            b5.setText("Update");
            b5.setTextSize(15);
            b5.setTypeface(tf);
        }
        else{
            Toast.makeText(this, "Invalid PIN. Try again.", Toast.LENGTH_SHORT).show();
            et2.setText("");
        }
    }

    public void updateoc(View view){

        EditText et5;

        et5 = (EditText)findViewById(R.id.edittext);
        note = et5.getText().toString();

        spedit.putString("data", note);
        spedit.apply();
        Toast.makeText(this, "Note Updated." , Toast.LENGTH_SHORT).show();
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }

    public void backoc(View view){
        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }
}