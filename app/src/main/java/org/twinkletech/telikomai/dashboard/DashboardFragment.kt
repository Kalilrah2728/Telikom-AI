package org.twinkletech.telikomai.dashboard

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.databinding.FragmentDashboardBinding
import org.twinkletech.telikomai.databinding.FragmentLoginBinding


class DashboardFragment : Fragment() {

    lateinit var binding: FragmentDashboardBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDashboardBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.addSubNumber.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_AIAssistantFragment)

        }

        binding.myPlan.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_plansFragment)

        }

        binding.topup.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_topupFragment)

        }

        binding.imageViewNoti.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_notificationFragment)

        }

        binding.myAccount.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_myAccountFragment)

        }

        binding.updateProfile.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_whatsAppFragment)

        }

        binding.specials.setOnClickListener {
            findNavController().navigate(R.id.action_dashboardFragment_to_storeLocaterFragment)

        }
    }
}