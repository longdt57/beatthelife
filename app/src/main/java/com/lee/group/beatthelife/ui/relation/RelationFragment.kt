package com.lee.group.beatthelife.ui.relation

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.lee.group.beatthelife.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class RelationFragment : Fragment() {

    private val relationViewModel: RelationViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_relation, container, false)
        val textView: TextView = root.findViewById(R.id.text_notifications)
        relationViewModel.text.observe(
            viewLifecycleOwner,
            {
                textView.text = it
            }
        )
        return root
    }
}
