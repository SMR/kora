package org.gskbyte.kora.settingsActivities;

import org.gskbyte.kora.R;
import org.gskbyte.kora.settings.User;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.Button;

public class SelectUserDialog extends AlertDialog
{
    Resources resources;
    UsersActivity activity;
    
    Button chooseButton, copyButton, editButton, deleteButton;
        
    public SelectUserDialog(Context context, User user)
    {
        super(context);
        resources = context.getResources();
        activity = (UsersActivity) context;
        
        View v = View.inflate(context, R.layout.user_select, null);
        chooseButton = (Button) v.findViewById(R.id.chooseButton);
        copyButton = (Button) v.findViewById(R.id.copyButton);
        editButton = (Button) v.findViewById(R.id.editButton);
        deleteButton = (Button) v.findViewById(R.id.deleteButton);
        
        setUser(user);
        
        setButton(BUTTON_NEGATIVE, resources.getString(R.string.return_), new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                 cancel();
            }
        });

        chooseButton.setOnClickListener(chooseListener);
        copyButton.setOnClickListener(copyListener);
        editButton.setOnClickListener(editListener);
        deleteButton.setOnClickListener(deleteListener);
        
        setView(v);
    }
    
    public void setUser(User user)
    {
        setTitle(resources.getString(R.string.user) + ": " + user.getName());
        setIcon(user.getPhoto());
        boolean isCustom = user.isCustom();
        editButton.setEnabled(isCustom);
        deleteButton.setEnabled(isCustom);
    }
    
    private android.view.View.OnClickListener chooseListener = new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                activity.chooseCurrentProfile();
                dismiss();
            }
        };
        
    private android.view.View.OnClickListener copyListener = new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                activity.showDialog(UsersActivity.COPY_DIALOG_ID);
                dismiss();
            }
        };
    
    private android.view.View.OnClickListener editListener = new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                activity.showDialog(UsersActivity.EDIT_DIALOG_ID);
                dismiss();
            }
        };
    
    private android.view.View.OnClickListener deleteListener = new android.view.View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                activity.showDialog(UsersActivity.CONFIRM_DELETE_DIALOG_ID);
                dismiss();
            }
        };

    
}
