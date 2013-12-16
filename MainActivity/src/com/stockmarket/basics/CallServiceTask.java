package com.stockmarket.basics;


import java.io.ByteArrayOutputStream;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;

import android.os.AsyncTask;

public class CallServiceTask extends AsyncTask<Object, Void, String>
{
	RestClient caller;
	
	public CallServiceTask (RestClient l)
	{	
		this.caller = l;
	}
	@Override
	protected String doInBackground(Object... params) {
		HttpGet httpGet = new HttpGet((String)params[0]);
		HttpParams httpParameters = new BasicHttpParams();
		// Set the timeout in milliseconds until a connection is established.
		// The default value is zero, that means the timeout is not used. 
		int timeoutConnection = 10000;
		HttpConnectionParams.setConnectionTimeout(httpParameters, timeoutConnection);
		// Set the default socket timeout (SO_TIMEOUT) 
		// in milliseconds which is the timeout for waiting for data.
		int timeoutSocket = 10000;
		HttpConnectionParams.setSoTimeout(httpParameters, timeoutSocket);
		
		DefaultHttpClient httpClient = new DefaultHttpClient(httpParameters);
		HttpResponse response;
		try {
			response = httpClient.execute(httpGet);
			StatusLine statusLine = response.getStatusLine();
			if(statusLine.getStatusCode() == HttpStatus.SC_OK){
			    ByteArrayOutputStream out = new ByteArrayOutputStream();
			    response.getEntity().writeTo(out);
			    out.close();
			    String responseString = out.toString();
			    
			    
			    return responseString;
			} else{
			    //Closes the connection.
			    response.getEntity().getContent().close();
			    throw new IOException(statusLine.getReasonPhrase());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		
		//HttpResponse response = httpclient.execute(new HttpGet(url));
		
		return "";
	}
	
	protected void onPostExecute(String result) {
        (caller).onBackgroundTaskCompleted(result);
    }
}