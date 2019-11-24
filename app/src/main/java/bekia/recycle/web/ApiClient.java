package bekia.recycle.web;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiClient {
    public static final String BASE_URL = "http://urgoship.com/olx/api/";
    public static final String IMAGES_URL ="http://urgoship.com/olx/uploads/categories/";
    private static Retrofit retrofit = null;


    public static Retrofit getClient() {
        if (retrofit==null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }


   public static Interceptor  headerAuthorizationInterceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            okhttp3.Request request = chain.request();
            Headers headers = request.headers().newBuilder()
            .build();
            request = request.newBuilder().headers(headers).build();
            return chain.proceed(request);
        }
    };
  public  static OkHttpClient okHttpClient = new OkHttpClient().newBuilder()
            .connectTimeout(100, TimeUnit.SECONDS)
            .readTimeout(100, TimeUnit.SECONDS)
            .writeTimeout(100, TimeUnit.SECONDS)
          .addInterceptor(headerAuthorizationInterceptor)
            .build();


}
