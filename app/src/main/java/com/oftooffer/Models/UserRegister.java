package com.oftooffer.Models;

import android.content.Context;
import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.databinding.ObservableField;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

import com.oftooffer.BR;
import com.oftooffer.R;
import com.oftooffer.databinding.CustomerBinding;

/**
 * Created by dsk-221 on 30/3/17.
 */

public class UserRegister extends BaseObservable {
    private String userEmail;
    private String userName;
    private String userMono;
    private String userPass;
    private Boolean signUp;
    private CustomerBinding mBinding;
    private Context mContext;

    public ObservableField<String> nameError = new ObservableField<>();
    public ObservableField<String> emailError = new ObservableField<>();
    public ObservableField<String> passwordError = new ObservableField<>();

    RegisterCallBack registerCallBack;

    public interface RegisterCallBack {
        void register(String name, String email, String password);

        void login(String email, String password);

    }
    public void setRegisterCallBack(RegisterCallBack registerCallBack) {
        this.registerCallBack = registerCallBack;
    }

    public UserRegister(Context mContext, CustomerBinding mBinding) {
        this.mBinding = mBinding;
        this.mContext = mContext;
    }

    @Bindable
    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
        notifyPropertyChanged(BR.userEmail);
        emailError.set("");
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        nameError.set("");
        notifyPropertyChanged(BR.userName);
    }

    public String getUserMono() {
        return userMono;
    }

    public void setUserMono(String userMono) {
        this.userMono = userMono;
    }

    @Bindable
    public String getUserPass() {
        return userPass;
    }

    public void setUserPass(String userPass) {
        this.userPass = userPass;
        passwordError.set("");
        notifyPropertyChanged(BR.userPass);
    }

    @Bindable
    public Boolean getSignUp() {
        return signUp;
    }

    public void setSignUp(Boolean signUp) {
        this.signUp = signUp;
        notifyPropertyChanged(BR.signUp);

        mBinding.inputLayoutEmail.setErrorEnabled(false);
        mBinding.inputLayoutName.setErrorEnabled(false);
        mBinding.inputLayoutPassword.setErrorEnabled(false);
    }

    public void onRegisterClick(View view, Boolean isSignUp) {
        setUserName(null);
        setUserEmail(null);
        setUserPass(null);

        hideKeyBoard(view);

        if (isSignUp) {
            mBinding.editUserName.requestFocus();
        } else {
            mBinding.editUserEmail.requestFocus();
        }
        setSignUp(isSignUp);
    }

    public void onRegister(View view) {
        hideKeyBoard(view);
        if (checkValidName() && checkValidEmail() && checkValidPassword()) {
            registerCallBack.register(getUserName(), getUserEmail(), getUserPass());
        }
    }

    public void onLogin(View view) {
        hideKeyBoard(view);
        if (checkValidEmail() && checkValidPassword()) {
            registerCallBack.login(getUserEmail(), getUserPass());
        }
    }
    private boolean checkValidName() {
        if (mBinding.editUserName.getText().toString().isEmpty()) {
            nameError.set(mContext.getString(R.string.error_enter_name));
            mBinding.inputLayoutName.setError(nameError.get());
            return false;
        } else if (mBinding.editUserName.getText().length() >= 10) {
            nameError.set(mContext.getString(R.string.error_name));
            mBinding.inputLayoutName.setError(nameError.get());
            return false;
        } else {
            nameError.set("");
            return true;
        }
    }

    private boolean checkValidEmail() {

        if (mBinding.editUserEmail.getText().toString().isEmpty()) {
            emailError.set(mContext.getString(R.string.error_enter_email));
            mBinding.inputLayoutEmail.setError(emailError.get());
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(mBinding.editUserEmail.getText().toString()).matches()) {
            emailError.set(mContext.getString(R.string.error_email));
            mBinding.inputLayoutEmail.setError(emailError.get());
            return false;
        } else {
            emailError.set("");
            return true;
        }
    }

    private boolean checkValidPassword() {
        if (mBinding.editUserPassword.getText().toString().isEmpty()) {
            passwordError.set(mContext.getString(R.string.error_enter_password));
            mBinding.inputLayoutPassword.setError(passwordError.get());
            return false;
        } else if (mBinding.editUserPassword.getText().toString().length() != 8) {
            passwordError.set(mContext.getString(R.string.error_password));
            mBinding.inputLayoutPassword.setError(passwordError.get());
            return false;
        } else {
            passwordError.set("");
            return true;
        }
    }
    private void hideKeyBoard(View view) {
        InputMethodManager im = (InputMethodManager) mContext.getSystemService(Context.INPUT_METHOD_SERVICE);
        im.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }
}
