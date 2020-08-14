package com.cnakhn.faradarscompletion.ExampleFragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.cnakhn.faradarscompletion.R;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;

public class ExampleBottomSheetDialogFragment extends BottomSheetDialogFragment {
    private ExampleDialogFragment.ExampleDialogCallback dialogCallback;

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialogCallback = (ExampleDialogFragment.ExampleDialogCallback) context;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_example_fragment, container, false);
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
        return view;
    }

}
