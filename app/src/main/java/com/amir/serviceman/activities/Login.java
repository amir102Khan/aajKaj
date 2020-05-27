package com.amir.serviceman.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.amir.serviceman.R;
import com.amir.serviceman.core.BaseActivity;
import com.amir.serviceman.databinding.ActivityLoginBinding;
import com.amir.serviceman.util.Common;
import com.google.android.material.bottomsheet.BottomSheetBehavior;
import com.google.android.material.bottomsheet.BottomSheetDialog;

public class Login extends BaseActivity implements View.OnClickListener {

    private ActivityLoginBinding binding;
    private BottomSheetBehavior bottomSheetBehavior;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initialize();
        decorateText();
        implementListener();
    }

    private void initialize() {

        /* loader = (ConstraintLayout) binding.layoutLoader.getRoot();*/
        //  bottomSheetBehavior = BottomSheetBehavior.from(binding.bottomSheet.getRoo);
    }

    private void implementListener() {
        binding.btnLogin.setOnClickListener(this);
    }

    private void decorateText() {
        SpannableString ss = new SpannableString(getString(R.string.don_t_have_an_account_sign_up));
        ClickableSpan clickableSpan = new ClickableSpan() {
            @Override
            public void onClick(@NonNull View widget) {
                //  openBottomSheet();
                // openDaialog();
                startActivity(new Intent(mContext, CustomerSignUp.class)
                        .putExtra("type", getIntent().getStringExtra("type")));

            }

            @Override
            public void updateDrawState(@NonNull TextPaint ds) {
                super.updateDrawState(ds);
                ds.setUnderlineText(false);
            }
        };

        ss.setSpan(clickableSpan, 23, ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        ss.setSpan(new ForegroundColorSpan(ContextCompat.getColor(mContext, R.color.lightGreen)), 23,
                ss.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        binding.tvSignUp.setText(ss);
        binding.tvSignUp.setMovementMethod(LinkMovementMethod.getInstance());
        binding.tvSignUp.setHighlightColor(Color.TRANSPARENT);
    }

    @Override
    public void onClick(View v) {
        if (v == binding.btnLogin) {
            String phone = binding.userPhone.getText().toString();
            if (!Common.validateEditText(phone)) {
                showSnackBar(binding.getRoot(), "Phone number is empty");
            } else if (phone.length() < 10) {
                showSnackBar(binding.getRoot(), "Please provide phone number of 10 digit");
            } else {
                startActivity(new Intent(mContext, PhoneVerification.class)
                        .putExtra("phone", phone)
                        .putExtra("type",getIntent().getStringExtra("type"))
                        .putExtra(IS_REGISTER, false));
            }
        }
    }

    private void hideBottomSheet() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_EXPANDED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
        }
    }

    private void openBottomSheet() {
        if (bottomSheetBehavior.getState() == BottomSheetBehavior.STATE_COLLAPSED) {
            bottomSheetBehavior.setState(BottomSheetBehavior.STATE_EXPANDED);
        }
    }

    private void openDaialog() {
        final BottomSheetDialog dialog = new BottomSheetDialog(mContext);
        dialog.setContentView(R.layout.layout_bottom_sheet_signup_type);
        dialog.setCanceledOnTouchOutside(false);

        ImageView btnClose = dialog.findViewById(R.id.ivCross);
        Button consumer = dialog.findViewById(R.id.btn_client);
        Button provider = dialog.findViewById(R.id.btn_personal_trainer);

        consumer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        provider.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        dialog.show();
    }
}
