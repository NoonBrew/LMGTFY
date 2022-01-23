package com.example.lmgtfy

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.core.widget.doAfterTextChanged

class MainActivity : AppCompatActivity() {
    // Allows for initializing our views later.
    private lateinit var searchText: EditText
    private lateinit var searchButton: Button
    private lateinit var searchConfirm: TextView
    var clickCounter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // Initialize our views by finding their resource ID in our code with the
        // findViewById function.
        searchText = findViewById(R.id.enter_search_text)
        searchButton = findViewById(R.id.search_button)
        searchConfirm = findViewById(R.id.show_search_text)


        // Type of listener, As the text is edited it will preform this function.
        searchText.doAfterTextChanged {
            mirrorUserSearchTerm()
        }

        searchButton.setOnClickListener {
            launchSearch()
        }
    }

    private fun launchSearch() {
        // When the button is clicked we store the value of the text from our EditText view.
        val text = searchText.text
        // isBlank is better than isEmpty for this because empty white space will not pass.
        if (text.isBlank())  {
            // Pass toast to instruct user to type something in the search bar.
            Toast.makeText(this,
                getString(R.string.no_text_error_message), Toast.LENGTH_SHORT).show()
            // Easter Egg search.
            clickCounter += 1
            if(clickCounter >= 10){
                clickCounter = 0
                googleSearch("How to Google")
            }
        } else {
            // Toast to confirm the text is being searched for.
            Toast.makeText(this,
                getString(R.string.searching_confirm_message, text),
                Toast.LENGTH_LONG).show()
            googleSearch(text.toString())

        }
    }

    private fun googleSearch(text: String) {
        // Grabs our google url string from resources and passes it the user search text.
        val webAddress = getString(R.string.google_search_text, text)
        // Passes that to a Uri
        val uri = Uri.parse(webAddress)
        // Creates an Intent to try and start our activity with.
        val browserIntent = Intent(Intent.ACTION_VIEW, uri)
        startActivity(browserIntent)

        searchText.text.clear()
    }

    private fun mirrorUserSearchTerm() {
        val text = searchText.text
        if (text.isNotBlank()){
            // Grabs our template string from the resource file and uses the search text
            // as an argument.
            searchConfirm.text = getString(R.string.search_display_text, text)
        } else {
            // If there is no search text we pass our own argument.
            searchConfirm.text = getString(R.string.search_display_text, "...")
        }
    }
}