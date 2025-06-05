package com.quadrado.movies_app

import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class InfoActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_info)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        supportActionBar?.hide()

        val overviewTextView: TextView = findViewById(R.id.text_geral)
        overviewTextView.text = HtmlCompat.fromHtml(
            getString(R.string.about_project_overview),
            HtmlCompat.FROM_HTML_MODE_LEGACY
        )
        overviewTextView.movementMethod = LinkMovementMethod.getInstance()
        overviewTextView.linksClickable = true

    }


}