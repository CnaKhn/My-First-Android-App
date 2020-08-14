package com.cnakhn.faradarscompletion.ExampleFragments;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.cnakhn.faradarscompletion.Fragments.SlideFragment;

public class SlideAdapter extends FragmentStateAdapter {
    public SlideAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        String data;
        switch (position) {
            case 0:
                data = "A";
                break;
            case 1:
                data = "B";
                break;
            case 2:
                data = "C";
                break;
            case 3:
                data = "D";
                break;
            case 4:
                data = "E";
                break;
            default:
                data = "";
                break;
        }
        return ExampleSlideFragment.newInstance(data);
    }

    @Override
    public int getItemCount() {
        return 5;
    }
}
