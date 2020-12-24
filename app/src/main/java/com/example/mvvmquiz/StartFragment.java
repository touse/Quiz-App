package com.example.mvvmquiz;


import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;


/**
 * A simple {@link Fragment} subclass.
 */
public class StartFragment extends Fragment {

    private ProgressBar startprogress;
    private TextView startfeedBackText;
    private FirebaseAuth firebaseAuth;
    private static final String  Start_Tag="Start_log";
    private NavController navController;

    public StartFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        firebaseAuth=FirebaseAuth.getInstance();

        navController= Navigation.findNavController(view);

        startprogress=view.findViewById(R.id.start_progess);
        startfeedBackText=view.findViewById(R.id.start_feedback);
        startfeedBackText.setText("Checking User Account...");

    }

    @Override
    public void onStart() {
        super.onStart();

        FirebaseUser currentUser=firebaseAuth.getCurrentUser();
        if(currentUser==null)
        {
            //create new account
            startfeedBackText.setText("Creating Account...");
            firebaseAuth.signInAnonymously().addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {

                    if(task.isSuccessful())
                    {
                        startfeedBackText.setText("Account Created...");
                        navController.navigate(R.id.action_startFragment_to_listFragment);

                    }else
                    {
                        Log.d(Start_Tag,"start log : " + task.getException());
                    }

                }
            });

        }

        else
        {
            //navigate to homepage
            startfeedBackText.setText("Logged In...");
            navController.navigate(R.id.action_startFragment_to_listFragment);
        }
    }
}
