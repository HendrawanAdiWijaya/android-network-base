package one.hendra.androidnetworkbase;

import android.util.Log;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;

import okhttp3.Authenticator;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.Route;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitNetwork {

    private static FieldNamingStrategy sFieldNamingStrategy = new FieldNamingStrategy() {
        @Override
        public String translateName(Field f) {
            Log.d("Before", f.getName());
            String name = f.getName();
            if (f.getModifiers() == Modifier.PRIVATE && f.getName().charAt(0) == 'm')
                name = f.getName().substring(1);
            String result = separateCamelCase(name, "_").toLowerCase(Locale.ENGLISH);
            Log.d("After", result);
            return result;
        }
    };

    static String separateCamelCase(String name, String separator) {
        StringBuilder translation = new StringBuilder();
        for (int i = 0, length = name.length(); i < length; i++) {
            char character = name.charAt(i);
            if (Character.isUpperCase(character) && translation.length() != 0 && i != 0) {
                translation.append(separator);
            }
            translation.append(character);
        }
        return translation.toString();
    }

    private static Gson sGson = new GsonBuilder()
            .setFieldNamingStrategy(sFieldNamingStrategy)
            .create();

    private static Retrofit retrofit;

    private static HttpLoggingInterceptor logging = new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY);

    private static OkHttpClient.Builder httpClient =
            new OkHttpClient.Builder();

    private static AuthenticatorInterceptor authenticatorInterceptor;

    private RetrofitNetwork(){

    }

    public static void init(String baseUrl, String token){
        if (!httpClient.interceptors().contains(logging)) {
            httpClient.addInterceptor(logging);
        }

        if (authenticatorInterceptor==null){
            authenticatorInterceptor = new AuthenticatorInterceptor(token);
            httpClient.addInterceptor(authenticatorInterceptor);
        }

        retrofit = new Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create(sGson))
                .client(httpClient.build())
                .baseUrl(baseUrl).build();
    }

    public static void setToken(String token){
        authenticatorInterceptor.setToken(token);
    }

    public static Retrofit getRetrofit(){
        return retrofit;
    }

}
