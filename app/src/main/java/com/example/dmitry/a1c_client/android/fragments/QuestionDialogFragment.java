package com.example.dmitry.a1c_client.android.fragments;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.dmitry.a1c_client.R;
import com.example.dmitry.a1c_client.misc.utils;


/**
 * Created by Admin on 07.12.2016.
 */

public class QuestionDialogFragment extends DialogFragment {

    public static final String QUESTION = "question";
    public static final String BUTTON_OK = "ok";
    public static final String BUTTON_CANCEL = "cancel";
    public static final String ID = "id";
    private static AnswerCallBack listener;
    private View form;
    private boolean ok = false;
    private int queryId;

    public static QuestionDialogFragment newInstance(String question, String btnOkText,
                                                     String btnCancelText, int queryId,
                                                     AnswerCallBack callBack) {
        listener = callBack;
        QuestionDialogFragment fragment = new QuestionDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(QuestionDialogFragment.QUESTION, question);
        bundle.putString(QuestionDialogFragment.BUTTON_OK, btnOkText);
        bundle.putString(QuestionDialogFragment.BUTTON_CANCEL, btnCancelText);
        bundle.putInt(ID, queryId);
        fragment.setArguments(bundle);
        fragment.setCancelable(false);
        return fragment;
    }

    public static QuestionDialogFragment newInstance(String question) {
        QuestionDialogFragment fragment = new QuestionDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString(QuestionDialogFragment.QUESTION, question);
        bundle.putString(QuestionDialogFragment.BUTTON_OK, "Да");
        bundle.putString(QuestionDialogFragment.BUTTON_CANCEL, "Нет");
        bundle.putInt(ID, 0);
        fragment.setArguments(bundle);
        return fragment;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Bundle bundle = getArguments();
        queryId = bundle.getInt(ID);
        form = getActivity().getLayoutInflater()
                .inflate(R.layout.question_dialog_fragment_layout, null, true);
        TextView tvMessage = (TextView) form.findViewById(R.id.tvMessage);
        Button btnOk = (Button) form.findViewById(R.id.btnOk);
        Button btnCancel = (Button) form.findViewById(R.id.btnCancel);
        btnOk.setText(bundle.getString(BUTTON_OK));
        btnCancel.setText(bundle.getString(BUTTON_CANCEL));
        tvMessage.setText(bundle.getString(QUESTION));
        btnOk.setOnClickListener(v -> {
            if (listener != null) {
                listener.onOkButtonClick(queryId);
            }
            ok = true;
            dismiss();
        });
        btnCancel.setOnClickListener(v -> dismiss());
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setView(form);
        Dialog dialog = builder.create();
        dialog.setCanceledOnTouchOutside(false);
        return dialog;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        getDialog().setCanceledOnTouchOutside(false);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (getParentFragment() instanceof MessageDialogFragment.MessageCallBack) {
            listener = (AnswerCallBack) getParentFragment();
        } else if (context instanceof MessageDialogFragment.MessageCallBack) {
            listener = (AnswerCallBack) context;
        } else {
            throw new UnsupportedOperationException();
        }
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        /*if (listener != null && !ok) {
            listener.onCancelButtonClick(queryId);
        }*/
    }

    @Override public void onPause() {
        super.onPause();
        //dismiss();
    }

    public interface AnswerCallBack {
        void onOkButtonClick(int queryId);

        void onCancelButtonClick(int queryId);
    }
}
