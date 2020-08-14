package com.cnakhn.faradarscompletion.ExampleFragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.cnakhn.faradarscompletion.R;

public class ExampleSlideFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.layout_example_viewpager_slide, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView textView = view.findViewById(R.id.txt_viewpager_slide);
        textView.setText(getArguments().getString("data", ""));
    }

    public static ExampleSlideFragment newInstance(String data) {
        
        Bundle args = new Bundle();
        args.putString("data", data);
        ExampleSlideFragment fragment = new ExampleSlideFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
