package com.agstudio.tennisdata

import androidx.recyclerview.widget.RecyclerView
import android.view.ViewGroup

import android.view.LayoutInflater
import com.agstudio.tennisdata.databinding.SetsRecyclerviewItemBinding

class SetsRecyclerViewAdapter(private var mList: ArrayList<String>) : RecyclerView.Adapter<SetsRecyclerViewAdapter.MyViewHolder>() {

    inner class MyViewHolder(private val binding: SetsRecyclerviewItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItem(text: String) {
            binding.SetPointsTextView.text = text
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(SetsRecyclerviewItemBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bindItem(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }


//    // create new views
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//        // inflates the card_view_design view
//        // that is used to hold list item
//        val inflater = LayoutInflater.from(parent.context)
//        .inflate(R.layout., parent, false)
//
//        return ViewHolder(view)
//    }
//
//    // binds the list items to a view
//    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//
//        val ItemsViewModel = mList[position]
//
//        // sets the image to the imageview from our itemHolder class
//        holder.imageView.setImageResource(ItemsViewModel.image)
//
//        // sets the text to the textview from our itemHolder class
//        holder.textView.text = ItemsViewModel.text
//
//    }
//
//    // return the number of the items in the list
//    override fun getItemCount(): Int {
//        return mList.size
//    }
//
//    // Holds the views for adding it to image and text
//    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
//        val imageView: ImageView = itemView.findViewById(R.id.imageview)
//        val textView: TextView = itemView.findViewById(R.id.textView)
//    }

//    private val mData = ArrayList<String>()
//    var binding: SetsRecyclerviewItemBinding? = null
//
//    inner class MyViewHolder constructor(view: View) : RecyclerView.ViewHolder(view) {
//        var myTextView = binding?.SetPointsTextView
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
//        val inflater = LayoutInflater.from(parent.context)
//        binding = SetsRecyclerviewItemBinding.inflate(inflater, parent, false)
//        binding?.let {
//            return MyViewHolder(it.SetPointsTextView)
//        }
//        return MyViewHolder(View(parent.context))
//    }
//
//    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
//        val text = mData[position]
//        holder.myTextView?.text = text
//    }
//
//    override fun getItemCount(): Int {
//        return mData.size
//    }
//
//
//
    fun addAll(list: List<String>?) {
        if(list != null) {
            mList.clear()
            mList.addAll(list)
            mList.forEachIndexed{ index, _ ->
                notifyItemChanged(index)
            }
        }
    }

}