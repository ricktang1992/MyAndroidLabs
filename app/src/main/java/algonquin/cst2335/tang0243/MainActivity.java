package algonquin.cst2335.tang0243;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * @author Ziyao Tang
 * @version 1.0
 */
public class MainActivity extends AppCompatActivity {

    /**
     * The TextView used to display text or information to the user.
     */
    private TextView tv = null;

    /**
     * The EditText used to allow the user to input text or data.
     */
    private EditText et = null;

    /**
     * The Button used to trigger actions or events in the application.
     */
    private Button btn = null;

    /**
     * Called when the activity is first created. This is where you should
     * initialize your user interface and set up event listeners.
     *
     * @param savedInstanceState The saved state of the activity, which can be null if
     *                           the activity is being created for the first time.
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Set the layout for this activity based on the XML layout resource "activity_main".
        setContentView(R.layout.activity_main);

        // Initialize the UI components by finding them in the layout XML.
        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editText);
        btn = findViewById(R.id.button);

        // Set a click listener for the Button "btn" to handle password complexity checking.
        btn.setOnClickListener( clk ->{
            String password = et.getText().toString();

            // Check if the provided password meets the specified complexity requirements.
            if(checkPasswordComplexity(password)){
                tv.setText("Your password meets the requirements");
            }else{
                tv.setText("You shall not pass!");
            }
        });
    }

    /** This function will check the parameter is complex or not.
     *
     * @param pw The String object that we are checking
     * @return Returns true if the password is complex and meet the requirement
     */
    boolean checkPasswordComplexity(String pw)
    {
        boolean foundUpperCase, foundLowerCase, foundNumber, foundSpecial;

        foundUpperCase = foundLowerCase = foundNumber = foundSpecial = false;

        for(int i=0;i<pw.length();i++){
            if(Character.isDigit(pw.charAt(i))){
                foundNumber=true;
            }
            else if(Character.isUpperCase(pw.charAt(i))){
                foundUpperCase=true;
            }
            else if(Character.isLowerCase(pw.charAt(i))) {
                foundLowerCase = true;
            }
            else if(isSpecialCharacter(pw.charAt(i))){
                foundSpecial=true;
            }
        }

        if(!foundUpperCase)
        {
            CharSequence text = "You need Upper Case!";
            Toast.makeText(this,text, Toast.LENGTH_SHORT).show();// Say that they are missing an upper case letter;
            return false;

        }

        else if( ! foundLowerCase)
        {
            CharSequence text = "You need Lower Case!";
            Toast.makeText(this,text,Toast.LENGTH_SHORT).show();// Say that they are missing an upper case letter;

            return false;

        }

        else if( ! foundNumber) {
            CharSequence text = "You need Number!";
            Toast.makeText(this,text,Toast.LENGTH_SHORT).show();// Say that they are missing an upper case letter;

            return false;
        }

        else if(! foundSpecial) {
            CharSequence text = "You need Special character!";
            Toast.makeText(this,text,Toast.LENGTH_SHORT).show();// Say that they are missing an upper case letter;

            return false;
        }

        else

            return true; //only get here if they're all true
    }

    /**
     * Checks if a character is a special character.
     *
     * @param c The character to be checked.
     * @return True if the character is a special character, false otherwise.
     */
    boolean isSpecialCharacter(char c)

    {
        switch (c)
        {
            case '#':
            case '$':
            case '%':
            case '^':
            case '&':
            case '*':
            case '!':
            case '@':
            case '?':
                Log.d( "aaaaaaaaaaaaa", "Message");
                return true;
            default:
                Log.d( "aaaaaaaaaaaaa", "fffffffffffffffff");
                return false;
        }


    }
}