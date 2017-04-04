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
        mBinding.stickySwitch.setLeftIcon(R.drawable.ic_people_black);
        mBinding.stickySwitch.setRightIcon(R.drawable.ic_domain_black);

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

                checkValidName();
                checkValidEmail();
                checkValidPassword();
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

    public void checkValidName() {
        if (mBinding.editUserName.getText().toString().isEmpty()) {
           // mBinding.editUserName.setError("Enter User name");
            mBinding.inputLayoutName.setError("Enter User Name");
        } else if (mBinding.editUserName.getText().length() >= 10) {

            mBinding.inputLayoutName.setError("User name must be less than 10 character");
        }
        else {
            mBinding.inputLayoutName.setError(null);
        }
    }

    public void checkValidEmail() {

        if (mBinding.editUserEmail.getText().toString().isEmpty()) {
            mBinding.inputLayoutEmail.setError("Enter Email");
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mBinding.editUserEmail.getText().toString()).matches()) {
            mBinding.inputLayoutEmail.setError("Invalid Email");
        }
        else{
            mBinding.inputLayoutEmail.setError(null);
        }

    }

    public void checkValidPassword() {
        if (mBinding.editUserPassword.getText().toString().isEmpty()) {
            mBinding.inputLayoutPassword.setError("Enter Password");
        } else if (mBinding.editUserPassword.getText().toString().length() != 8) {
            mBinding.inputLayoutPassword.setError("Pass word must be equal to 8 character");
        }
        else{
            mBinding.inputLayoutPassword.setError(null);
        }
    }
}
