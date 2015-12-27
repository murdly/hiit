package com.bucket.akarbowy.hiit.view.fragments;

import android.app.Activity;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.widget.EditText;
import android.widget.TextView;

import com.bucket.akarbowy.hiit.R;
import com.bucket.akarbowy.hiit.base.BaseFragment;
import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

import java.util.List;

import butterknife.Bind;
import butterknife.OnClick;
import hugo.weaving.DebugLog;

/**
 * Created by akarbowy on 03.12.2015.
 */
public class SignUpFragment extends BaseFragment {

    public interface OnRegistrationActionListener {
        void onLogIn();

        void onRegister();

    }

    @Bind(R.id.log_in)
    TextView mLogIn;
    @Bind(R.id.register)
    TextView mRegister;
    @Bind({R.id.layout_username, R.id.layout_mail, R.id.layout_password, R.id.layout_password_repeat})
    List<TextInputLayout> mInputs;
    @Bind(R.id.signup_username)
    EditText mUsername;
    @Bind(R.id.signup_mail)
    EditText mMail;
    @Bind(R.id.signup_password)
    EditText mPassword;
    @Bind(R.id.signup_password_repeat)
    EditText mPasswordRepeat;

    private OnRegistrationActionListener mRegistrationActionListener;

    public static Fragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        if (activity instanceof OnRegistrationActionListener) {
            this.mRegistrationActionListener = (OnRegistrationActionListener) activity;
        }
    }

    @Override
    protected int getLayout() {
        return R.layout.fragment_sign_up;
    }

    @OnClick(R.id.log_in)
    public void navigateToLogIn() {
        mRegistrationActionListener.onLogIn();
    }

    @OnClick(R.id.register)
    public void register() {
        if (isFromValid() && isUserUnique())
            signUpUser();
        else
            showToastMessage(getString(R.string.error_msg_not_valid_form));
    }

    //todo enable mail verification
    //todo show progress dialog
    private void signUpUser() {
        ParseUser user = new ParseUser();
        user.setUsername(mUsername.getText().toString().trim());
        user.setEmail(mMail.getText().toString().trim());
        user.setPassword(mPassword.getText().toString().trim());

        user.signUpInBackground(new SignUpCallback() {
            @DebugLog
            @Override
            public void done(ParseException e) {
                if(e != null)
                    e.getCode();
                else{
                    mRegistrationActionListener.onRegister();
                }
                //todo error
                //todo success dialog -> login
            }
        });
    }

    /*todo fbug validate:
   * empty-> error -> type, clear -> no error
   * regex mail
   * regex pass (i.e. exclude space)
   */
    @DebugLog
    public boolean isFromValid() {
        return isFormFilled() && passwordsAreEqual();
    }

    //todo cloud code
    public boolean isUserUnique() {
        return true;
    }

    private boolean isFormFilled(){
        boolean valid = true;
        for (TextInputLayout input : mInputs)
            if (showErrorIfEmpty(input)) valid = false;

        return valid;
    }

    private boolean passwordsAreEqual() {
        return mPassword.getText().toString().equals(mPasswordRepeat.getText().toString());
    }

    @DebugLog
    private boolean showErrorIfEmpty(TextInputLayout input) {
        if (input.getEditText().getText().toString().isEmpty()) {
            input.setError(getString(R.string.fill_input));
            return true;
        }

        input.setErrorEnabled(false);
        return false;
    }
}
