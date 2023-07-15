package gilir.gilifinalproject.service

import gilir.gilifinalproject.models.artistapi.ArtistsResponse
import gilir.gilifinalproject.models.playlistApi.PlaylistResponse
import gilir.gilifinalproject.models.songsapi.SongResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path



interface AppService {

    //songs: https://api.deezer.com/chart/0/tracks

    //artists : https://api.deezer.com/chart/0/artists

    // playlist new :https://api.deezer.com/radio/lists


    @GET("chart/0/tracks")
    suspend fun getHitsSongs(): SongResponse

    @GET ("chart/0/artists")
    suspend fun getArtists () : ArtistsResponse

    @GET("radio/lists")
    suspend fun getPlaylist (): PlaylistResponse


    @GET("radio/{PLAYLIST_ID}/tracks")
   suspend fun getTracklistPlaylist(@Path("PLAYLIST_ID") playListId : String):SongResponse


    @GET("artist/{ARTIST_ID}/top?limit=50")
    suspend fun getArtistSongs(@Path("ARTIST_ID") artistId : Long):SongResponse


    companion object {
            fun create(): AppService {
                return Retrofit.Builder()
                    .baseUrl("https://api.deezer.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
                    .create(AppService::class.java)
            }
    }
}