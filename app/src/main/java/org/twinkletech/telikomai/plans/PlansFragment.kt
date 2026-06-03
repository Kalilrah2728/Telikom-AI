package org.twinkletech.telikomai.plans

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.selfcaretelikomaidemoscreen.Plan
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.databinding.FragmentPlansBinding


class PlansFragment : Fragment() {

    lateinit var binding: FragmentPlansBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPlansBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupSpinner()
        setupRecyclerView()
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

    private fun setupRecyclerView() {
        val plans = listOf(
            Plan("K1 Night Data", "1GB valid for 1 day", "K 1"),
            Plan("K3, 1GB – 1 Day", "1GB valid for 1 day", "K 3"),
            Plan("CM K5 Data Plan", "2GB valid for 30 days", "K 5"),
            Plan("K10 Weekend Pack", "5GB valid for 3 days", "K 10"),
            Plan("K15 Weekly Data", "8GB valid for 7 days", "K 15"),
            Plan("K25 Fortnight", "15GB valid for 14 days", "K 25"),
            Plan("K50 Monthly", "30GB valid for 30 days", "K 50"),
            Plan("K5 Social Pack", "Unlimited Facebook/WhatsApp 7 days", "K 5"),
        )

        val adapter = PlansAdapter(plans) { plan ->
            Toast.makeText(requireContext(), "Selected: ${plan.name}", Toast.LENGTH_SHORT).show()
        }

        binding.recyclerPlans.layoutManager = LinearLayoutManager(requireContext())
        binding.recyclerPlans.adapter = adapter
        binding.recyclerPlans.isNestedScrollingEnabled = false
    }

}