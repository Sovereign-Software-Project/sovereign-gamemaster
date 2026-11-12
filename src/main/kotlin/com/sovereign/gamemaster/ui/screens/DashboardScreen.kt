package com.sovereign.gamemaster.ui.screens



// @Composable
// fun DashboardScreen(
//     containerState: ContainerState,
//     logOutput: String,
//     onStartClick: () -> Unit,
//     onStopClick: () -> Unit,
//     onRestartClick: () -> Unit,
//     onClearLogsClick: () -> Unit,
//     modifier: Modifier = Modifier
// ) {
//     val currentTheme = remember { SovereignThemeConfig.fromPreset(ThemePreset.MINIMAL_DARK) }
// 
//     Column(
//         modifier = modifier
//             .fillMaxSize()
//             .background(currentTheme.background)
//             .padding(16.dp),
//         verticalArrangement = Arrangement.spacedBy(16.dp)
//     ) {
//         ServerHeader(
//             serverName = "SERVER NAME HERE",
//             details = "LIST DETAILS HERE",
//             containerState = containerState,
//             theme = currentTheme
//         )
// 
//         ServerActionBar(
//             containerState = containerState,
//             theme = currentTheme,
//             onStart = onStartClick,
//             onStop = onStopClick,
//             onRestart = onRestartClick,
//             onClearLogs = onClearLogsClick
//         )
// 
//         TerminalCard(
//             logs = logOutput,
//             theme = currentTheme,
//             modifier = Modifier.fillMaxWidth().weight(1f)
//         )
//     }
// }