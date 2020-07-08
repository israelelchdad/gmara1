package com.example.myapp.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.model.DafLearning1;
import com.example.myapp.R;
import com.example.myapp.activity.SplashActivity;
import com.example.myapp.adapters.NameAllMasechtotAdapter;
import com.example.myapp.adapters.OneDafAdapter;
import com.example.myapp.databinding.FragmentShewStudyRvBinding;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ShewStudyRvFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShewStudyRvFragment extends Fragment {
    FragmentShewStudyRvBinding binding;
    ArrayList<DafLearning1> myList1 = new ArrayList<>();
    boolean showListMasechtot;


    public ShewStudyRvFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ShewStudyRvFragment newInstance(ArrayList<DafLearning1>myList1, boolean showListMasechtot) {
        ShewStudyRvFragment fragment = new ShewStudyRvFragment();
        fragment.showListMasechtot= showListMasechtot;
        Bundle args = new Bundle();
        args.putParcelableArrayList(SplashActivity.KEY_EXTRA_List1,myList1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            myList1 = getArguments().getParcelableArrayList(SplashActivity.KEY_EXTRA_List1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = FragmentShewStudyRvBinding.inflate(inflater, container, false);
        if(showListMasechtot){
            initReciclerviewMasechtot();
            binding.showStudyRVMasechtot.setVisibility(View.VISIBLE);
        }
      initReciclerviewDapim();
       return binding.getRoot();
    }

    private void initReciclerviewDapim() {

        RecyclerView recyclerView = binding.showStudyRVDapim;
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        OneDafAdapter myAdapter = new OneDafAdapter(getContext(),myList1);
        recyclerView.setAdapter(myAdapter);
    }

    private void initReciclerviewMasechtot() {
        RecyclerView recyclerViewMasechtot = binding.showStudyRVMasechtot;
        recyclerViewMasechtot.setLayoutManager(new LinearLayoutManager(getContext(),LinearLayoutManager.HORIZONTAL,false));
        ArrayList<String> allMasechtot = new ArrayList<>();
        for (int i = 1; i <myList1.size() ; i++) {
            if(i == myList1.size()-1){
                allMasechtot.add(myList1.get(i).getMasechet());
                NameAllMasechtotAdapter myAdapter2 = new NameAllMasechtotAdapter(getContext(),allMasechtot);
                recyclerViewMasechtot.setAdapter(myAdapter2);
                return;
            }
            if(!myList1.get(i).getMasechet().equals(myList1.get(i-1).getMasechet())){
                allMasechtot.add(myList1.get(i-1).getMasechet());
            }

        }

    }
}