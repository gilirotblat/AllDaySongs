package gilir.gilifinalproject.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import gilir.gilifinalproject.data.dao.SongDao
import gilir.gilifinalproject.models.songsapi.AlbumConverter
import gilir.gilifinalproject.models.songsapi.ArtistConverter
import gilir.gilifinalproject.models.songsapi.Song


private const val DB_VERSION =3
private const val DB_NAME = "AppDataBase"

@Database(entities = [Song::class], version = DB_VERSION)
abstract class AppDataBase : RoomDatabase() {

    abstract fun songDao(): SongDao

    companion object {
        fun create(context: Context): AppDataBase =
            Room.databaseBuilder(context, AppDataBase::class.java, DB_NAME)
                .addTypeConverter(AlbumConverter())
                .addTypeConverter(ArtistConverter())
                .fallbackToDestructiveMigration()
                .build()
    }
}