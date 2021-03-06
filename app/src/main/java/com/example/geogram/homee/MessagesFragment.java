package com.example.geogram.homee;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.geogram.R;
import com.example.geogram.SignedInActivity;

public class MessagesFragment extends Fragment {
    private static final String TAG = "MessagesFragment";



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_messages,container,false);

        Button btn = (Button) view.findViewById(R.id.openMessage);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SignedInActivity.class);
                getActivity().startActivity(intent);
            }
        });
        return view;
    }


}
