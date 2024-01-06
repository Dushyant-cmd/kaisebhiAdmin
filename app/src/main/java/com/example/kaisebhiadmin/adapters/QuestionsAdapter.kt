package com.example.kaisebhiadmin.adapters

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentManager
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.databinding.BottomSheetLayoutBinding
import com.example.kaisebhiadmin.databinding.ModalQuestionsBinding
import com.example.kaisebhiadmin.models.QuestionsModel
import com.example.kaisebhiadmin.ui.home.MainViewModel
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.ui.PlayerView
import com.google.android.exoplayer2.upstream.DefaultDataSource
import com.google.android.exoplayer2.util.MimeTypes
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class QuestionsAdapter(
    private val ctx: Context,
    private val viewModel: MainViewModel,
    private val fm: FragmentManager
) : PagingDataAdapter<QuestionsModel, QuestionsAdapter.ViewHolder>(COMPARATOR) {
    private val TAG = "QuestionsAdapter.kt"

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = DataBindingUtil.inflate<ModalQuestionsBinding>(
            LayoutInflater.from(parent.context),
            R.layout.modal_questions,
            parent,
            false
        )
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataObj = getItem(position)
        Log.d(TAG, "onBindViewHolder obj: $dataObj")
        dataObj?.let {
            holder.bind(dataObj)
        }
    }

    inner class ViewHolder(private val binding: ModalQuestionsBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(dataObj: QuestionsModel) {
            binding.username.text = dataObj.uname
            binding.quesDesc.text = dataObj.desc
            binding.quesTitle.text = dataObj.title
            binding.portalTV.text = dataObj.portal
            val picasso = Picasso.get()
            if (dataObj.image?.contains("http") == true)
                picasso.load(Uri.parse(dataObj.image)).into(binding.quesImage)
            if (dataObj.userPicUrl?.contains("http") == true)
                picasso.load(dataObj.userPicUrl).placeholder(R.drawable.profile)
                    .into(binding.userPro)

            Log.d(TAG, "onBindViewHolder qualityCheck: ${dataObj.qualityCheck}")
            when (dataObj.qualityCheck) {
                "fail" -> {
                    binding.failBtn.visibility = View.GONE
                    binding.passBtn.visibility = View.VISIBLE
                }

                "pass" -> {
                    binding.passBtn.visibility = View.GONE
                    binding.failBtn.visibility = View.VISIBLE
                }

                "pending" -> {
                    binding.failBtn.visibility = View.VISIBLE
                    binding.passBtn.visibility = View.VISIBLE
                }
            }

            if (dataObj.audio.isNullOrEmpty())
                binding.playBtn.visibility = View.GONE

            binding.playBtn.setOnClickListener {
                val sheet = PlayerBottomSheet(dataObj.audio!!)
                sheet.show(fm, "audio")
            }

            binding.passBtn.setOnClickListener {
                viewModel.updateQues(
                    dataObj.iD.toString(),
                    "pass",
                    position,
                    dataObj.qualityCheck!!
                )
            }

            binding.failBtn.setOnClickListener {
                viewModel.updateQues(
                    dataObj.iD.toString(),
                    "fail",
                    position,
                    dataObj.qualityCheck!!
                )
            }
        }
    }

    /**Below class is BottomSheetDialogFragment to display a sheet to play audio.  */
    class PlayerBottomSheet(private val downloadUrl: String) : BottomSheetDialogFragment() {
        private var exoPlayer: com.google.android.exoplayer2.ExoPlayer? = null
        private lateinit var binding: BottomSheetLayoutBinding
        private val isPlaying get() = exoPlayer?.isPlaying ?: false
        private val TAG = "PlayerBottomSheet.java"
        override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            saveInstanceState: Bundle?
        ): View? {
            binding =
                DataBindingUtil.inflate(inflater, R.layout.bottom_sheet_layout, container, false)
            initializePlayer(binding.playerView, downloadUrl)
            return binding.root
        }

        /**Below method is to play audio media using ExoPlayer library with its default controller
         * @param playerView xml view on which all the control or media related view will be displayed
         * @param downloadUrl it is a http protocol url to play dynamic media over internet.
         */
        private fun initializePlayer(playerView: PlayerView, audioUrl: String) {
            try {
                //Create ExoPlayer instance
                exoPlayer =
                    activity?.let { com.google.android.exoplayer2.ExoPlayer.Builder(it).build() }
                //Create a MediaItem
                val mediaItem = MediaItem.Builder()
                    .setUri(audioUrl)
                    .setMimeType(MimeTypes.AUDIO_AAC)
                    .build()
                //Create MediaSource and pass the media item
                val mediaSource = ProgressiveMediaSource.Factory(
                    DefaultDataSource.Factory(requireActivity())
                ).createMediaSource(mediaItem)

                //Attach mediaSource to exoPlayer and attach exoPlayer on PlayerView
                exoPlayer!!.apply {
                    setMediaSource(mediaSource)
                    playWhenReady = true //auto play when ready by decoding and getting resources
                    seekTo(0, 0L) //seek to start of media.
                    prepare()//Change state from idle
                }.also {
                    binding.playerView.player = it
                    binding.playerView.showController()
                    binding.playerView.controllerHideOnTouch = false
                    binding.playerView.controllerShowTimeoutMs = 0
                }

                Log.d(TAG, "initializePlayer is playing: $isPlaying")
            } catch (e: Exception) {
                Log.d(TAG, "setupAudio: $e")
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            exoPlayer!!.stop()
            exoPlayer!!.release()
            Log.d(TAG, "onDestroy: sheet destroyed")
        }
    }

    companion object {
        val COMPARATOR = object :
            DiffUtil.ItemCallback<QuestionsModel>() {
            override fun areContentsTheSame(
                oldItem: QuestionsModel,
                newItem: QuestionsModel
            ): Boolean {
                return oldItem == newItem
            }

            override fun areItemsTheSame(
                oldItem: QuestionsModel,
                newItem: QuestionsModel
            ): Boolean {
                return oldItem.iD == newItem.iD
            }
        }
    }
}