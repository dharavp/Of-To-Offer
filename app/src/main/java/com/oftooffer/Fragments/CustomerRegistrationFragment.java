package com.oftooffer.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;

import com.oftooffer.Models.UserRegister;
import com.oftooffer.R;
import com.oftooffer.databinding.FragmentCustomerRegestrationBinding;

import org.jetbrains.annotations.NotNull;

import io.codetail.animation.ViewAnimationUtils;
import io.ghyeok.stickyswitch.widget.StickySwitch;

/**
 * Created by dsk-221 on 31/3/17.
 */

public class CustomerRegistrationFragment extends Fragment {

    private UserRegister userRegister;
    FragmentCustomerRegestrationBinding mBinding;

    public CustomerRegistrationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        userRegister = new UserRegister();
        userRegister.setUserTitle("Of To Offer");
        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_customer_regestration, container, false);
        mBinding.setUserRegister(userRegister);
        View view = mBinding.getRoot();

        Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        mBinding.textTitle.setTypeface(face);

        mBinding.stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String s) {
                if (direction == StickySwitch.Direction.LEFT) {
                    startAnimation("customer");
                } else {
                    startAnimation("business");
                }
            }
        });

        mBinding.buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (mBinding.editUserName.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),"Enter username",Toast.LENGTH_SHORT).show();
                }
                else if(mBinding.editUserName.getText().length()>=10){
                    Toast.makeText(getActivity(),"Invalid name",Toast.LENGTH_SHORT).show();
                }


                if (mBinding.editUserEmail.getText().toString().isEmpty()) {
                    Toast.makeText(getActivity(),"Enter email",Toast.LENGTH_SHORT).show();
                } else {
                    if (!Patterns.EMAIL_ADDRESS.matcher(mBinding.editUserEmail.getText().toString()).matches()) {
                        Toast.makeText(getActivity(),"Invalid email",Toast.LENGTH_SHORT).show();
                    }
                }


            }
        });

        return view;
    }

    public void startAnimation(final String selectedButton) {
        final View myView = getView().findViewById(R.id.revealiew);

        if (selectedButton.equalsIgnoreCase("customer")) {
            myView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorCustomerPrimary));

        } else {
            myView.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBusinessPrimary));
        }

        int[] viewLocation = new int[2];
        mBinding.stickySwitch.getLocationOnScreen(viewLocation);
        int cx = ((mBinding.stickySwitch.getWidth() / 2) + viewLocation[0]);
        int cy = ((mBinding.stickySwitch.getHeight() / 2) + viewLocation[1]);

        int finalRadius = Math.max(myView.getWidth(), myView.getHeight());

        Animator anim =
                ViewAnimationUtils.createCircularReveal(myView, cx, cy, 0, finalRadius);

        anim.setInterpolator(new AccelerateDecelerateInterpolator());
        anim.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                if (selectedButton.equalsIgnoreCase("customer")) {

                    mBinding.linearLayoutSignUp.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorCustomerPrimary));

                } else {
                    mBinding.linearLayoutSignUp.setBackgroundColor(ContextCompat.getColor(getActivity(), R.color.colorBusinessPrimary));
                }
            }
        });
        anim.setDuration(1000);
        myView.setVisibility(View.VISIBLE);
        anim.start();
    }
}
