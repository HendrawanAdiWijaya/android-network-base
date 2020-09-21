package one.hendra.androidnetworkbase;

import android.content.Context;
import android.util.Log;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Locale;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private ServiceGenerator() {

    }

    public static <S> S createService(Class<S> serviceClass) {
        Retrofit retrofit = RetrofitNetwork.getRetrofit();
        if (retrofit == null)
            return null;
        return retrofit.create(serviceClass);
    }

}
