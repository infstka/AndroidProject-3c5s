package com.android.project.UserInterface.Categories;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import com.android.project.Activities.NewCategoryActivity;
import com.android.project.JSON;
import com.android.project.R;
import com.android.project.databinding.FragmentCategoriesBinding;

import java.util.ArrayList;
import java.util.List;

public class CategoriesFragment extends Fragment {

    private FragmentCategoriesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {

        binding = FragmentCategoriesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        ListView listView = root.findViewById(R.id.recyclerview);

        List<String> categories = JSON.importFromJSON(getActivity().getApplicationContext());
        if (categories == null)
            categories = new ArrayList<>();
        ListAdapter adapter = new ArrayAdapter(
                getActivity().getApplicationContext(),
                R.layout.category_item, categories);

        listView.setAdapter(adapter);

        binding.fab.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity().getApplicationContext(), NewCategoryActivity.class);
            startActivity(intent);
        });

        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}