package com.oftooffer.Fragments;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.Activity;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.Toast;
import com.oftooffer.Models.UserRegister;
import com.oftooffer.R;
import com.oftooffer.databinding.CustomerBinding;
import org.jetbrains.annotations.NotNull;
import io.codetail.animation.ViewAnimationUtils;
import io.ghyeok.stickyswitch.widget.StickySwitch;

/**
 * Created by dsk-221 on 31/3/17.
 */

public class CustomerRegistrationFragment extends Fragment implements UserRegister.RegisterCallBack{

    private UserRegister userRegister;
    private CustomerBinding mBinding;
    public boolean isCustomer=true;

    public CustomerRegistrationFragment() {
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        mBinding = DataBindingUtil.inflate(
                inflater, R.layout.fragment_customer_regestration, container, false);
        userRegister = new UserRegister(getActivity(),mBinding);
        userRegister.setSignUp(true);
        mBinding.setUserRegister(userRegister);
        View view = mBinding.getRoot();

        final Typeface face = Typeface.createFromAsset(getActivity().getAssets(), "font.ttf");
        mBinding.textTitle.setTypeface(face);
        mBinding.stickySwitch.setLeftIcon(R.drawable.ic_people_black);
        mBinding.stickySwitch.setRightIcon(R.drawable.ic_domain_black);

        userRegister.setRegisterCallBack(this);

        mBinding.stickySwitch.setOnSelectedChangeListener(new StickySwitch.OnSelectedChangeListener() {
            @Override
            public void onSelectedChange(@NotNull StickySwitch.Direction direction, @NotNull String s) {
                if (direction == StickySwitch.Direction.LEFT) {
                    startAnimation("customer");
                    isCustomer=true;
                } else {
                    startAnimation("business");
                    isCustomer=false;
                }
            }
        });
        mBinding.textForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showForgotPasswordDialog();
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

    @Override
    public void register(String name, String email, String password) {
        if (isCustomer) {
            Toast.makeText(getActivity(), "customer" + " " + name + " " + email + " " + password, Toast.LENGTH_SHORT).show();
        } else
                Toast.makeText(getActivity(), "businessman" + " " + name + " " + email + "" + password, Toast.LENGTH_SHORT).show();
        }

    @Override
    public void login(String email, String password) {

    }
    public void showForgotPasswordDialog(){
        AlertDialog.Builder builder ;
        builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = (getActivity()).getLayoutInflater();
        View v = inflater.inflate(R.layout.dialog_forgot_password, null);
        builder.setView(v);
        builder
                .setTitle(R.string.text_forgot_password)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // continue with delete
                    }
                })
                .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        // do nothing
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder.create();
        alert11.show();
    }
}