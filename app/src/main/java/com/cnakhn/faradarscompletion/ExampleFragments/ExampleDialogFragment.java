package com.cnakhn.faradarscompletion.ExampleFragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.cnakhn.faradarscompletion.R;

public class ExampleDialogFragment extends DialogFragment {
    private ExampleDialogCallback dialogCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialogCallback = (ExampleDialogCallback) context;
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_example_fragment, null, false);
        builder.setView(view);


        final EditText inputText = view.findViewById(R.id.input_dialog_fragment_text);
        Button btnSubmit = view.findViewById(R.id.btn_dialog_fragment_submit);
        Button btnCancel = view.findViewById(R.id.btn_dialog_fragment_cancel);

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputText.length() > 0) {
                    dialogCallback.onSubmit(inputText.getText().toString());
                    dismiss();
                }
            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogCallback.onCancel();
                dismiss();
            }
        });
        return builder.create();
    }

    public interface ExampleDialogCallback {
        void onSubmit(String data);

        void onCancel();

    }
}
