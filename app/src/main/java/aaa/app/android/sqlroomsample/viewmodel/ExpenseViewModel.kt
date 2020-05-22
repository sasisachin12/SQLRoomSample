package aaa.app.android.sqlroomsample.viewmodel

import aaa.app.android.sqlroomsample.db.ExpenseRoomDatabase
import aaa.app.android.sqlroomsample.entity.ExpenseInfo
import aaa.app.android.sqlroomsample.repository.Repository
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ExpenseViewModel(app: Application) : AndroidViewModel(app) {
    private val repository: Repository

    // Using LiveData and caching what getAlphabetizedWords returns has several benefits:
    // - We can put an observer on the data (instead of polling for changes) and only update the
    //   the UI when the data actually changes.
    // - Repository is completely separated from the UI through the ViewModel.
    val allExpenses: LiveData<List<ExpenseInfo>>

    val currentData = CompletableDeferred<List<ExpenseInfo>>()
    val insertResponse = CompletableDeferred<Long>()



    init {
        val wordsDao = ExpenseRoomDatabase.getDatabase(app, viewModelScope).expenseDao()
        repository = Repository(wordsDao)
        allExpenses = repository.allExpenses
    }

    /**
     * Launching a new coroutine to insert the data in a non-blocking way
     */
    suspend fun insert(word: ExpenseInfo): Long {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.insert(word)
            insertResponse.complete(result)
        }
        return insertResponse.await()
    }


    suspend fun getExpense(): List<ExpenseInfo> {
        viewModelScope.launch(Dispatchers.IO) {
            val result = repository.getAll()
            currentData.complete(result)
        }
        return currentData.await()
    }


    suspend fun deleteByID(id:Int){
        viewModelScope.launch(Dispatchers.IO) {
            repository.getDeleteById(id)

        }

    }







}