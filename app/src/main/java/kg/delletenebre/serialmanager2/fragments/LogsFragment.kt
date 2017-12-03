package kg.delletenebre.serialmanager2.fragments

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.content.LocalBroadcastManager
import android.text.Selection
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import kg.delletenebre.serialmanager2.App
import kg.delletenebre.serialmanager2.R
import java.text.SimpleDateFormat
import java.util.*


class LogsFragment : Fragment() {
    private lateinit var mSendTextView: TextView
    private lateinit var mSendButton: Button
    private lateinit var mTextView: TextView
    private lateinit var mAutoscrollCheckbox: CheckBox

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val layoutInflater = activity!!.layoutInflater
        val layout = layoutInflater.inflate(R.layout.fragment_logs, container, false)

        mSendTextView = layout.findViewById(R.id.send_text)
        mSendButton = layout.findViewById(R.id.send_button)
        mTextView = layout.findViewById(R.id.text)
        mAutoscrollCheckbox = layout.findViewById(R.id.autoscroll_checkbox)

        mSendButton.setOnClickListener {
            val message = mSendTextView.text.toString()
            addMessage(message)
            sendData(message)
        }

        mTextView.movementMethod = ScrollingMovementMethod()

        return layout
    }

    fun addMessage(message: String) {
        if (message.isNotBlank()) {
            val timestamp = (SimpleDateFormat("HH:mm:ss", Locale.ROOT))
                    .format(Calendar.getInstance().time)
            mTextView.append("$timestamp\t\ue801\ue800\t$message\r\n")

            val editable = mTextView.editableText
            if (mAutoscrollCheckbox.isChecked) {
                Selection.setSelection(editable, editable.length)
            } else {
                Selection.removeSelection(editable)
            }
        }
    }

    fun sendData(data: String) {
        LocalBroadcastManager.getInstance(context!!).sendBroadcast(
                Intent(App.LOCAL_ACTION_SEND_DATA)
                        .putExtra("data", data))
    }
}