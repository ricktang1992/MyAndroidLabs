package algonquin.cst2335.tang0243;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import algonquin.cst2335.tang0243.databinding.ActivityMainBinding;

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
    protected String cityName;
    protected String stringURL;
    protected String imageUrl;

    double current;
    double min;
    double max;
    int humidity;

    JSONArray weatherArray;
   // JSONObject mainObject;
    RequestQueue queue = null;
    protected Bitmap image;


    //This part goes at the top of the onCreate function:

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
        ActivityMainBinding binding = ActivityMainBinding.inflate(getLayoutInflater());
        // Set the layout for this activity based on the XML layout resource "activity_main".
        setContentView(binding.getRoot());

        // Initialize the UI components by finding them in the layout XML.
        tv = findViewById(R.id.textView);
        et = findViewById(R.id.editText);
        btn = findViewById(R.id.getForecast);
        //image=binding.imageView;
        queue = Volley.newRequestQueue(this);

        binding.getForecast.setOnClickListener(click -> {
            cityName = binding.editText.getText().toString();
            try {
                stringURL = "https://api.openweathermap.org/data/2.5/weather?q="
                        + URLEncoder.encode(cityName, "UTF-8")
                        + "&appid=7e943c97096a9784391a981c4d878b22&units=metric";
            } catch (UnsupportedEncodingException e) {
                throw new RuntimeException(e);
            }

            //this goes in the button click handler:
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, stringURL, null,
                    (response) -> {
                        try {
                            weatherArray = response.getJSONArray("weather");

                            int vis = response.getInt("visibility");
                            String name = response.getString("name");
                            JSONObject position0 = weatherArray.getJSONObject(0);
                            String description = position0.getString("description");
                            String iconName = position0.getString("icon");
                            JSONObject mainObject = response.getJSONObject("main");
                            double current = mainObject.getDouble("temp");
                            double min = mainObject.getDouble("temp_min");
                            double max = mainObject.getDouble("temp_max");
                            int humidity = mainObject.getInt("humidity");
                            binding.tempText.setText("The current temperature is " + current);
                            binding.tempText.setVisibility(View.VISIBLE);
                            binding.minText.setText("The min temperature is " + min);
                            binding.minText.setVisibility(View.VISIBLE);
                            binding.maxText.setText("The max temperature is " + max);
                            binding.maxText.setVisibility(View.VISIBLE);
                            binding.humidityText.setText("The humidity rate is " + humidity);
                            binding.humidityText.setVisibility(View.VISIBLE);
                            binding.description.setText(description);
                            binding.description.setVisibility(View.VISIBLE);
                            imageUrl = "http://openweathermap.org/img/w/" + iconName + ".png";
                            String iconUrl = "https://openweathermap.org/img/w/" + iconName + ".png";
            /*ImageRequest imgReq = new ImageRequest(iconUrl, new Response.Listener<Bitmap>() {
                @Override
                public void onResponse(Bitmap bitmap) {
                    // Do something with loaded bitmap...
                    binding.imageView.setImageBitmap(bitmap);
                    Log.e("ImageRequest", " loading image: " + iconUrl);

                }
            }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {
                Log.e("ImageRequest", "Error loading image: " + error.getMessage());
            });

            queue.add(imgReq);*/
                            String pathname = getFilesDir() + "/" + iconName + ".png";
                            File file = new File(pathname);
                            if (file.exists()) {
                                image = BitmapFactory.decodeFile(pathname);
                                binding.imageView.setImageBitmap(image);
                            } else {
                                ImageRequest imgReq2 = new ImageRequest(iconUrl, new Response.Listener<Bitmap>() {
                                    @Override
                                    public void onResponse(Bitmap bitmap) {
                                        // Do something with loaded bitmap...

                                        try {
                                            binding.imageView.setImageBitmap(bitmap);
                                            Log.e("ImageRequest", " loading image: " + iconUrl);
                                            image= bitmap;
                                            image.compress(Bitmap.CompressFormat.PNG,100,MainActivity.this.openFileOutput(iconName+".png", Activity.MODE_PRIVATE));
                                        } catch (Exception e) {

                                        }
                                        Log.e("ImageRequest", " loading image: " + iconUrl);

                                    }
                                }, 1024, 1024, ImageView.ScaleType.CENTER, null, (error) -> {
                                    Log.e("ImageRequest", "Error loading image: " + error.getMessage());
                                });
                                queue.add(imgReq2);
                            }
                            Log.d("WeatherResponse", "Visibility: " + vis);
                            Log.d("WeatherResponse", "Name: " + name);
                            Log.d("WeatherResponse", "Description: " + description);
                            Log.d("WeatherResponse", "IconName: " + iconName);
                            Log.d("WeatherResponse", "Current Temperature: " + current);
                            Log.d("WeatherResponse", "Min Temperature: " + min);
                            Log.d("WeatherResponse", "Max Temperature: " + max);
                            Log.d("WeatherResponse", "Humidity: " + humidity);
                            Log.d("WeatherResponse", "iconURL: " + imageUrl);
                            Log.d("WeatherResponse", "iconURL: " + "http://openweathermap.org/img/w/" + iconName + ".png");
                        } catch (JSONException e) {
                            throw new RuntimeException(e);
                        }
                    },
                    (error) -> {
                    });
            queue.add(request);
            //String iconName=binding.imageText.getText().toString();

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