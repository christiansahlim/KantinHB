package com.rpll.kantinhb.ui.screen.payment

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.airbnb.lottie.LottieComposition
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.rpll.kantinhb.R
import com.rpll.kantinhb.di.Injection
import com.rpll.kantinhb.navigation.KantinHBScreen
import com.rpll.kantinhb.ui.ViewModelFactory
import com.rpll.kantinhb.ui.common.UiState
import com.rpll.kantinhb.ui.components.CustomTopNavigationBar
import com.rpll.kantinhb.ui.components.Loader
import com.rpll.kantinhb.ui.components.ProductItem04
import com.rpll.kantinhb.ui.theme.VividBlue_100
import com.rpll.kantinhb.ui.theme.VividBlue_300
import com.rpll.kantinhb.utils.Utils.toRupiah

@Composable
fun PaymentScreen(
    navController: NavController,
    viewModel: PaymentViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    //Screen config
    val configuration = LocalConfiguration.current

    val animComposition by rememberLottieComposition(LottieCompositionSpec.RawRes(R.raw.empty_cart_green))
    val totalPayment = remember { mutableStateOf(0.0) }

    BackHandler {
        //remove badge by route directly to the Home screen
        navController.navigate(KantinHBScreen.HomeScreen.route)
    }

    when (configuration.orientation) {
        Configuration.ORIENTATION_LANDSCAPE -> {
            com.rpll.kantinhb.ui.screen.payment.LandscapeVersion(
                navController,
                viewModel,
                totalPayment,
                animComposition
            )
        }

        else -> {
            com.rpll.kantinhb.ui.screen.payment.PortraitVersion(
                navController,
                viewModel,
                totalPayment,
                animComposition
            )
        }
    }
}


@Composable
private fun LandscapeVersion(
    navController: NavController,
    viewModel: PaymentViewModel,
    totalPayment: MutableState<Double>,
    animComposition: LottieComposition?
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTopNavigationBar(title = "Payment", navController = navController, onClickAction = {
            navController.navigate(KantinHBScreen.HomeScreen.route)
        })

        Row {
            viewModel.productUiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
                when (uiState) {
                    is UiState.Loading -> {
                        viewModel.getProductsInPayment()
                        Loader(Modifier.size(80.dp))
                    }

                    is UiState.Success -> {
                        if (uiState.data.isNotEmpty()) {
                            LazyColumn(
                                verticalArrangement = Arrangement.spacedBy(16.dp),
                                contentPadding = PaddingValues(horizontal = 16.dp),
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .weight(1f)
                                    .padding(vertical = 10.dp)
                                    .testTag("productByCategoryList")
                            ) {
                                items(uiState.data) { order ->
                                    ProductItem04(
                                        navController = navController,
                                        order = order,
                                        viewModel = viewModel
                                    )
                                }
                            }
                        } else {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(modifier = Modifier.fillMaxWidth()) {
                                    LottieAnimation(
                                        composition = animComposition,
                                        modifier = Modifier
                                            .align(Alignment.Center)
                                            .size(100.dp),
                                        iterations = LottieConstants.IterateForever,
                                    )
                                }

                                Text(
                                    "Your cart still empty",
                                    style = TextStyle(
                                        fontSize = 24.sp,
                                        fontWeight = FontWeight.Bold,
                                        color = VividBlue_100
                                    )
                                )

                                Spacer(modifier = Modifier.height(20.dp))

                                Button(
                                    onClick = {
                                        navController.navigate(KantinHBScreen.CategoryScreen.route)
                                        navController.currentBackStackEntry?.arguments?.putInt(
                                            "categoryId",
                                            1
                                        )
                                    },
                                    shape = RoundedCornerShape(50)
                                ) {
                                    Text(text = "Browse our foods & drinks")
                                }
                            }
                        }
                    }

                    is UiState.Error -> {}
                }
            }

            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(1f)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                PaymentSummary(viewModel, totalPayment)

                Spacer(modifier = Modifier.weight(1f))

                Button(
                    onClick = {
                        navController.navigate(KantinHBScreen.SuccessPayment.route)
                        viewModel.removeAllProductsFromCart()
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp, end = 16.dp),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "Place to Order"
                    )
                }
            }
        }
    }
}

