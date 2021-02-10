package com.example.boonduser;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;

public class MainActivity extends AppCompatActivity {

    EditText etName, etAddress, etOTP, etPhone;
    TextInputLayout tfName, tfAddress, tfOTP, tfPhone;
    Button btnLogin, btnSubmit, btnVerify, btnBackLogin, btnBackSubmit;
    TextView tvNoAccount;
    String verificationCodeSent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        loginView();
    }

    public void init()
    {
        etAddress = findViewById(R.id.etAddress);
        etName = findViewById(R.id.etName);
        etOTP = findViewById(R.id.etOTP);
        etPhone = findViewById(R.id.etPhone);

        btnLogin = findViewById(R.id.btnLogin);
        btnSubmit = findViewById(R.id.btnSubmit);
        btnVerify = findViewById(R.id.btnVerify);
        btnBackSubmit = findViewById(R.id.btnBackSubmit);
        btnBackLogin = findViewById(R.id.btnBackLogin);

        tfName = findViewById(R.id.tfName);
        tfAddress = findViewById(R.id.tfAddress);
        tfOTP = findViewById(R.id.tfOTP);
        tfPhone = findViewById(R.id.tfPhone);

        tvNoAccount = findViewById(R.id.tvNoAccount);
    }

    public void login (View v)
    {

        if(etPhone.getText().toString().trim().isEmpty())
        {
            etPhone.setError("Please Enter Your Number...");
            etPhone.requestFocus();
        }
        else {

            FirebaseDatabase.getInstance().getReference().child("User").orderByChild("Number").equalTo(etPhone.getText().toString().trim())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue() != null){
                                OTPForLogin();
                            }else {
                                etPhone.setError("Error: Number is not Registered...");
                                etPhone.requestFocus();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
            InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etPhone.getWindowToken(), 0);
            verifyPhone();
        }

    }


    public void add()
    {
        HashMap<String, String> data = new HashMap<>();
        data.put("Name",etName.getText().toString().trim());
        data.put("Address",etAddress.getText().toString().trim());
        data.put("Number",etPhone.getText().toString().trim());

        FirebaseDatabase.getInstance().getReference()
                .child("User")
                .child(etPhone.getText().toString().trim())
                .setValue(data)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(MainActivity.this, "User successfully created.", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    public void noAccount(View view)
    {

        etPhone.clearFocus();
        InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPhone.getWindowToken(), 0);

        btnLogin.setVisibility(View.GONE);
        tvNoAccount.setVisibility(View.GONE);
        btnBackSubmit.setVisibility(View.GONE);

        tfAddress.setVisibility(View.VISIBLE);
        tfName.setVisibility(View.VISIBLE);
        tfPhone.setVisibility(View.VISIBLE);

        btnSubmit.setVisibility(View.VISIBLE);
        btnBackLogin.setVisibility(View.VISIBLE);

    }

    public void loginView()
    {
        tfAddress.setVisibility(View.GONE);
        tfName.setVisibility(View.GONE);
        tfOTP.setVisibility(View.GONE);
        btnBackLogin.setVisibility(View.GONE);
        btnBackSubmit.setVisibility(View.GONE);

        btnVerify.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
    }

    public void loginView(View view)
    {
        InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPhone.getWindowToken(), 0);

        tfAddress.setVisibility(View.GONE);
        tfName.setVisibility(View.GONE);
        tfOTP.setVisibility(View.GONE);
        btnBackLogin.setVisibility(View.GONE);
        btnBackSubmit.setVisibility(View.GONE);

        btnVerify.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);

        btnLogin.setVisibility(View.VISIBLE);
        tvNoAccount.setVisibility(View.VISIBLE);
    }

    public void OTP(View view)
    {

        InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(etPhone.getWindowToken(), 0);
        if(etPhone.getText().toString().isEmpty())
        {
            etPhone.setError("Please Enter Your Number...");
            etPhone.requestFocus();
        }else if(etName.getText().toString().isEmpty()){
                etName.setError("Please Enter Your Name...");
                etName.requestFocus();
        }else if(etAddress.getText().toString().isEmpty())
        {
            etAddress.setError("Please Enter Your Address...");
            etAddress.requestFocus();
        }else{

            FirebaseDatabase.getInstance().getReference().child("User").orderByChild("Number").equalTo(etPhone.getText().toString().trim())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if(snapshot.getValue() != null)
                            {
                                etPhone.setError("Error: Number Already registered...");
                                etPhone.requestFocus();
                            }else{
                                tfAddress.setVisibility(View.GONE);
                                tfName.setVisibility(View.GONE);
                                btnSubmit.setVisibility(View.GONE);
                                btnBackLogin.setVisibility(View.GONE);
                                tfPhone.setVisibility(View.GONE);

                                tfOTP.setVisibility(View.VISIBLE);
                                btnVerify.setVisibility(View.VISIBLE);
                                btnBackSubmit.setVisibility(View.VISIBLE);

                                verifyPhone();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {
                            Toast.makeText(MainActivity.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
        }
    }

    public void OTPForLogin(){
        tfAddress.setVisibility(View.GONE);
        tfName.setVisibility(View.GONE);
        btnSubmit.setVisibility(View.GONE);
        btnBackLogin.setVisibility(View.GONE);
        tfPhone.setVisibility(View.GONE);
        btnLogin.setVisibility(View.GONE);
        tvNoAccount.setVisibility(View.GONE);

        tfOTP.setVisibility(View.VISIBLE);
        btnVerify.setVisibility(View.VISIBLE);
        btnBackSubmit.setVisibility(View.VISIBLE);

        verifyPhone();
    }

    public void verifyCode(View view)
    {
        if(etOTP.getText().toString().isEmpty())
        {
            etOTP.setError("Please Enter OTP...");
            etOTP.requestFocus();
        }else {
            verifyCode(etOTP.getText().toString().trim());
            InputMethodManager imm = (InputMethodManager)getSystemService(MainActivity.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(etPhone.getWindowToken(), 0);
        }
    }

    public void verifyPhone(){

        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder()
                        .setPhoneNumber("+92" + etPhone.getText().toString().trim())       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
    }

    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            verificationCodeSent = s;
        }

        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {
            String code = phoneAuthCredential.getSmsCode();
            if(code!=null){
                verifyCode(code);
            }
        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {
            Toast.makeText(MainActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    };

    private void verifyCode(String verificationCode){
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(verificationCodeSent, verificationCode);
        signInUserByCredentials(credential);
    }

    private void signInUserByCredentials(PhoneAuthCredential credential) {
        FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){
                            add();
                            startActivity();
                        }
                        else{
                            Toast.makeText(MainActivity.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    public void startActivity(){
        Intent intent = new Intent(MainActivity.this, MainView.class);
        intent.putExtra("Number", etPhone.getText().toString().trim());
        startActivity(intent);
    }

}