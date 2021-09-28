package com.mynews.metronews.ui


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.mynews.metronews.R
import com.mynews.metronews.adapter.CategoryListAdapter
import com.mynews.metronews.model.Category
import kotlinx.android.synthetic.main.fragment_category.*

class CategoryFragment : Fragment() {

    private var arrayList: ArrayList<Category>? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_category, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        arrayList = ArrayList()
        arrayList!!.add(Category("Business", R.drawable.business))
        arrayList!!.add(Category("Technology", R.drawable.technology))
        arrayList!!.add(Category("Health", R.drawable.health))
        arrayList!!.add(Category("Sports", R.drawable.sports))
        arrayList!!.add(Category("Science", R.drawable.science))

        if(resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE){
            category_recycler_view.layoutManager = GridLayoutManager(context, 5)
            category_recycler_view.setHasFixedSize(true)
            category_recycler_view.adapter = CategoryListAdapter(requireContext(), arrayList!!)
        }
        else  if(resources.configuration.orientation == Configuration.ORIENTATION_PORTRAIT){
            category_recycler_view.layoutManager = GridLayoutManager(context, 3)
            category_recycler_view.setHasFixedSize(true)
            category_recycler_view.adapter = CategoryListAdapter(requireContext(), arrayList!!)
            }
        else{
            category_recycler_view.layoutManager = GridLayoutManager(context, 3)
            category_recycler_view.setHasFixedSize(true)
            category_recycler_view.adapter = CategoryListAdapter(requireContext(), arrayList!!)
        }
    }
}