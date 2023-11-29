package com.example.aeontest.presenter.payments_list.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aeontest.R
import com.example.aeontest.domain.model.Payment
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PaymentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val textTitle = itemView.findViewById<TextView>(R.id.text_title)
    private val textAmount = itemView.findViewById<TextView>(R.id.text_amount)
    private val textCreated = itemView.findViewById<TextView>(R.id.text_created)

    fun bind(payment: Payment) {

        payment.title?.let {
            textTitle.text = it
            textTitle.visibility = View.VISIBLE
        }

        payment.amount?.let {
            textAmount.text = formatLargeNumber(it)
            textAmount.visibility = View.VISIBLE
        }

        payment.created?.let {
            val timestamp: Long = payment.created.toLong()
            val formattedDate: String = convertUnixTimestampToDate(timestamp)
            textCreated.text = formattedDate
            textCreated.visibility = View.VISIBLE
        }
    }

    // преобразование даты в нужный формат
    private fun convertUnixTimestampToDate(timestamp: Long): String {
        val sdf = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault())
        val date = Date(timestamp * 1000)
        return sdf.format(date)
    }

    // форматирование чисел
    private fun formatLargeNumber(number: Any): String {
        val numberFormat = NumberFormat.getInstance()
        numberFormat.maximumFractionDigits = 3

        return try {
            when (number) {
                is String -> {
                    val numericValue = number.toDoubleOrNull()
                    if (numericValue != null) {
                        numberFormat.format(numericValue)
                    } else {
                        ""
                    }
                }
                is Long -> numberFormat.format(number)
                is Double -> numberFormat.format(number)
                is Int -> numberFormat.format(number)
                else -> ""
            }
        } catch (e: NumberFormatException) {
            ""
        }
    }
}