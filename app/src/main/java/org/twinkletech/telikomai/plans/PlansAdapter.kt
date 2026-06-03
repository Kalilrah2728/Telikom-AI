package org.twinkletech.telikomai.plans

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.selfcaretelikomaidemoscreen.Plan
import org.twinkletech.telikomai.R

class PlansAdapter(
    private val plans: List<Plan>,
    private val onPlanClick: (Plan) -> Unit
) : RecyclerView.Adapter<PlansAdapter.PlanViewHolder>() {

    inner class PlanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName: TextView = itemView.findViewById(R.id.tvPlanName)
        val tvSubtitle: TextView = itemView.findViewById(R.id.tvPlanSubtitle)
        val tvPrice: TextView = itemView.findViewById(R.id.tvPlanPrice)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_plan_row, parent, false)
        return PlanViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlanViewHolder, position: Int) {
        val plan = plans[position]
        holder.tvName.text = plan.name
        holder.tvSubtitle.text = plan.subtitle
        holder.tvPrice.text = plan.price
        holder.itemView.setOnClickListener { onPlanClick(plan) }
    }

    override fun getItemCount(): Int = plans.size
}