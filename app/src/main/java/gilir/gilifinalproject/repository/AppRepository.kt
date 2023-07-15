package gilir.gilifinalproject.repository


import gilir.gilifinalproject.data.dao.SongDao
import gilir.gilifinalproject.models.songsapi.Song
import gilir.gilifinalproject.service.AppService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext


data class Songs(
    var songList : MutableList<Song>,
    var favoritesMapping: HashMap<Long,Boolean>
)

class AppRepository(private val songDao: SongDao) {


    suspend fun getSongs(): Songs = withContext(Dispatchers.IO) {
        val songs = AppService.create().getHitsSongs().songs
        val favoriteSongs = songDao.getAllSongs()
        var favoritesMapping = HashMap<Long,Boolean>()
        favoriteSongs.forEach { song ->
            favoritesMapping[song.id] = true
        }
        Songs(songs.toMutableList(), favoritesMapping)
    }


    suspend fun getFavoriteSongs() = songDao.getAllSongs()
    fun getFavoriteSongsLive() = songDao.listenAllSongs()

    suspend fun addSongToFavorites(song: Song) {
        withContext(Dispatchers.IO) {
            songDao.addSong(song)
        }
    }

    suspend fun deleteSong(song: Song){
        withContext(Dispatchers.IO){
            songDao.deleteSong(song)
        }
    }


    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            //  val songs = AppService.create().getSongs().songs
            // songDao.addAllSongs(songs)


        }
    }


}



