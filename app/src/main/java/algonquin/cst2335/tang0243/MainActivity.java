package algonquin.cst2335.tang0243;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //No ids loaded yet.
        setContentView(R.layout.activity_main);


        TextView tv=findViewById(R.id.myTextView);//same as getElementById() from JS

        Button b=findViewById(R.id.myButton);//same as getElementById() from JS

        EditText ev=findViewById(R.id.myEditText);//same as getElementById() from JS

        //OnClickListener       //anonymous class
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                tv.setText("You clicked the button");
                ev.setText("You clicked the button");
                b.setText("You clicked the button");


            }
        });
        /*
            b.setOnClickListener(
                    ( v ) -> {

                        tv.setText("You clicked the button");
                        ev.setText("You clicked the button");
                        b.setText("You clicked the button");
            });
        */
    }
}