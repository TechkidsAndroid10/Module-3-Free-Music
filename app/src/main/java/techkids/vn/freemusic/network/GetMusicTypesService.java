package techkids.vn.freemusic.network;

import retrofit2.Call;
import retrofit2.http.GET;

/**
 * Created by Admins on 10/12/2017.
 */

public interface GetMusicTypesService {
    @GET("api")
    Call<MainObjectJSON> getMusicTypes();
}
