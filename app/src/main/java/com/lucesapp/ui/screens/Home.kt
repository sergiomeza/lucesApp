package com.lucesapp.ui.screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.compose.*
import com.lucesapp.R
import com.lucesapp.Screen
import com.lucesapp.ui.theme.LucesTheme
import com.lucesapp.ui.theme.backgroundSplash
import com.lucesapp.viewmodel.FirestoreViewModel
import dev.chrisbanes.accompanist.insets.ProvideWindowInsets
import dev.chrisbanes.accompanist.insets.navigationBarsPadding
import dev.chrisbanes.accompanist.insets.statusBarsPadding

@ExperimentalAnimationApi
@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    firestoreViewModel: FirestoreViewModel
) {
    val navController = rememberNavController()
    LucesTheme {
        ProvideWindowInsets {
            Scaffold(
                topBar = { AppBar() },
                bottomBar = {
                    BottomNavigation(
                        backgroundColor = MaterialTheme.colors.background,
                        modifier = Modifier.navigationBarsPadding()
                    ) {
                        val navBackStackEntry by navController.currentBackStackEntryAsState()
                        val currentRoute = navBackStackEntry?.arguments?.getString(KEY_ROUTE)
                        val items = listOf(
                            Screen.Sales,
                            Screen.Products
                        )
                        items.forEach { screen ->
                            BottomNavigationItem(
                                selected = currentRoute == screen.route,
                                onClick = {
                                    navController.navigate(route = screen.route) {
                                        popUpTo = navController.graph.startDestination
                                        launchSingleTop = true
                                    }
                                },
                                icon = {
                                    Icon(
                                        screen.icon,
                                        contentDescription = null,
                                        modifier = Modifier.size(18.dp),
                                        tint = MaterialTheme.colors.primary
                                    )
                                },
                                label = {
                                    Text(
                                        stringResource(id = screen.resourceId).toUpperCase(),
                                        style = MaterialTheme.typography.caption,
                                        color = MaterialTheme.colors.primary
                                    )
                                }
                            )
                        }
                    }
                }
            ) {
                NavHost(navController = navController, startDestination = Screen.Sales.route,
                    builder = {
                        composable(Screen.Sales.route) {
                            SalesScreen(
                                navController = navController,
                                firestoreViewModel = firestoreViewModel)
                        }
                        composable(Screen.Products.route) {
                            ProductsScreen(
                                navController = navController,
                                firestoreViewModel = firestoreViewModel)
                        }
                    }
                )
            }
        }
    }
}

@Composable
private fun AppBar() {
    Row(
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .statusBarsPadding()
    ) {
        Image(
            painter = painterResource(id = R.drawable.ic_logo_luces),
            contentDescription = null,
            modifier = Modifier.padding(horizontal = 16.dp).size(54.dp)
        )
        IconButton(
            modifier = Modifier.padding(horizontal = 16.dp),
            onClick = { /* todo */ }
        ) {
            Icon(
                imageVector = Icons.Filled.Settings,
                contentDescription = stringResource(R.string.app_name),
                tint = MaterialTheme.colors.primary
            )
        }
    }
}
