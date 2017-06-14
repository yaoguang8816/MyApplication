package com.example.baijunfeng.myapplication.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.transition.Explode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.baijunfeng.myapplication.AncientPoetryActivity;
import com.example.baijunfeng.myapplication.R;
import com.example.baijunfeng.myapplication.utils.Util;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LiteratureFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LiteratureFragment extends Fragment implements View.OnClickListener{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public LiteratureFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LiteratureFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static LiteratureFragment newInstance(String param1, String param2) {
        LiteratureFragment fragment = new LiteratureFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }

        Explode explode = new Explode();
        getActivity().getWindow().setExitTransition(explode);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_literature, container, false);

//        CardView cardView = (CardView) view.findViewById(R.id.card_view1);
        view.findViewById(R.id.card_view_shi).setOnClickListener(this);
        view.findViewById(R.id.card_view_ci).setOnClickListener(this);
        view.findViewById(R.id.card_view_guwen).setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(getActivity(), AncientPoetryActivity.class);

        String type = "";
        switch (v.getId()) {
            case R.id.card_view_shi:
                type = Util.LITERATURE_TYPE.SHI;
                break;
            case R.id.card_view_ci:
                type = Util.LITERATURE_TYPE.CI;
                break;
            case R.id.card_view_guwen:
                type = Util.LITERATURE_TYPE.GUWEN;
                break;
            case R.id.card_view_gutishi:
                type = Util.LITERATURE_TYPE.GUTISHI;
                break;
        }

        intent.putExtra(Util.LITERATURE_TYPE_EXTRA, type);
        startActivity(intent);
    }
}
