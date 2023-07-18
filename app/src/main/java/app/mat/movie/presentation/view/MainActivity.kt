package app.mat.movie.presentation.view

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.core.view.WindowCompat
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.rememberNavController
import app.mat.movie.presentation.navigation.graph.MainGraph
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    //region Override Methods
    override fun onCreate(
        savedInstanceState: Bundle?
    ) {
        super.onCreate(
            savedInstanceState
        )

        configWindow()

        setContent {
            val mainViewModel: MainViewModel = hiltViewModel(
                viewModelStoreOwner = this
            )

            val navHostController = rememberNavController()

            MainGraph(
                mainViewModel = mainViewModel,
                navHostController = navHostController
            )
        }
    }
    //endregion

    //region Private Functions
    private fun configWindow() = WindowCompat.setDecorFitsSystemWindows(
        window,
        true
    )
    //endregion
}