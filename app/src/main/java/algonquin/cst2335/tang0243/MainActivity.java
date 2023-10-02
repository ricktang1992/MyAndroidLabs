package algonquin.cst2335.tang0243;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {
    private static String TAG = "MainActivity";

    @Override
    protected void onStart() {
        super.onStart();
        Log.w( TAG, "In onStart() - Visible on screen" );
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.w( TAG, "In onResume() - Responding to user input" );
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.w( TAG, "In onPause() - No longer responding to user input" );
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.w( TAG, "In onStop() - no longer visible" );
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.w( TAG, "In onDestroy() - Freeing memory used by the application" );
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Intent nextPage = new Intent( MainActivity.this, SecondActivity.class);
        Button loginButton=findViewById(R.id.loginButton);;
        EditText emailEditText=findViewById(R.id.Email_Input);
        loginButton.setOnClickListener(clk->
            {
                nextPage.putExtra( "EmailAddress", emailEditText.getText().toString() );
                startActivity( nextPage);
            } );
        Log.d( TAG, "Message");
        Log.w( TAG, "In onCreate() - Loading Widgets" );
    }
}