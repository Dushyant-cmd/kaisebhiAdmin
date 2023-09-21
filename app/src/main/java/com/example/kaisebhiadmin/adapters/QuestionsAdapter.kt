package com.example.kaisebhiadmin.adapters

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.FragmentManager
import androidx.media3.exoplayer.SimpleExoPlayer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.kaisebhiadmin.R
import com.example.kaisebhiadmin.models.QuestionsModel
import com.example.kaisebhiadmin.ui.home.MainViewModel
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.squareup.picasso.Picasso

class QuestionsAdapter(
    private val list: ArrayList<QuestionsModel>,
    private val ctx: Context,
    private val viewModel: MainViewModel,
    private val fm: FragmentManager
) : ListAdapter<QuestionsModel, QuestionsAdapter.ViewHolder>(object :
    DiffUtil.ItemCallback<QuestionsModel>() {
    override fun areContentsTheSame(oldItem: QuestionsModel, newItem: QuestionsModel): Boolean {
        return oldItem == newItem
    }

    override fun areItemsTheSame(oldItem: QuestionsModel, newItem: QuestionsModel): Boolean {
        return oldItem.iD == newItem.iD
    }
}) {
    private val TAG = "QuestionsAdapter.kt"
    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.modal_questions, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        try {
            val dataObj = getItem(position)
            Log.d(TAG, "onBindViewHolder: $dataObj")
            holder.userName.text = dataObj.uname
            holder.desc.text = dataObj.desc
            holder.title.text = dataObj.title
            Log.d(TAG, "onBindViewHolder: ${dataObj.image}")
            val picasso = Picasso.get()
            picasso.load(Uri.parse(dataObj.image)).into(holder.quesImg)
            picasso.load(dataObj.userPicUrl).placeholder(R.drawable.profile).into(holder.userImage)

            Log.d(TAG, "onBindViewHolder asdasdf: ${dataObj.qualityCheck}")
            if (dataObj.qualityCheck == "fail") {
                holder.failBtn.visibility = View.GONE
                holder.passBtn.visibility = View.VISIBLE
            } else if (dataObj.qualityCheck == "pass") {
                holder.passBtn.visibility == View.GONE
                holder.failBtn.visibility = View.VISIBLE
            } else {
                holder.failBtn.visibility = View.VISIBLE
                holder.passBtn.visibility = View.VISIBLE
            }
            holder.audioBtn.setOnClickListener {
                val sheet = PlayerBottomSheet(dataObj.audio!!)
                sheet.show(fm, "audio")
            }

            holder.passBtn.setOnClickListener {
                viewModel.updateQues(dataObj.iD.toString(), "pass")
            }

            holder.failBtn.setOnClickListener {
                viewModel.updateQues(dataObj.iD.toString(), "fail")
            }
        } catch (e: Exception) {
            Log.d(TAG, "onBindViewHolder: $e")
        }
    }

    /**Below class is BottomSheetDialogFragment to display a sheet to play audio.  */
    class PlayerBottomSheet(private val downloadUrl: String) : BottomSheetDialogFragment() {
        private lateinit var exoPlayer: SimpleExoPlayer
        private val TAG = "PlayerBottomSheet.java"
        override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?, saveInstanceState: Bundle?
        ): View? {
            val view = inflater.inflate(R.layout.bottom_sheet_layout, container, false)
//            setupAudio(view.findViewById(), downloadUrl)
            return view
        }

        /**Below method is to play audio media using ExoPlayer library with its default controller
         * @param playerView xml view on which all the control or media related view will be displayed
         * @param downloadUrl it is a http protocol url to play dynamic media over internet.
         */
        private fun setupAudio(playerView: SimpleExoPlayer, downloadUrl: String) {
            try {

            } catch (e: Exception) {
                Log.d(TAG, "setupAudio: $e")
            }
        }

        override fun onDestroy() {
            super.onDestroy()
            Log.d(TAG, "onDestroy: sheet destroyed")
        }
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val userImage = view.findViewById<ImageView>(R.id.userPro)
        val title = view.findViewById<TextView>(R.id.quesTitle)
        val desc = view.findViewById<TextView>(R.id.quesDesc)
        val quesImg = view.findViewById<ImageView>(R.id.quesImage)
        val audioBtn = view.findViewById<Button>(R.id.playBtn)
        val passBtn = view.findViewById<Button>(R.id.passBtn)
        val failBtn = view.findViewById<Button>(R.id.failBtn)
        val userName = view.findViewById<TextView>(R.id.username)
    }
}