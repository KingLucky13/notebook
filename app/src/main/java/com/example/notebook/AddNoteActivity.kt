package com.example.notebook

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import coil.compose.AsyncImage
import com.example.notebook.db.DbManager
import com.example.notebook.db.MyIntentConstants

class AddNoteActivity : ComponentActivity() {

    private val dbManager = DbManager(this)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DrawAddNote()

        }
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDb()
    }

    @Preview
    @Composable
    fun DrawAddNote(){
        val context = LocalContext.current

        var titleText by remember { mutableStateOf("") }
        var descriptionText by remember { mutableStateOf("") }
        var imageUri by remember { mutableStateOf<Uri?>(Uri.parse("android.resource://"+context.packageName +"/"+R.drawable.avatarka))}

        val i = intent
        if(i != null ){
            if( i.getStringExtra(MyIntentConstants.TITLE_KEY ) != null ){
                titleText = i.getStringExtra(MyIntentConstants.TITLE_KEY)!!
                descriptionText = i.getStringExtra(MyIntentConstants.DESCRIPTION_KEY)!!
                imageUri = i.getStringExtra(MyIntentConstants.URI_KEY)!!.toUri()
            }
        }

        var displayImagePanel by remember { mutableIntStateOf(0) }



        dbManager.openDb()

        val startForImageResult = rememberLauncherForActivityResult(
            contract = ActivityResultContracts.GetContent()
        ) {
            imageUri =it
        }

        Column(
            Modifier
                .fillMaxSize()
                .background(Color.Black)) {
            if(displayImagePanel==1) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .fillMaxHeight(0.15f), verticalAlignment = Alignment.CenterVertically
                ) {
                    AsyncImage(
                        model = imageUri,
                        contentDescription = "image",
                        Modifier
                            .fillMaxHeight()
                            .padding(all = 10.dp)
                    )

                    Spacer(Modifier.fillMaxWidth(0.025f))

                    Button(
                        onClick = {
                            startForImageResult.launch("image/*")
                        },
                        Modifier.fillMaxHeight(0.425f).aspectRatio(1f),
                        shape = CircleShape
                    ) {

                    }

                    Spacer(Modifier.fillMaxWidth(0.125f))

                    Button(
                        onClick = {
                            displayImagePanel = 0
                                  },
                        Modifier.fillMaxHeight(0.425f).aspectRatio(1f),
                        shape = CircleShape
                    ) {

                    }
                }
            }

            TextField(
                value = titleText, onValueChange = { titleText = it },
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.10f)
                    .padding(all = 10.dp)
                    .background(Color.Blue),
                placeholder = { Text(text = "title") }
            )

            TextField(
                value = descriptionText, onValueChange = { descriptionText = it },
                Modifier
                    .fillMaxWidth()
                    .fillMaxHeight(0.75f)
                    .padding(all = 10.dp)
                    .background(Color.Blue),
                placeholder = { Text(text = "description") }
            )

            Button(onClick = {
                finish()
            },
                Modifier.fillMaxWidth(),shape = RectangleShape) {
                Text(text="Назад", textAlign = TextAlign.Center)
            }

            Button(onClick = {
                if(titleText != ""){
                    dbManager.writeDb(titleText,descriptionText, imageUri.toString())
                    finish()
                }
            },
                Modifier.fillMaxWidth(),shape = RectangleShape) {
                Text(text="Добавить", textAlign = TextAlign.Center)
            }

            if(displayImagePanel == 0) {
                Button(onClick = {
                    displayImagePanel = 1
                }, Modifier.fillMaxWidth(), shape = RectangleShape) {
                    Text(text = "Фотография", textAlign = TextAlign.Center)
                }
            }
        }
    }
    }
