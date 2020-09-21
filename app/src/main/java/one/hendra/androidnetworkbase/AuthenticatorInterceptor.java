package one.hendra.androidnetworkbase;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthenticatorInterceptor implements Interceptor{

    private String mToken;

    public AuthenticatorInterceptor(String token) {
        mToken = token;
    }

    public String getToken() {
        return mToken;
    }

    public void setToken(String token) {
        mToken = token;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request original = chain.request();

        Request request = original.newBuilder()
                .header("Authorization", "Bearer " + mToken)
                .method(original.method(), original.body())
                .build();

        return chain.proceed(request);
    }

}
