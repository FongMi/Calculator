package com.fongmi.android.calculator;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.ksoap2.serialization.SoapObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

	@BindView(R.id.group) RadioGroup mGroup;
	@BindView(R.id.intA) EditText mIntA;
	@BindView(R.id.intB) EditText mIntB;

	private static final String NAMESPACE = "http://tempuri.org/";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		ButterKnife.bind(this);
	}

	@OnClick(R.id.calculate)
	public void onCalculate() {
		RadioButton button = mGroup.findViewById(mGroup.getCheckedRadioButtonId());
		new Task(getRequest(button), (String result) -> Toast.makeText(this, result, Toast.LENGTH_SHORT).show()).execute();
		hideKeyboard(mIntB);
	}

	private SoapObject getRequest(RadioButton button) {
		SoapObject request = new SoapObject(NAMESPACE, button.getText().toString());
		request.addProperty("intA", mIntA.getText().toString());
		request.addProperty("intB", mIntB.getText().toString());
		return request;
	}

	public void hideKeyboard(View view) {
		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
		IBinder windowToken = view.getWindowToken();
		if (imm != null && windowToken != null) {
			imm.hideSoftInputFromWindow(windowToken, 0);
		}
	}
}

