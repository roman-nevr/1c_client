package com.example.dmitry.a1c_client.android.views.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;


/**
 * Created by Admin on 07.12.2016.
 */

public class MessageDialogFragment extends DialogFragment {

    private MessageCallBack listener;
    private int id;
    private View form;
    public static final String MESSAGE = "message";
    public static final String ID = "id";

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        form = getActivity().getLayoutInflater()
                .inflate(R.layout.message_dialog_fragment_layout, null, true);
        TextView tvMessage = (TextView) form.findViewById(R.id.tvMessage);
        Button btnOk = (Button) form.findViewById(R.id.btnOk);
        tvMessage.setText(bundle.getString(MESSAGE));
        id = bundle.getInt(ID);
        btnOk.setOnClickListener(v -> dismiss());
        AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
        builder.setView(form);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if(getParentFragment() instanceof MessageCallBack){
            listener = (MessageCallBack) getParentFragment();
        }else if(context instanceof MessageCallBack){
            listener = (MessageCallBack) context;
        }else {
            throw new UnsupportedOperationException();
        }
    }

    public interface MessageCallBack {
        void onMessageButtonClick(int id);
    }

    public static MessageDialogFragment newInstance(String message, int id){
        MessageDialogFragment fragment = new MessageDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(MessageDialogFragment.MESSAGE, message);
        bundle.putInt(ID, id);
        fragment.setArguments(bundle);
        return fragment;
    }

    public static MessageDialogFragment newInstance(String message){
        return newInstance(message, 0);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(listener != null){
            listener.onMessageButtonClick(id);
        }
    }
}
