package org.gskbyte.Kora;


import org.gskbyte.Kora.R;
import org.gskbyte.Kora.Settings.*;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class WelcomeActivity extends Activity
{
    private static final String TAG = "WelcomeActivity";
    
    private Button startButton, settingsButton;
    private TextView autostartText;
    
    private void init()
    {
        SettingsManager.init(this);
    }
    
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        startButton = (Button)findViewById(R.id.startButton);
        settingsButton = (Button)findViewById(R.id.settingsButton);
        autostartText =  (TextView)findViewById(R.id.autostart);
        
        // Asociar eventos con botones
        startButton.setOnClickListener(startButtonListener);
        settingsButton.setOnClickListener(configureButtonListener);
        
        // Cargar datos de programa (usuarios, perfiles, etc)
        init();
        
        SharedPreferences settings = this.getSharedPreferences("global_settings", 0);
        String nombre = settings.getString("mCurrentUser", "Default---");
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("mCurrentUser", "random");
        editor.commit();
        
        // Cargar estos datos de donde corresponde
        String userName = nombre,
            willStart = getResources().getString(R.string.autostartText1),
            seconds = getResources().getString(R.string.autostartText2);
        int seconds_left  = 10;
        autostartText.setText(userName + " " + willStart + " " + seconds_left + " " + seconds);
        
    }
    
    private OnClickListener startButtonListener = new OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(WelcomeActivity.this, ControlActivity.class);
            startActivity(i);
        }
    };
    
    private OnClickListener configureButtonListener = new OnClickListener() {
        public void onClick(View v) {
            Intent i = new Intent(WelcomeActivity.this, SettingsActivity.class);
            startActivity(i);
        }
    };
}