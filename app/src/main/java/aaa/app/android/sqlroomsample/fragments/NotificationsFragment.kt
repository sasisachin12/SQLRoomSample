package aaa.app.android.sqlroomsample.fragments

import android.view.LayoutInflater
import android.view.ViewGroup
import android.os.Bundle
import aaa.app.android.sqlroomsample.R
import android.view.View
import android.widget.TextView
import androidx.fragment.app.Fragment

class NotificationsFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?, savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_notifications, container, false)
        val textView = root.findViewById<TextView>(R.id.text_notifications)
        return root
    }
}