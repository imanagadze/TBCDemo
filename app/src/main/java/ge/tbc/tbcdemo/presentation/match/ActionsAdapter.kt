package ge.tbc.tbcdemo.presentation.match

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
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
            holder.itemView.ivPlayer1.visibility = View.VISIBLE
            holder.itemView.txtAction1.visibility = View.VISIBLE
            holder.itemView.txtPlayer1.visibility = View.VISIBLE
            holder.itemView.ivAction1.visibility = View.VISIBLE

            when (action.team1Action.actionType) {
                MatchActionType.GOAL -> {
                    holder.itemView.ivPlayer1.loadIcon(
                        (action.team1Action.action as GoalAction).player.playerImage ?: ""
                    )
                    if ((action.team1Action.action).goalType == GoalType.GOAL) {
                        holder.itemView.ivAction1.setImageResource(R.drawable.ic_ball_green)
                        holder.itemView.txtAction1.text = "${action.actionTime} Goals by"
                    } else {
                        holder.itemView.txtAction1.setTextColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.red_2
                            )
                        )
                        holder.itemView.ivAction1.setImageResource(R.drawable.ic_ball_red)
                        holder.itemView.txtAction1.text = "${action.actionTime} Own Goal"
                    }
                    holder.itemView.txtPlayer1.text = action.team1Action.action.player.playerName
                }
                MatchActionType.YELLOW_CARD, MatchActionType.RED_CARD -> {
                    holder.itemView.ivPlayer1.loadIcon(
                        (action.team1Action.action as CardAction).player.playerImage ?: ""
                    )
                    if (action.team1Action.actionType == MatchActionType.YELLOW_CARD)
                        holder.itemView.ivAction1.setImageResource(R.drawable.ic_yellow_card)
                    else
                        holder.itemView.ivAction1.setImageResource(R.drawable.ic_red_card)
                    holder.itemView.txtAction1.text = "${action.actionTime} Tripping"
                    holder.itemView.txtPlayer1.text = action.team1Action.action.player.playerName
                }
                MatchActionType.SUBSTITUTION -> {
                    holder.itemView.txtAction1.text = "${action.actionTime} Substitution"
                    holder.itemView.ivSubPlayer1.visibility = View.VISIBLE
                    holder.itemView.txtSubPlayer1.visibility = View.VISIBLE
                    holder.itemView.txtPlayer1.text =
                        (action.team1Action.action as SubstitutionAction).player1.playerName
                    holder.itemView.txtSubPlayer1.text =
                        action.team1Action.action.player2.playerName
                    holder.itemView.ivPlayer1.loadIcon(
                        action.team1Action.action.player1.playerImage ?: ""
                    )
                    holder.itemView.ivSubPlayer1.loadIcon(
                        action.team1Action.action.player2.playerImage ?: ""
                    )
                }
            }
        }
        if (action.team2Action != null) {
            holder.itemView.ivPlayer2.visibility = View.VISIBLE
            holder.itemView.txtAction2.visibility = View.VISIBLE
            holder.itemView.txtPlayer2.visibility = View.VISIBLE
            holder.itemView.ivAction2.visibility = View.VISIBLE

            when (action.team2Action.actionType) {
                MatchActionType.GOAL -> {
                    holder.itemView.ivPlayer1.loadIcon(
                        (action.team2Action.action as GoalAction).player.playerImage ?: ""
                    )
                    if ((action.team2Action.action).goalType == GoalType.GOAL) {
                        holder.itemView.ivAction2.setImageResource(R.drawable.ic_ball_green)
                        holder.itemView.txtAction2.text = "${action.actionTime} Goals by"
                    } else {
                        holder.itemView.txtAction2.setTextColor(
                            ContextCompat.getColor(
                                holder.itemView.context,
                                R.color.red_2
                            )
                        )
                        holder.itemView.ivAction2.setImageResource(R.drawable.ic_ball_red)
                        holder.itemView.txtAction2.text = "${action.actionTime} Own Goal"
                    }
                    holder.itemView.txtPlayer2.text = action.team2Action.action.player.playerName
                }
                MatchActionType.YELLOW_CARD, MatchActionType.RED_CARD -> {
                    holder.itemView.ivPlayer2.loadIcon(
                        (action.team2Action.action as CardAction).player.playerImage ?: ""
                    )
                    if (action.team2Action.actionType == MatchActionType.YELLOW_CARD)
                        holder.itemView.ivAction2.setImageResource(R.drawable.ic_yellow_card)
                    else
                        holder.itemView.ivAction2.setImageResource(R.drawable.ic_red_card)
                    holder.itemView.txtAction2.text = "${action.actionTime} Tripping"
                    holder.itemView.txtPlayer2.text = action.team2Action.action.player.playerName
                }
                MatchActionType.SUBSTITUTION -> {
                    holder.itemView.txtAction2.text = "${action.actionTime} Substitution"
                    holder.itemView.ivSubPlayer2.visibility = View.VISIBLE
                    holder.itemView.txtSubPlayer2.visibility = View.VISIBLE
                    holder.itemView.txtPlayer2.text =
                        (action.team2Action.action as SubstitutionAction).player1.playerName
                    holder.itemView.txtSubPlayer2.text =
                        action.team2Action.action.player2.playerName
                    holder.itemView.ivPlayer2.loadIcon(
                        action.team2Action.action.player1.playerImage ?: ""
                    )
                    holder.itemView.ivSubPlayer2.loadIcon(
                        action.team2Action.action.player2.playerImage ?: ""
                    )
                }
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