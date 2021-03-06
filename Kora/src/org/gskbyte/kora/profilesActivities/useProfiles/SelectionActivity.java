package org.gskbyte.kora.profilesActivities.useProfiles;

import org.gskbyte.kora.R;
import org.gskbyte.kora.profiles.ProfilesManager;
import org.gskbyte.kora.profiles.UseProfile;
import org.gskbyte.kora.profiles.User;
import org.gskbyte.kora.profiles.ProfilesManager.SettingsException;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class SelectionActivity extends Activity
{
    private static final String TAG = "UserSelectionActivity";
    
    private Resources mResources;
    private User mCurrentUser;
    private UseProfile mSelectedUseProfile;
    private Button mChooseButton, mCopyButton, mEditButton, mDeleteButton,
                   mReturnButton;
    
    private Dialog mDeleteDialog = null;
    
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.use_profile_select);
        
        mResources = getResources();

        mChooseButton = (Button) findViewById(R.id.chooseButton);
        mCopyButton = (Button) findViewById(R.id.copyButton);
        mEditButton = (Button) findViewById(R.id.editButton);
        mDeleteButton = (Button) findViewById(R.id.deleteButton);
        mReturnButton = (Button) findViewById(R.id.returnButton);

        mChooseButton.setOnClickListener(chooseListener);
        mCopyButton.setOnClickListener(copyListener);
        mEditButton.setOnClickListener(editListener);
        mDeleteButton.setOnClickListener(deleteListener);
        mReturnButton.setOnClickListener(returnListener);
    }
    
    public void onStart()
    {
        super.onStart();
        
        try {
            Bundle extras = getIntent().getExtras();
            String useProfileName =  extras.getString(UseProfilesActivity.TAG_USEPROFILE_NAME);
            mCurrentUser = ProfilesManager.getCurrentUser();
            mSelectedUseProfile = ProfilesManager.getUseProfile(useProfileName);
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
            Toast.makeText(this, 
                    "ERROR LOADING USE PROFILE. Please contact author.",
                    Toast.LENGTH_LONG);
        }
        
        setView();
    }
    
    public void setView()
    {
        setTitle(mResources.getString(R.string.useProfile) + ": " +
                 mSelectedUseProfile.getName());

        mChooseButton.setText(mResources.getString(R.string.chooseUseProfileFor) +
                " " +mCurrentUser.getName());
        
        boolean isCustomUser = mCurrentUser.isCustom();
        mChooseButton.setEnabled(isCustomUser);
        
        boolean isCustomProfile = mSelectedUseProfile.isCustom();
        mEditButton.setEnabled(isCustomProfile);
        mDeleteButton.setEnabled(isCustomProfile);
    }
    
    private android.view.View.OnClickListener chooseListener = 
        new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                String userName = mCurrentUser.getName(),
                       useProfileName = mSelectedUseProfile.getName();
                mCurrentUser.setUseProfileName(useProfileName);
                try {
                	ProfilesManager.editUser(userName, mCurrentUser);
                    Toast.makeText(SelectionActivity.this, 
                           userName + " " + mResources.getString(R.string.nowUses) +
                           " " + useProfileName, Toast.LENGTH_SHORT).show();
                } catch (SettingsException e) {
                    Toast.makeText(SelectionActivity.this,
                            mResources.getString(R.string.settingsError),
                            Toast.LENGTH_SHORT).show();
                }
                finish();
            }
        };
    
    private android.view.View.OnClickListener copyListener = 
        new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SelectionActivity.this,
                        CopyActivity.class);
                intent.putExtra(UseProfilesActivity.TAG_USEPROFILE_NAME,
                        mSelectedUseProfile.getName());
                SelectionActivity.this.startActivity(intent);
                finish();
            }
        };
    
    private android.view.View.OnClickListener editListener = 
        new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                Intent intent = new Intent(SelectionActivity.this,
                        AddEditActivity.class);
                intent.putExtra(UseProfilesActivity.TAG_USEPROFILE_NAME,
                        mSelectedUseProfile.getName());
                SelectionActivity.this.startActivity(intent);
                finish();
            }
        };
    
    private android.view.View.OnClickListener deleteListener = 
        new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(mDeleteDialog==null){
                    AlertDialog.Builder builder = new AlertDialog.Builder(SelectionActivity.this);
                    builder.setMessage(
                            mResources.getString(R.string.useProfileDeletionQuestion)+
                            " " + mSelectedUseProfile.getName() + "?")
                    .setIcon(mResources.getDrawable(R.drawable.icon_important))
                    .setCancelable(false)
                    .setPositiveButton(R.string.yes, 
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    try{
                                    	ProfilesManager.removeUseProfile(mSelectedUseProfile.getName());
                                        Toast.makeText(SelectionActivity.this, 
                                                mResources.getString(R.string.deleteUseProfileOk) + ":"  +
                                                mSelectedUseProfile.getName(), Toast.LENGTH_SHORT).show();
                                    }catch (ProfilesManager.SettingsException e){
                                        if(e.type==SettingsException.NOT_EXISTS){
                                            Toast.makeText(SelectionActivity.this,
                                                mResources.getString(R.string.deleteUseProfileFail) + ":"  +
                                                mSelectedUseProfile.getName(), Toast.LENGTH_SHORT).show();
                                        } else { // HAS_DEPENDENCIES
                                            Toast.makeText(SelectionActivity.this,
                                                mResources.getString(R.string.deleteUseProfileFailUsed),
                                                Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                    finish();
                                }
                    })
                    .setNegativeButton(R.string.no,
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                    finish();
                                }
                    });
                    mDeleteDialog = builder.create();
                }
                
                mDeleteDialog.show();
            }
        };
    
    private android.view.View.OnClickListener returnListener = 
        new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                finish();
            }
        };
}