@Composable
private fun PortraitVersion(
    navController: NavController,
    viewModel: PaymentViewModel,
    totalPayment: MutableState<Double>,
    animComposition: LottieComposition?
) {
    Column(
        modifier = Modifier
            .fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        CustomTopNavigationBar(title = "Payment", navController = navController, onClickAction = {
            navController.navigate(KantinHBScreen.HomeScreen.route)
        })


        viewModel.productUiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
            when (uiState) {
                is UiState.Loading -> {
                    viewModel.getProductsInPayment()
                    Loader(Modifier.size(80.dp))
                }

                is UiState.Success -> {
                    if (uiState.data.isNotEmpty()) {
                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(16.dp),
                            contentPadding = PaddingValues(horizontal = 16.dp),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                                .padding(vertical = 8.dp)
                                .testTag("productByCategoryList")
                        ) {
                            items(uiState.data) { order ->
                                ProductItem04(
                                    navController = navController,
                                    order = order,
                                    viewModel = viewModel
                                )
                            }
                        }

                        ButtonRow()

                        PaymentSummary(viewModel, totalPayment)

                        Button(
                            onClick = {
                                navController.navigate(KantinHBScreen.SuccessPayment.route)
                                viewModel.removeAllProductsFromCart()
                            },
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(start = 16.dp, end = 16.dp),
                            shape = RoundedCornerShape(50)
                        ) {
                            Text(
                                "Place to Order"
                            )
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Box(modifier = Modifier.fillMaxWidth()) {
                                LottieAnimation(
                                    composition = animComposition,
                                    modifier = Modifier
                                        .align(Alignment.Center)
                                        .fillMaxWidth(),
                                    iterations = LottieConstants.IterateForever,
                                )
                            }

                            Text(
                                "Your cart still empty",
                                style = TextStyle(
                                    fontSize = 24.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = VividBlue_100
                                )
                            )

                            Spacer(modifier = Modifier.fillMaxHeight(.3f))

                            Button(
                                onClick = {
                                    navController.navigate(KantinHBScreen.CategoryScreen.route)
                                    navController.currentBackStackEntry?.arguments?.putInt(
                                        "categoryId",
                                        1
                                    )
                                },
                                shape = RoundedCornerShape(50)
                            ) {
                                Text(text = "Browse our foods & drinks")
                            }
                        }
                    }
                }

                is UiState.Error -> {}
            }
        }
    }
}

@Composable
private fun PaymentSummary(
    viewModel: PaymentViewModel,
    totalPayment: MutableState<Double>
) {
    val serviceAndOtherFee = 8500.0

    totalPayment.value = viewModel.totalPriceInCart.value + serviceAndOtherFee

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Payment summary",
                style = TextStyle(
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp,
                    color = VividBlue_300
                )
            )

            PaymentSummaryItem(
                desc = "Price",
                number = viewModel.totalPriceInCart.value
            )


            PaymentSummaryItem(
                desc = "Service and other fee",
                number = serviceAndOtherFee
            )

            Divider(modifier = Modifier.padding(vertical = 10.dp))

            Row {
                Text(
                    text = "Total Payment",
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black
                    ),
                    modifier = Modifier.weight(1f)
                )

                Text(
                    text = toRupiah(totalPayment.value),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = Color.Black,
                        textAlign = TextAlign.End
                    ),
                )
            }

        }
    }
}

@Composable
private fun PaymentSummaryItem(desc: String, number: Double) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = desc,
            style = TextStyle(
                color = Color.Gray,
                fontSize = 12.sp
            ),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = toRupiah(number),
            style = TextStyle(
                color = Color.Black,
                fontSize = 12.sp,
                textAlign = TextAlign.End
            ),
        )
    }
}

@Composable
fun ButtonRow() {
    var cashClicked by remember { mutableStateOf(false) }
    var ewalletClicked by remember { mutableStateOf(false) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp)
    ) {
        Text(
            text = "Payment Method",
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 8.dp, start = 4.dp)
        )

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Center
        ) {
            Button(
                onClick = {
                    cashClicked = true
                    ewalletClicked = false
                },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .widthIn(80.dp)
                    .clip(
                        RoundedCornerShape(
                            topStart = 20.dp,
                            bottomStart = 20.dp
                        )
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (cashClicked) Color.Blue else Color.Transparent,
                    contentColor = if (cashClicked) Color.White else Color.Black
                )
            ) {
                Text(text = "Cash")
            }

            Button(
                onClick = {
                    ewalletClicked = true
                    cashClicked = false
                },
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .widthIn(80.dp)
                    .clip(
                        RoundedCornerShape(
                            topEnd = 20.dp,
                            bottomEnd = 20.dp
                        )
                    ),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = if (ewalletClicked) Color.Blue else Color.Transparent,
                    contentColor = if (ewalletClicked) Color.White else Color.Black
                )
            ) {
                Text(text = "E-Wallet")
            }
        }
    }
}

