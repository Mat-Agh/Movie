package app.mat.movie.presentation.component.item

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavHostController
import app.mat.movie.common.util.safeNavigate
import app.mat.movie.presentation.navigation.screen.NavigationBarGraphScreen
import app.mat.movie.presentation.view.MainViewModel

@Composable
fun RowScope.AddItem(
    modifier: Modifier,
    mainViewModel: MainViewModel,
    screen: NavigationBarGraphScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    NavigationBarItem(
        modifier = modifier,
        label = {
            Text(
                text = stringResource(
                    id = screen.title
                ),
                style = MaterialTheme.typography.labelMedium
            )
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = null
            )
        },
        selected = currentDestination?.hierarchy?.any {
            it.route == screen.route
        } == true,
        colors = NavigationBarItemDefaults.colors(),
        onClick = {
            navController.safeNavigate(
                route = screen.route,
                onSameRouteSelected = { sameRoute ->
                    mainViewModel.onSameRouteSelected(
                        sameRoute
                    )
                }
            )
        }
    )
}