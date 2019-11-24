package ge.tbc.tbcdemo.presentation.match

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import ge.tbc.tbcdemo.R
import ge.tbc.tbcdemo.data.models.*
import ge.tbc.tbcdemo.presentation.helper.loadIcon
import kotlinx.android.synthetic.main.item_action.view.*

class ActionsAdapter : RecyclerView.Adapter<ActionsAdapter.ActionsViewHolder>() {

    private val actions = mutableListOf<RVActionModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ActionsViewHolder {
        return ActionsViewHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_action,
                parent,
                false
            )
        )
    }

    fun setItems(items: List<RVActionModel>) {
        actions.clear()
        actions.addAll(items)
        notifyDataSetChanged()
    }

    override fun getItemCount() = actions.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ActionsViewHolder, position: Int) {
        val action = actions[position]
        resetPlayer1(holder)
        resetPlayer2(holder)
        if (position == 0 || actions[position].actionTime != actions[position - 1].actionTime)
            holder.itemView.ivCircle.visibility = View.VISIBLE
        else holder.itemView.ivCircle.visibility = View.GONE

        if (action.team1Action != null) {
            populatePlayer(
                holder.itemView.ivPlayer1, holder.itemView.txtAction1,
                holder.itemView.txtPlayer1, holder.itemView.ivAction1,
                holder.itemView.ivSubPlayer1, holder.itemView.txtSubPlayer1,
                action.team1Action, action.actionTime
            )
        }
        if (action.team2Action != null) {

            populatePlayer(
                holder.itemView.ivPlayer2, holder.itemView.txtAction2,
                holder.itemView.txtPlayer2, holder.itemView.ivAction2,
                holder.itemView.ivSubPlayer2, holder.itemView.txtSubPlayer2,
                action.team2Action, action.actionTime
            )
        }
    }

    @SuppressLint("SetTextI18n")
    private fun populatePlayer(
        ivPlayer: ImageView,
        txtAction: TextView,
        txtPlayer: TextView,
        ivAction: ImageView,
        ivSubPlayer: ImageView,
        txtSubPlayer: TextView,
        teamAction: TeamAction,
        actionTime: Int
    ) {
        ivPlayer.visibility = View.VISIBLE
        txtAction.visibility = View.VISIBLE
        txtPlayer.visibility = View.VISIBLE
        ivAction.visibility = View.VISIBLE

        when (teamAction.actionType) {
            MatchActionType.GOAL -> {
                ivPlayer.loadIcon((teamAction.action as GoalAction).player.playerImage ?: "")
                if ((teamAction.action).goalType == GoalType.GOAL) {
                    ivAction.setImageResource(R.drawable.ic_ball_green)
                    txtAction.text = "${actionTime} Goals by"
                } else {
                    txtAction.setTextColor(
                        ContextCompat.getColor(
                            txtAction.context,
                            R.color.red_2
                        )
                    )
                    ivAction.setImageResource(R.drawable.ic_ball_red)
                    txtAction.text = "${actionTime} Own Goal"
                }
                txtPlayer.text = teamAction.action.player.playerName
            }
            MatchActionType.YELLOW_CARD, MatchActionType.RED_CARD -> {
                ivPlayer.loadIcon((teamAction.action as CardAction).player.playerImage ?: "")
                if (teamAction.actionType == MatchActionType.YELLOW_CARD)
                    ivAction.setImageResource(R.drawable.ic_yellow_card)
                else
                    ivAction.setImageResource(R.drawable.ic_red_card)
                txtAction.text = "${actionTime} Tripping"
                txtPlayer.text = teamAction.action.player.playerName
            }
            MatchActionType.SUBSTITUTION -> {
                txtAction.text = "${actionTime} Substitution"
                ivSubPlayer.visibility = View.VISIBLE
                txtSubPlayer.visibility = View.VISIBLE
                txtPlayer.text = (teamAction.action as SubstitutionAction).player1.playerName
                txtSubPlayer.text = teamAction.action.player2.playerName
                ivPlayer.loadIcon(teamAction.action.player1.playerImage ?: "")
                ivSubPlayer.loadIcon(teamAction.action.player2.playerImage ?: "")
            }
        }
    }

    private fun resetPlayer1(holder: ActionsViewHolder) {
        holder.itemView.ivPlayer1.visibility = View.GONE
        holder.itemView.ivSubPlayer1.visibility = View.GONE
        holder.itemView.txtAction1.visibility = View.GONE
        holder.itemView.txtPlayer1.visibility = View.GONE
        holder.itemView.txtSubPlayer1.visibility = View.GONE
        holder.itemView.ivAction1.visibility = View.GONE
        holder.itemView.txtAction1.setTextColor(
            ContextCompat.getColor(
                holder.itemView.context,
                R.color.grey_1
            )
        )
    }

    private fun resetPlayer2(holder: ActionsViewHolder) {
        holder.itemView.ivPlayer2.visibility = View.GONE
        holder.itemView.ivSubPlayer2.visibility = View.GONE
        holder.itemView.txtAction2.visibility = View.GONE
        holder.itemView.txtPlayer2.visibility = View.GONE
        holder.itemView.txtSubPlayer2.visibility = View.GONE
        holder.itemView.ivAction2.visibility = View.GONE
    }

    inner class ActionsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView)
}