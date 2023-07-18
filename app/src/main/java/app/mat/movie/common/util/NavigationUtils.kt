package app.mat.movie.common.util

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.AnimatedVisibilityScope
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController

fun NavHostController.safeNavigate(
    route: String,
    onSameRouteSelected: (String) -> Unit = {}
) {
    val currentRoute = currentBackStackEntry?.destination?.route

    if (currentRoute == route) {
        onSameRouteSelected(
            route
        )
        return
    }

    val isInBackstack = try {
        getBackStackEntry(
            route
        ).destination.route == route
    } catch (e: Exception) {
        false
    }

    if (isInBackstack) {
        popBackStack(
            route = route,
            inclusive = false
        )
    } else {
        navigate(
            route
        ) {
            graph.findStartDestination().route?.let {
                popUpTo(
                    it
                )
            }
            launchSingleTop = true
        }
    }
}

@Composable
fun EnterAnimation(
    content: @Composable AnimatedVisibilityScope.() -> Unit
) {
    AnimatedVisibility(
        visibleState = MutableTransitionState(
            true
        ),
        enter = slideInVertically(
            initialOffsetY = {
                -40
            }
        ) + expandVertically(
            expandFrom = Alignment.Top
        ) + fadeIn(
            initialAlpha = 0.3f
        ),
        exit = slideOutVertically() + shrinkVertically() + fadeOut(),
        content = content,
    )
}