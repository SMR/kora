package org.gskbyte.kora.settingsActivities.useProfiles;

import org.gskbyte.kora.R;
import org.gskbyte.kora.settings.UseProfile;

import android.os.Bundle;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.CompoundButton.OnCheckedChangeListener;

public class InteractionActivity extends ProfilePropertiesActivity
{
    private static final String TAG = "InteractionActivity";
    
    private RadioButton mTouchRadio,
                    mMultitouchRadio, mPressAndDragRadio, mSimpleRadio,
                mScanRadio;
    
    private TextView mScanSecondsText;
    private EditText mScanSecondsEdit;
    private Button mAcceptButton, mCancelButton;

    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_LEFT_ICON);
        setContentView(R.layout.use_profile_interaction);
        getWindow().setFeatureDrawableResource(Window.FEATURE_LEFT_ICON,
                                               R.drawable.icon_interaction);

        initButtonBar();
        
        /* Load views */
        mTouchRadio = (RadioButton) findViewById(R.id.touchInteractionRadio);
        mMultitouchRadio = (RadioButton) findViewById(R.id.multitouchRadio);
        mPressAndDragRadio = (RadioButton) findViewById(R.id.pressAndDragRadio);
        mSimpleRadio = (RadioButton) findViewById(R.id.simpleTouchRadio);
        mScanRadio = (RadioButton) findViewById(R.id.scanInteractionRadio);
        mScanSecondsText = (TextView) findViewById(R.id.scanSecondsText);
        mScanSecondsEdit = (EditText) findViewById(R.id.scanSecondsEdit);
        
        /* Add listeners */
        mTouchRadio.setOnCheckedChangeListener(touchListener);
        
    }
    
    protected void setView()
    {
        /* Set main interaction mode */
        if(mUseProfile.mainInteraction == UseProfile.interaction.touch_mode){
            touchListener.onCheckedChanged(mTouchRadio, true);
        } else {
            mScanRadio.setChecked(true);
            touchListener.onCheckedChanged(mTouchRadio, false);
        }
        
        /* Set touch interaction mode */
        switch(mUseProfile.touchMode){
        case UseProfile.interaction.multitouch_and_drag:
            mMultitouchRadio.setChecked(true);
            break;
        case UseProfile.interaction.press_and_drag:
            mPressAndDragRadio.setChecked(true);
            break;
        case UseProfile.interaction.simple_press:
            mSimpleRadio.setChecked(true);
            break;
        }

        /* Set scan interaction mode */
        mScanSecondsEdit.setText(String.valueOf(mUseProfile.scanTimeMillis));
    }
    
    protected void captureData()
    {
        if(mTouchRadio.isChecked()){
            mUseProfile.mainInteraction =
                UseProfile.interaction.touch_mode;
        } else {
            mUseProfile.mainInteraction =
                UseProfile.interaction.scan_mode;
        }
        
        if(mMultitouchRadio.isChecked()) {
            mUseProfile.touchMode =
                UseProfile.interaction.multitouch_and_drag;
        } else if(mPressAndDragRadio.isChecked()) {
            mUseProfile.touchMode =
                UseProfile.interaction.press_and_drag;
        } else {
            mUseProfile.touchMode =
                UseProfile.interaction.simple_press;
        }
        String timeString = mScanSecondsEdit.getText().toString();
        mUseProfile.scanTimeMillis = Integer.parseInt(timeString);
    }
    
    private OnCheckedChangeListener touchListener =
        new OnCheckedChangeListener(){
            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                    boolean isChecked)
            {
                mMultitouchRadio.setEnabled(isChecked);
                mPressAndDragRadio.setEnabled(isChecked);
                mSimpleRadio.setEnabled(isChecked);

                mScanSecondsText.setEnabled(!isChecked);
                mScanSecondsEdit.setEnabled(!isChecked);
            }
        };
}
