package com.dbtechprojects.photonotes

import android.content.Intent
import android.os.Bundle
import android.provider.ContactsContract
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.navigation.compose.rememberNavController
import com.dbtechprojects.photonotes.theme.PhotoNotesTheme
import com.project.bicos_project.graphs.RootNavigationGraph


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PhotoNotesTheme() {
                RootNavigationGraph(navController = rememberNavController())
            }
        }
    }
}

//@Preview(showBackground = true)
//@Composable
//fun DefaultPreview() {
//    BICOS_ProjectTheme {
//
//    }
//}