package org.twinkletech.telikomai.myaccount

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.databinding.FragmentMyAccountBinding
import org.twinkletech.telikomai.databinding.FragmentTopupBinding

class MyAccountFragment : Fragment() {

    lateinit var binding: FragmentMyAccountBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentMyAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()


    }

    private fun setupSpinner() {
        val subscriptionNumbers = listOf(
            "675-770-66066",
            "675-880-77123",
            "675-991-33456"
        )

        val adapter = ArrayAdapter(
            requireActivity(),
            R.layout.spinner_item_selected,
            subscriptionNumbers
        ).apply {
            setDropDownViewResource(R.layout.spinner_item_dropdown)
        }

        binding.spinnerSubscription.adapter = adapter

        binding.spinnerSubscription.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>,
                    view: View?,
                    position: Int,
                    id: Long
                ) {

                }

                override fun onNothingSelected(parent: AdapterView<*>) {}
            }
    }
}