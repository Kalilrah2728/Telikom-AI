package org.twinkletech.telikomai.notification

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.twinkletech.telikomai.R
import org.twinkletech.telikomai.databinding.FragmentNotificationBinding
import org.twinkletech.telikomai.databinding.FragmentPlansBinding


class NotificationFragment : Fragment() {

    lateinit var binding: FragmentNotificationBinding

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: NotificationAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding = FragmentNotificationBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.recyclerNotifications
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val notifications = buildNotificationList()

        adapter = NotificationAdapter(
            items = notifications,
            onPrimaryClick = { item ->
                Toast.makeText(requireContext(), "${item.primaryButtonText} clicked", Toast.LENGTH_SHORT).show()
            },
            onSecondaryClick = { item ->
                Toast.makeText(requireContext(), "${item.secondaryButtonText} clicked", Toast.LENGTH_SHORT).show()
            }
        )

        recyclerView.adapter = adapter

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner) {
            findNavController().navigate(R.id.action_notificationFragment_to_dashboardFragment)
        }
    }

    private fun buildNotificationList(): List<NotificationItem> = listOf(
        NotificationItem(
            id = 1,
            title = "Plan expires in 2 days",
            message = "Your Mobile Wasa Night Data plan (1GB) expires 19 May. Renew now to avoid losing service.",
            timeLabel = "now",
            cardType = CardType.WARNING,
            primaryButtonText = "Renew plan",
            secondaryButtonText = "Dismiss"
        ),
        NotificationItem(
            id = 2,
            title = "Balance running low",
            message = "675-770-66066 balance is K 1.09. Top up now to stay connected.",
            timeLabel = "5m ago",
            cardType = CardType.ALERT,
            primaryButtonText = "Top up now",
            secondaryButtonText = "Later"
        ),
        NotificationItem(
            id = 3,
            title = "Weekend special — just for you",
            message = "Get 5GB for K10 this weekend only. Offer expires Sunday midnight.",
            timeLabel = "1h ago",
            cardType = CardType.PROMO,
            primaryButtonText = "View offer",
            secondaryButtonText = "Dismiss"
        )
    )
}