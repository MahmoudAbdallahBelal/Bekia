package bekia.recycle.web;

import bekia.recycle.requests.register.RegisterRequest;
import bekia.recycle.requests.register.RegisterResponse;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface ApiInterface {
    @GET("movie/top_rated")
    Call<RegisterResponse> getTopRatedMovies(@Query("api_key") String apiKey);



}
