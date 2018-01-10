package eu.schoolproject.api;

import eu.schoolproject.api.model.IpInfo;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * Created by BP on 21/03/2017.
 */

public interface RestService {

    @GET("{ip}/json/")
    Call<IpInfo> requestIpInfo(@Path("ip") String ip);
}
