package com.dbtechprojects.photonotes

import com.dbtechprojects.photonotes.model.Note

object Constants {
const val NAVIGATION_NOTES_LIST = "notesList"
const val NAVIGATION_NOTES_CREATE = "notesCreated"
const val NAVIGATION_NOTE_DETAIL = "noteDetail/{noteId}"
const val NAVIGATION_NOTE_EDIT= "noteEdit/{noteId}"
const val NAVIGATION_NOTE_ID_Argument = "noteId"
const val TABLE_NAME = "Notes"
const val DATABASE_NAME = "NotesDatabase"

    fun noteDetailNavigation(noteId : Int) = "noteDetail/$noteId"
    fun noteEditNavigation(noteId : Int) = "noteEdit/$noteId"


    fun List<Note>?.orPlaceHolderList(): List<Note> {
        fun placeHolderList(): List<Note> {
            return listOf(Note(id = 0, title = "메모를 찾을 수 없습니다", note = "메모를 작성하십시오", dateUpdated = ""))
        }
        return if (this != null && this.isNotEmpty()){
            this
        } else placeHolderList()
    }

    val noteDetailPlaceHolder = Note(note = "메모 세부정보를 찾을 수 없음", id = 0, title = "메모 세부정보를 찾을 수 없음")
}