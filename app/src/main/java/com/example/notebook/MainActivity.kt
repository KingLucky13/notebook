package com.example.notebook

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notebook.db.DbManager
import com.example.notebook.db.MyIntentConstants

class MainActivity : ComponentActivity() {

    private val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        dbManager.openDb()
        setContent {
            DrawMain()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }

   @Preview
   @Composable
   fun DrawMain(){
       val notes = dbManager.readDb()
       Column(
           Modifier
               .fillMaxSize()
               .background(Color.Black),verticalArrangement = Arrangement.Top){
           LazyColumn(
               Modifier
                   .fillMaxWidth()
                   .fillMaxHeight(0.925f)
                   .background(Color.Blue)
                   .padding(all = 5.dp)) {
              items(notes){notes->
                  Button(onClick = {
                      val intent= Intent(this@MainActivity,AddNoteActivity::class.java).apply {
                          putExtra(MyIntentConstants.TITLE_KEY,notes.title)
                          putExtra(MyIntentConstants.DESCRIPTION_KEY,notes.content)
                          putExtra(MyIntentConstants.URI_KEY,notes.uri)
                      }
                      startActivity(intent)
                  },
                      modifier = Modifier.fillMaxWidth(),shape = RectangleShape) {
                      Text(notes.title,fontSize=18.sp, modifier = Modifier.fillMaxWidth(), textAlign = TextAlign.Left)
                  }
              }
           }
           Spacer(modifier = Modifier
               .fillMaxWidth()
               .fillMaxHeight(0.025f) )
           Button(onClick = {
               val intent= Intent(this@MainActivity,AddNoteActivity::class.java)
               startActivity(intent)

           }, Modifier.fillMaxSize(),shape = RectangleShape) {
               Text(text="Добавить", textAlign = TextAlign.Center)
           }
       }
   }
}
