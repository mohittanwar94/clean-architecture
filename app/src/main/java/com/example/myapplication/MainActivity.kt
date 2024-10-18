package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarDuration
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.lifecycleScope
import com.example.myapplication.ui.theme.MyApplicationTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MyViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        viewModel.setValueFlow()
        viewModel.liveData.observe(this){
            println("it==============$it")
        }
        //setObserver()
        setContent {
            MyApplicationTheme {
                val snackBarHostState = remember {
                    SnackbarHostState()
                }
                Scaffold(snackbarHost = { SnackbarHost(hostState = snackBarHostState) }) { paddingValues ->
                    Column(Modifier.padding(paddingValues)) {
                        val dataState = viewModel.stateUi.collectAsState()
                        println("dataState==========${dataState.value.data}")
                        dataState.value.data?.let { data ->
                            if (data.isNotEmpty())
                                lifecycleScope.launch {
                                    snackBarHostState.showSnackbar(
                                        "data loaded",
                                        duration = SnackbarDuration.Long,
                                        withDismissAction = true
                                    )
                                }
                            LazyColumn {
                                items(data.count(), itemContent = {
                                    Text(
                                        text = data[it].name ?: "",
                                        Modifier
                                            .fillMaxWidth()
                                            .padding(all = 20.dp)
                                            .background(Color.Red)
                                    )
                                })
                            }
                        } ?: run {
                            lifecycleScope.launch {
                                snackBarHostState.showSnackbar(
                                    dataState.value.message ?: "",
                                    duration = SnackbarDuration.Long,
                                    withDismissAction = true
                                )
                            }
                            Greeting(name = dataState.value.message ?: "", Modifier.padding(0.dp))
                        }

                    }
                }
            }
        }
    }

    private fun setObserver() {
        viewModel.setValueFlow()
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}