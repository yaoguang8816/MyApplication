package com.example.baijunfeng.myapplication.fragment;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.baijunfeng.myapplication.R;
import com.example.baijunfeng.myapplication.TabActivity;

/**
 * Created by baijunfeng on 17/3/13.
 */

/**
 * A placeholder fragment containing a simple view.
 */
public class ViewTestFragment extends Fragment {
    /**
     * The fragment argument representing the section number for this
     * fragment.
     */
    private static final String ARG_SECTION_NUMBER = "section_number";

    public ViewTestFragment() {
    }

    /**
     * Returns a new instance of this fragment for the given section
     * number.
     */
    public static ViewTestFragment newInstance(int sectionNumber) {
        ViewTestFragment fragment = new ViewTestFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_SECTION_NUMBER, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.view_test_fragment_tab, container, false);
        TextView textView = (TextView) rootView.findViewById(R.id.section_label);
        textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));


        Button button = (Button) rootView.findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View imageView = rootView.findViewById(R.id.imageViewSVG);
                int cx = (imageView.getLeft() + imageView.getRight()) / 2;
                int cy = (imageView.getTop() + imageView.getBottom()) / 2;

                int finalRadius = Math.max(imageView.getWidth(), imageView.getHeight());

                Animator animator = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, 0, finalRadius);
                animator.setDuration(1000);

                imageView.setVisibility(View.VISIBLE);

                // make the view invisible when the animation is done
                animator.addListener(new AnimatorListenerAdapter() {
                    @Override
                    public void onAnimationEnd(Animator animation) {
                        super.onAnimationEnd(animation);
                        imageView.setVisibility(View.INVISIBLE);
                    }
                });
                animator.start();
            }
        });

        Button button1 = (Button) rootView.findViewById(R.id.button1);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View imageView = rootView.findViewById(R.id.imageViewSVG);
                int cx = (imageView.getLeft() + imageView.getRight()) / 2;
                int cy = (imageView.getTop() + imageView.getBottom()) / 2;

                int finalRadius = Math.max(imageView.getWidth(), imageView.getHeight());

                Animator animator = ViewAnimationUtils.createCircularReveal(imageView, cx, cy, finalRadius, 0);
                animator.setDuration(1000);

                imageView.setVisibility(View.VISIBLE);

                animator.start();
            }
        });
        return rootView;
    }
}
