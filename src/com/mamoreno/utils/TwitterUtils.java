package com.mamoreno.utils;

import oauth.signpost.OAuth;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.auth.AccessToken;
import android.content.SharedPreferences;
import android.util.Log;

public class TwitterUtils {

	public static void mandaTuit( String tuit, SharedPreferences prefs ){
		
    	AccessToken a = getAccessToken( prefs );
    	if( a!=null ){
	    	Twitter twitter = new TwitterFactory().getInstance();
	    	twitter.setOAuthConsumer(TwitterData.CONSUMER_KEY, TwitterData.CONSUMER_SECRET);
	    	twitter.setOAuthAccessToken(a);
	        try {
	        	//Looper.loop();
				twitter.updateStatus(tuit);
				Log.d("MGL", ""+ twitter.getScreenName().toString()		);
	
			} catch (TwitterException e) {
				Log.e("MGL","TwitterExc: " + e.getMessage() 	 );
			}   
    	}
	}
	
	public static boolean isAuthenticated( SharedPreferences prefs ){

		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
    	String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
    		
    	if( secret.equals("") || token.equals("") ) return false;
    	
		return true;
	}

	private static AccessToken getAccessToken( SharedPreferences prefs ){
		String token = prefs.getString(OAuth.OAUTH_TOKEN, "");
    	String secret = prefs.getString(OAuth.OAUTH_TOKEN_SECRET, "");
    		
    	if( secret.equals("") || token.equals("") ) return null;
    	Log.i("MGL", "TOKEN: " + token);
    	Log.i("MGL", "SECRET: " + secret);
    	
    	return new AccessToken( token, secret );
	}
	
}
