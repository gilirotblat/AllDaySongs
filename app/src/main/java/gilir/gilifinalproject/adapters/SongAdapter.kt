package gilir.gilifinalproject.adapters


import android.content.Intent
import android.net.Uri
import android.system.Os.remove
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.squareup.picasso.Picasso
import gilir.gilifinalproject.R
import gilir.gilifinalproject.databinding.SongItemBinding
import gilir.gilifinalproject.models.songsapi.Song
import gilir.gilifinalproject.repository.Songs
import java.util.concurrent.TimeUnit

class SongAdapter(private var songs: Songs,
                  val onFavoriteClicked: (Song) -> Unit,
                  val isFavoritesAdapter : Boolean = false) :
    Adapter<SongAdapter.SongViewHolder>() {

    var updatePosition : Int = 0

    class SongViewHolder(val binding: SongItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SongViewHolder {
        val context = LayoutInflater.from(parent.context)
        val binding = SongItemBinding.inflate(context, parent, false)
        return SongViewHolder(binding)
    }


    private fun removeSong(song:Song, position:Int) {
        songs.songList.remove(song)
        notifyItemRemoved(position)
    }

    override fun onBindViewHolder(holder: SongViewHolder, position: Int) {
        val song = songs.songList[position]

        fun intToTime(duration: Int): String {
            val min = TimeUnit.SECONDS.toMinutes(duration.toLong())
            val sec = duration - TimeUnit.MINUTES.toSeconds((min))
            return String.format("%02d:%02d", min, sec)
        }

        val duration = song.artist.name + ", " + intToTime(song.duration)
        with(holder.binding) {
            tvSongName.text = song.title
            tvArtist.text = duration
            tvLink.setOnClickListener {
                val uri = Uri.parse(song.link)
                val intent = Intent(Intent.ACTION_VIEW, uri)
                startActivity(it.context, intent, null)
            }
            if (song.artist.picture != null) {
                Picasso.get().load(song.artist.picture).into(ivCover)
            } else
                Picasso.get().load(song.album.cover).into(ivCover)

            if (isFavoritesAdapter || songs.favoritesMapping[song.id] == true) {
                ivFavoriteBtn.setImageResource(R.drawable.ic_full_heart_favorite)
            } else {
                ivFavoriteBtn.setImageResource(R.drawable.ic_outline_favorite)
            }

            ivFavoriteBtn.setOnClickListener {
                if (songs.favoritesMapping[song.id] == true && isFavoritesAdapter) {
                        removeSong(song, position)
                }
                onFavoriteClicked(song)
                updatePosition = position

            }
        }
    }


    override fun getItemCount(): Int = songs.songList.size

    fun updateFavorites(it: List<Song>?) {
        val favoritesMapping = HashMap<Long,Boolean>()
        it?.forEach { song ->
            favoritesMapping[song.id] = true
        }
        songs.favoritesMapping = favoritesMapping
        notifyItemChanged(updatePosition)
    }

}