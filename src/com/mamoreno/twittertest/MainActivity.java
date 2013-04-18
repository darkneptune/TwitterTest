package com.mamoreno.twittertest;

import java.util.Date;

import com.mamoreno.utils.TwitterData;

import oauth.signpost.OAuthProvider;
import oauth.signpost.basic.DefaultOAuthProvider;
import oauth.signpost.commonshttp.CommonsHttpOAuthConsumer;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends Activity {

	private static CommonsHttpOAuthConsumer httpOauthConsumer;
	private static OAuthProvider httpOauthprovider;
	private Button btnOAuth;
	private TextView codigoRespuesta;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		this.btnOAuth = (Button)findViewById(R.id.btnOAuth);
		this.codigoRespuesta = (TextView)findViewById(R.id.codigoRespuesta);
		
		this.btnOAuth.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				autorizarApp();
		    	Log.i("MGL", "depues de mandar");
			}
		});
	}
	

	/**
	 * Called when the OAuthRequestTokenTask finishes (user has authorized the
	 * request token). The callback URL will be intercepted here.
	 */
	@Override
	public void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		final Uri uri = intent.getData();
		SharedPreferences preferencias = this.getSharedPreferences("TwitterPrefs", MODE_PRIVATE);
	
		if (uri != null && uri.toString().indexOf(TwitterData.CALLBACK_URL) != -1) {
			Log.i("MGL", "Callback received : " + uri);
		
			new RetrieveAccessTokenTask(this, getConsumer(), getProvider(),
					preferencias).execute(uri);
		}
	}

	protected void autorizarApp() {
		try {

			getProvider().setOAuth10a(true);
			// retrieve the request token
			new OAuthRequestTokenTask(this, getConsumer(), getProvider()).execute();

		} catch (Exception e) {
			codigoRespuesta.setText(e.getMessage());
		}		
	}
	
	
	/**
	 * @return the provider (initialize on the first call)
	 */
	public static OAuthProvider getProvider() {
		if (httpOauthprovider == null) {
			httpOauthprovider = new DefaultOAuthProvider(
					TwitterData.REQUEST_URL, TwitterData.ACCESS_URL,
					TwitterData.AUTHORIZE_URL);
			httpOauthprovider.setOAuth10a(true);
		}
		return httpOauthprovider;
	}

	/**
	 * @param context
	 *            the context
	 * @return the consumer (initialize on the first call)
	 */
	public static CommonsHttpOAuthConsumer getConsumer() {
		if (httpOauthConsumer == null) {
			httpOauthConsumer = new CommonsHttpOAuthConsumer(
					TwitterData.CONSUMER_KEY, TwitterData.CONSUMER_SECRET);
		}
		return httpOauthConsumer;
	}

}
