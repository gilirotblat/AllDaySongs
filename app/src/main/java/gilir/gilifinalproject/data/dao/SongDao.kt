package gilir.gilifinalproject.data.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import gilir.gilifinalproject.models.songsapi.Song


@Dao
interface SongDao {


    //add
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSong(songsList: Song)


    //getters
    @Query("SELECT * FROM songs")
    fun listenAllSongs(): LiveData<List<Song>>

    //getters
    @Query("SELECT * FROM songs")
    suspend fun getAllSongs(): List<Song>

    //delete
    @Delete()
    suspend fun deleteSong(song: Song)

    // האם זה נחוץ ?
    @Update
    suspend fun updateSong(song: Song)




    //sorted
//
//    @Query("SELECT * FROM songs ORDER BY duration DESC")
//    fun songByDuration(): LiveData<List<FavoriteSong>>
//
//    @Query("SELECT * FROM songs ORDER BY title DESC")
//    fun songByTitle(): LiveData<List<FavoriteSong>>


}