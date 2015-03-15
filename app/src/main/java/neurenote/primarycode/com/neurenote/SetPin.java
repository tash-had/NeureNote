package neurenote.primarycode.com.neurenote;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SetPin extends Activity {

    EditText et3;
    EditText et4;
    Button b2;
    Typeface tf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin);

        tf = Typeface.createFromAsset(getAssets(), "fonts/cdreams.ttf");
        et3 = (EditText)findViewById(R.id.newpasset);
        et4 = (EditText)findViewById(R.id.confirmet);
        b2 = (Button)findViewById(R.id.setpassb);

        et3.setHint("Enter PIN");
        et3.setGravity(Gravity.CENTER);
        et3.setTypeface(tf);

        et4.setHint("Re-enter PIN");
        et4.setGravity(Gravity.CENTER);
        et4.setTypeface(tf);

        b2.setText("Confirm");
        b2.setGravity(Gravity.CENTER);
        b2.setTypeface(tf);
    }

    public void newpassoc(View view){
        String p;
        String pcon;
        int pl;

        p = et3.getText().toString();
        pcon = et4.getText().toString();
        pl = p.length();
        if(p.equals(pcon)){
            if(pl>3){
                Intent returnIntent = new Intent();
                returnIntent.putExtra("passcode", p);
                setResult(RESULT_OK,returnIntent);
                finish();
            }

            else{
                Toast.makeText(this, "Make a PIN that's more than 3 digits.", Toast.LENGTH_SHORT).show();
                et3.setText("");
                et4.setText("");
            }
        }
        else{
            et3.setText("");
            et4.setText("");
            Toast.makeText(this, "PIN Mismatch. Try Again", Toast.LENGTH_SHORT).show();
        }
    }
}