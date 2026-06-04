package org.twinkletech.telikomai.topup

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.databinding.FragmentAIAssistantBinding
import org.twinkletech.telikomai.databinding.FragmentTopupBinding

class TopupFragment : Fragment() {

    lateinit var binding: FragmentTopupBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTopupBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_topupFragment_to_dashboardFragment)
        }
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