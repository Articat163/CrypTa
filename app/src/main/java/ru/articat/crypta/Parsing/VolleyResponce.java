package ru.articat.crypta.Parsing;


import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.android.volley.toolbox.StringRequest;

import java.util.HashMap;
import java.util.Map;

import static ru.articat.crypta.Settings.Constants.TAG;



public class VolleyResponce {
	private final RequestQueue mRequestQueue;
	private VolleyCallback vc;
	private String login, password;


	public VolleyResponce(Context c){
//		Context c1 = c;

		Cache cache = new DiskBasedCache(c.getCacheDir(), 1024 * 1024); // 1MB cap

// Set up the network to use HttpURLConnection as the HTTP client.

//        HttpsURLConnection.setDefaultSSLSocketFactory(new NoSSLv3Factory());

		Network network = new BasicNetwork(new HurlStack());

// Instantiate the RequestQueue with the cache and network.
//		mRequestQueue = new RequestQueue(cache, network);
//		mRequestQueue= Volley.newRequestQueue(c, new HurlStack(null, ClientSSLSocketFactory.getSocketFactory(c)));
        mRequestQueue= new RequestQueue(cache, network);

// Старт очереди
		mRequestQueue.start();
	}


	//***********************
	// 		простой запрос
	//***********************

	public void getSimpleResponce(String url, VolleyCallback vc){

		this.vc=vc;

		Log.i(TAG, "getSimpleResponce; url= "+ url);
// Formulate the request and handle the response.

		StringRequest request = new StringRequest(
            Request.Method.GET,
            url,
            listener,
            errorListener);

		request.setRetryPolicy(
				new DefaultRetryPolicy(
						DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*5, // 2500*5
						DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // 1
						DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		mRequestQueue.add(request);

    }


	//***********************
	//  запрос с авторизацией
	//***********************

	public void postAuthResponce(String url, final String login, final String password, VolleyCallback vc){

		Log.i(TAG, "postAuthResponce");
		this.login=login;
		this.password=password;
		this.vc=vc;

// Formulate the request and handle the response.
		StringRequest request = new StringRequest(
            Request.Method.POST,
            url,
            postListener,
            errorListener) {

			// добавляем параметры авторизации
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return createBasicAuthHeader(login, password);
			}


			// добавляем параметры запроса
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {

				Map<String, String> map = new HashMap<>();
				map.put("pas", password);
				map.put("logi", login);

				return map;
			}
		};


		request.setRetryPolicy(
			new DefaultRetryPolicy(
				DefaultRetryPolicy.DEFAULT_TIMEOUT_MS*5, // 2500*5
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // 1
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		mRequestQueue.add(request);

    }

// -------- end postAuthRequest --------
// ------------------------------------

	
	//***********************
	//  delete
	//***********************

	public void postDeleteResponce(String url, VolleyCallback vc){

		Log.i(TAG, "postAuthResponce");
	//	this.login=login;
	//	this.password=password;
		this.vc=vc;

// Formulate the request and handle the response.
		StringRequest request = new StringRequest(
            Request.Method.POST,
            url,
            postListener,
            errorListener) {

			// добавляем параметры авторизации
			@Override
			public Map<String, String> getHeaders() throws AuthFailureError {
				return createBasicAuthHeader(login, password);
			}


			// добавляем параметры запроса
			@Override
			protected Map<String, String> getParams() throws AuthFailureError {

				Map<String, String> map = new HashMap<>();
				map.put("userAgent", "Opera/9.80 (Windows NT 6.1; U; Edition Yx; ru) Presto/2.10.229 Version/11.64");
				map.put("referrer", "https://mirplus.info/?p=nmail");

				return map;
			}
		};


		request.setRetryPolicy(
			new DefaultRetryPolicy(
				DefaultRetryPolicy.DEFAULT_TIMEOUT_MS, // 2500
				DefaultRetryPolicy.DEFAULT_MAX_RETRIES, // 1
				DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));

		mRequestQueue.add(request);

    }

// -------- end postDeleteRequest --------
// ------------------------------------
	

	// получаем запрос из интернета GET
	private final Response.Listener<String> listener = new Response.Listener<String>() {
		@Override
		public void onResponse(String response) {
			Log.i(TAG, "listener.onResponse");
			vc.requestResult(response);
		}
	};

	// получаем запрос из интернета POST
	private final Response.Listener<String> postListener = new Response.Listener<String>() {
		@Override
		public void onResponse(String response) {
			Log.i(TAG, "postListener.onResponse");
			vc.requestComplete();
		}
	};


	// ошибка запроса
    private final Response.ErrorListener errorListener = new Response.ErrorListener() {
		@Override
		public void onErrorResponse(VolleyError error) {
			if (error.networkResponse != null) {
				Log.e(TAG, "Error Response code: " +  error.networkResponse.statusCode);
				vc.requestError(error.networkResponse.statusCode);
			}
		}
	};


	// авторизация
    private Map<String, String> createBasicAuthHeader(String login, String password) {
		Map<String, String> headerMap = new HashMap<String, String>();
		headerMap.put("User-agent", "Mozilla");

		return headerMap;
	}

	// интерфейс
	public interface VolleyCallback{
		void requestComplete();
		String requestResult(String res);
		int requestError(int res);

	}
}

