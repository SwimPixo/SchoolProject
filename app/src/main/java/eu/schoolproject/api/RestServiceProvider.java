package eu.schoolproject.api;

import android.content.Context;
import android.support.annotation.NonNull;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import eu.schoolproject.BuildConfig;
import eu.schoolproject.util.sl.IService;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by BP on 21/03/2017.
 */

public class RestServiceProvider implements IService {

    private final static String REST_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss";
    private final static int CONNECTION_TIMEOUT_DURATION = 5;

    private final @NonNull OkHttpClient mOkHttpClient;
    private final @NonNull RestService mService;
    private final @NonNull Retrofit mRetrofit;

    public RestServiceProvider(final @NonNull Context context) {
        mOkHttpClient = getOkHttpClient();

        mRetrofit = new Retrofit.Builder()
                .baseUrl("https://ipapi.co/")
                .client(mOkHttpClient)
                .addConverterFactory(GsonConverterFactory.create(getGson()))
                .build();

        mService = mRetrofit.create(RestService.class);
    }

    private OkHttpClient getOkHttpClient() {
        final HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor();
        OkHttpClient.Builder builder = new OkHttpClient.Builder();

        if (BuildConfig.DEBUG) {
            httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
            trustAllHosts(builder);
        }

       return builder
                .addInterceptor(httpLoggingInterceptor)
                .connectTimeout(CONNECTION_TIMEOUT_DURATION, TimeUnit.SECONDS)
                .readTimeout(CONNECTION_TIMEOUT_DURATION, TimeUnit.SECONDS)
                .build();
    }

    private Gson getGson() {
        return new GsonBuilder()
                .serializeNulls()
                .setDateFormat(REST_DATE_FORMAT)
                .create();
    }

    public RestService provide() {
        return mService;
    }

    public void cancelAll() {
        mOkHttpClient.dispatcher().cancelAll();
    }

    private void trustAllHosts(@NonNull OkHttpClient.Builder builder) {
        // Create a trust manager that does not validate certificate chains
        TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {

            @Override
            public void checkClientTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }

            @Override
            public void checkServerTrusted(java.security.cert.X509Certificate[] chain, String authType) throws java.security.cert.CertificateException {
            }

            @NonNull
            @Override
            public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                return new java.security.cert.X509Certificate[0];
            }
        }};

        try {
            SSLContext sc = SSLContext.getInstance("TLS"); //NON-NLS
            sc.init(null, trustAllCerts, null);
            builder.sslSocketFactory(sc.getSocketFactory());
            builder.hostnameVerifier(new HostnameVerifier() {
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
