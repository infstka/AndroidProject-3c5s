package com.android.project;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.DialogFragment;

public class BlackListDialog extends DialogFragment {

    private DialogInterface.OnClickListener Listener;

    public BlackListDialog(DialogInterface.OnClickListener listener)
    {
        this.Listener = listener;
    }
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        return builder.setTitle("Блокировка пользователей")
                .setMessage("Заблокировать пользователя?")
                .setPositiveButton("Да", Listener)
                .setNegativeButton("Нет", null).create();
    }
}
