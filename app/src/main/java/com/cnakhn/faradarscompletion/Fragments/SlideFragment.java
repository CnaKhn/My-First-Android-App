package com.cnakhn.faradarscompletion.Fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.cnakhn.faradarscompletion.R;

public class SlideFragment extends Fragment {
    private int imageID;
    private String title, content;

    public static SlideFragment newSlide(int imageID, String title, String content) {
        SlideFragment slideFragment = new SlideFragment();
        Bundle args = new Bundle();
        args.putInt("imageID", imageID);
        args.putString("title", title);
        args.putString("content", content);
        slideFragment.setArguments(args);
        return slideFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle args = getArguments();
        if (args == null) return;
        imageID = args.getInt("imageID");
        title = args.getString("title");
        content = args.getString("content");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.layout_slide, container, false);
        ((ImageView) view.findViewById(R.id.slide_image)).setImageResource(imageID);
        ((TextView) view.findViewById(R.id.slide_title)).setText(title);
        ((TextView) view.findViewById(R.id.slide_content)).setText(content);


        return view;
    }
}
