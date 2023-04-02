package com.dbtechprojects.photonotes

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.dbtechprojects.photonotes.memo.EditNote.NoteEditScreen
import com.dbtechprojects.photonotes.memo.NoteDetail.NoteDetailScreen
import com.dbtechprojects.photonotes.memo.NotesList.NotesList
import com.dbtechprojects.photonotes.memo.NotesViewModel
import com.dbtechprojects.photonotes.memo.NotesViewModelFactory
import com.dbtechprojects.photonotes.memo.createNote.CreateNoteScreen


@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MemoScreen(){

    Log.d("database","start")
    val notesViewModel : NotesViewModel = NotesViewModelFactory(PhotoNotesApp.getDao()).create(
        NotesViewModel::class.java)

    Scaffold() {
        val navController = rememberNavController()
        NavHost(
            navController = navController,
            startDestination = Constants.NAVIGATION_NOTES_LIST
        ) {
            composable(Constants.NAVIGATION_NOTES_LIST) { NotesList(navController, notesViewModel) }

            composable(
                Constants.NAVIGATION_NOTE_DETAIL,
                arguments = listOf(navArgument(Constants.NAVIGATION_NOTE_ID_Argument) {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getInt(Constants.NAVIGATION_NOTE_ID_Argument)
                    ?.let { NoteDetailScreen(noteId = it, navController, notesViewModel) }
            }

            composable(
                Constants.NAVIGATION_NOTE_EDIT,
                arguments = listOf(navArgument(Constants.NAVIGATION_NOTE_ID_Argument) {
                    type = NavType.IntType
                })
            ) { backStackEntry ->
                backStackEntry.arguments?.getInt(Constants.NAVIGATION_NOTE_ID_Argument)
                    ?.let { NoteEditScreen(noteId = it, navController, notesViewModel) }
            }

            composable(Constants.NAVIGATION_NOTES_CREATE) { CreateNoteScreen(navController, notesViewModel) }

        }
    }
}