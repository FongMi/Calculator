package com.fongmi.android.calculator;

import android.os.AsyncTask;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

public class Task extends AsyncTask<String, Void, String> {

	private static final String URL = "http://www.dneonline.com/calculator.asmx?wsdl";

	private SoapObject request;
	private Callback callback;

	Task(SoapObject request, Callback callback) {
		this.request = request;
		this.callback = callback;
	}

	@Override
	protected String doInBackground(String... strings) {
		try {
			SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(SoapEnvelope.VER11);
			envelope.setOutputSoapObject(request);
			envelope.dotNet = true;
			new HttpTransportSE(URL).call(request.getNamespace() + request.getName(), envelope);
			return envelope.getResponse().toString();
		} catch (Exception e) {
			return "";
		}
	}

	@Override
	protected void onPostExecute(String s) {
		callback.onResult(s);
	}

	public interface Callback {

		void onResult(String result);
	}
}

