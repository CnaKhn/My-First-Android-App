package com.cnakhn.faradarscompletion;

import android.content.Context;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.cnakhn.faradarscompletion.Weather.TextViewWeather;
import com.cnakhn.faradarscompletion.Weather.WeatherFragment;
import com.cnakhn.faradarscompletion.Weather.WeatherInfo;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

public class ApiService {
    private static final String TAG = "ApiService";
    private static final String SAVE_USER_URL = "http://cnadev.000webhostapp.com/my_site/ApiService/SaveUser.php";
    private static final String LOGIN_USER_URL = "http://cnadev.000webhostapp.com/my_site/ApiService/LoginUser.php";
    private static Context context;
    private boolean success;
    private static final String API_KEY = "8e58dde2a4f2641dc4c69ff24e35b43c";
    private static final String URL = "https://api.openweathermap.org/data/2.5/weather?q=%s&units=metric&APPID=" + API_KEY;
    public static final int STATUS_FAILED = 0;
    public static final int STATUS_SUCCESS = 1;
    public static final int STATUS_EMAIL_EXISTS = 2;

    public TextView txtCity = WeatherFragment.txtCity;
    public TextView txtDetail = WeatherFragment.txtDetail;
    public TextView txtTemp = WeatherFragment.txtTemp;
    public TextView txtTempSign = WeatherFragment.txtTempSign;
    public TextViewWeather txtIcon = WeatherFragment.txtIcon;

    public ApiService(Context context) {
        ApiService.context = context;
    }

    public void saveUser(String username, String email, String password, final onSignUpResponse onSignupResponse) {
        JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put("username", username);
            requestJsonObject.put("email", email);
            requestJsonObject.put("password", password);

            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, SAVE_USER_URL, requestJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        int status = response.getInt("response");
                        onSignupResponse.onResponse(status);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    onSignupResponse.onResponse(STATUS_FAILED);
                }
            });

            request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(request);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public void loginUser(String email, String password, final onLoginResponse onLoginResponse) {
        JSONObject requestJsonObject = new JSONObject();
        try {
            requestJsonObject.put("email", email);
            requestJsonObject.put("password", password);
            JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST, LOGIN_USER_URL, requestJsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        boolean success = response.getBoolean("response");
                        onLoginResponse.onResponse(success);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            request.setRetryPolicy(new DefaultRetryPolicy(18000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
            Volley.newRequestQueue(context).add(request);
        } catch (JSONException e) {
            Log.i(TAG, "loginUser: " + e.toString());
        }

    }

    public boolean isSuccess() {
        return success;
    }

    public void requestWeatherData(final onWeatherInfoReceived onWeatherInfoReceived, String city) {

        String url = String.format(URL, city);
        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                onWeatherInfoReceived.onReceived(parseResponseToWeatherInfo(response));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                error.printStackTrace();
            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }

    private WeatherInfo parseResponseToWeatherInfo(JSONObject response) {
        final WeatherInfo weatherInfo = new WeatherInfo();
        try {

            JSONObject details = response.getJSONArray("weather").getJSONObject(0);
            weatherInfo.setCityName(response.getString("name") + ", " + response.getJSONObject("sys").getString("country").toUpperCase());
            weatherInfo.setCityDetail(details.getString("description"));
            weatherInfo.setCityTemp(response.getJSONObject("main").getDouble("temp"));

            JSONObject sys = response.getJSONObject("sys");
            weatherInfo.setWeatherID(details.getInt("id"));
            weatherInfo.setSunrise(sys.getLong("sunrise"));
            weatherInfo.setSunset(sys.getLong("sunset"));
            if (weatherInfo.getCityName().isEmpty() && weatherInfo.getCityDetail().isEmpty() && weatherInfo.getCityTemp() != 0) {
                txtCity.setText(weatherInfo.getCityName());
                txtDetail.setText(weatherInfo.getCityDetail());
                txtTemp.setText(String.format(Locale.US, "%.0f", weatherInfo.getCityTemp()));
                txtTempSign.setText(String.format(Locale.US, "%s", Html.fromHtml("&#8451;")));
                txtIcon.setWeatherIcon(weatherInfo.getWeatherID(), weatherInfo.getSunrise(), weatherInfo.getSunset());

            }


            return weatherInfo;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }
    }

    public interface onWeatherInfoReceived {
        void onReceived(WeatherInfo weatherInfo);
    }

    public interface onSignUpResponse {
        void onResponse(int status);
    }

    public interface onLoginResponse {
        void onResponse(boolean success);
    }
}
