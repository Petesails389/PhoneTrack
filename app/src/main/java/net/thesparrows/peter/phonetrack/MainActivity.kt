package net.thesparrows.peter.phonetrack

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.collectAsState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.compose.runtime.getValue
import androidx.room.Room
import kotlin.getValue

class MainActivity : ComponentActivity() {

    private val db by lazy<AppDatabase> {
        Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java,
            "database.db"
        ).build()
    }

    private val viewModel by viewModels<HomeViewModel>(
        factoryProducer = {
            object : ViewModelProvider.Factory {
                override fun <T : ViewModel> create(modelClass: Class<T>): T {
                    @Suppress("UNCHECKED_CAST")
                    return HomeViewModel(db.dao) as T
                }
            }
        }
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state by viewModel.state.collectAsState()
            HomeScreen(state = state, onEvent = viewModel::onEvent)
        }
    }
}