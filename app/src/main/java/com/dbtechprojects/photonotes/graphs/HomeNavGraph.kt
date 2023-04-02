package com.dbtechprojects.photonotes.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.dbtechprojects.photonotes.BottomBarScreen
import com.dbtechprojects.photonotes.MemoScreen
import com.dbtechprojects.photonotes.screens.calendar.CalendarEvent
import com.project.bicos_project.screens.ScreenContent
import com.dbtechprojects.photonotes.screens.calendar.CalendarScreen
import com.dbtechprojects.photonotes.screens.client.AddClientScreen
import com.dbtechprojects.photonotes.screens.info.*
import com.dbtechprojects.photonotes.screens.logIn.LoginContent
import com.dbtechprojects.photonotes.screens.memo.search.SearchScreen
import com.dbtechprojects.photonotes.screens.logIn.ForgotContent
import com.dbtechprojects.photonotes.screens.logIn.SignUpContent
import com.project.bicos_project.graphs.Graph
import com.project.bicos_project.screens.info.MyInfo

@Composable
fun HomeNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        route = Graph.HOME,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            ScreenContent(
                name = BottomBarScreen.Home.route,
                onClick = {
                    navController.navigate(Graph.DETAILS)
                }
            )
            CalendarScreen(navController)
        }
        composable(route = BottomBarScreen.Profile.route) {
            MyInfo(navController)
        }
        composable(route = DetailsScreen.Chprofilepw.route){
            MyInfo_pwchange(navController)
        }
        composable(route = DetailsScreen.Infofare.route){
            MyInfo_fare(navController)
        }
        composable(route = DetailsScreen.Infodrop1.route){
            MyInfo_break(navController)
        }
        composable(route = DetailsScreen.Infodrop2.route){
            MyInfo_break2(navController)
        }
        composable(route = BottomBarScreen.Memo.route) {
            MemoScreen()
        }
        composable(route = BottomBarScreen.AddClient.route) {
            AddClientScreen(navController)
        }
        composable(route = BottomBarScreen.Search.route) {
            SearchScreen()
        }
        composable(route = DetailsScreen.Login2.route) {
            LoginContent(
                loginClick = {
                    navController.popBackStack()
                    navController.navigate(Graph.HOME)
                },
                onSignUpClick = {
                    navController.navigate(DetailsScreen.SignUp2.route)
                },
                onForgotClick = {
                    navController.navigate(DetailsScreen.Forgot2.route)
                }
            )
        }
        composable(route = DetailsScreen.SignUp2.route) {
            SignUpContent(name = DetailsScreen.SignUp2.route, navController) {
            }
        }
        composable(route = DetailsScreen.Forgot2.route) {
            ForgotContent(name = DetailsScreen.Forgot2.route) {}
        }
        composable(route = DetailsScreen.Chprofile.route) {
            MyInfo_change(navController = navController)
        }
        composable(route = DetailsScreen.Chprofilecl.route) {
            MyInfo(navController)
        }
        composable(route = DetailsScreen.CalDetail.route) {
            CalendarEvent(navController)
        }
    }
}

fun NavGraphBuilder.detailsNavGraph(navController: NavHostController) {
    navigation(
        route = Graph.DETAILS,
        startDestination = DetailsScreen.Information.route
    ) {
        composable(route = DetailsScreen.Information.route) {
            ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Overview.route)
            }
        }
        composable(route = DetailsScreen.Overview.route) {
            ScreenContent(name = DetailsScreen.Overview.route) {
                navController.popBackStack(
                    route = DetailsScreen.Information.route,
                    inclusive = false
                )
            }

        }
        composable(route = DetailsScreen.Information.route) {
            ScreenContent(name = DetailsScreen.Information.route) {
                navController.navigate(DetailsScreen.Memo.route)
            }
        }
    }
}

sealed class DetailsScreen(val route: String) {
    object Information : DetailsScreen(route = "정보")
    object Overview : DetailsScreen(route = "상세")
    object Memo : DetailsScreen(route = "메모")
    object Chprofile : DetailsScreen(route = "내정보수정")
    object Chprofilecl : DetailsScreen(route = "내정보수정 취소")
    object Chprofilepw : DetailsScreen(route = "비밀번호수정")
    object Infofare : DetailsScreen(route = "멤버십구매")
    object Infodrop1 : DetailsScreen(route = "회원탈퇴1")
    object Infodrop2 : DetailsScreen(route = "회원탈퇴2")
    object CalDetail : DetailsScreen(route = "캘린더상세")
    object Login2 : DetailsScreen(route = "1")
    object SignUp2 : DetailsScreen(route = "2")
    object Forgot2 : DetailsScreen(route = "3")

}