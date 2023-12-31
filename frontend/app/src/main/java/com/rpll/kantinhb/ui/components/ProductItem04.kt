package com.rpll.kantinhb.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rpll.kantinhb.R
import com.rpll.kantinhb.data.source.DataSource
import com.rpll.kantinhb.di.Injection
import com.rpll.kantinhb.model.OrderItem
import com.rpll.kantinhb.navigation.KantinHBScreen
import com.rpll.kantinhb.ui.ViewModelFactory
import com.rpll.kantinhb.ui.screen.cart.CartViewModel
import com.rpll.kantinhb.ui.screen.payment.PaymentViewModel
import com.rpll.kantinhb.ui.theme.VividBlue_100
import com.rpll.kantinhb.utils.Utils
import kotlinx.coroutines.delay

@Composable
fun ProductItem04(
    navController: NavController,
    order: OrderItem,
    viewModel: PaymentViewModel
){
    val totalOrder = remember{ mutableStateOf(order.count) }

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 16.dp)
            .clickable {
                //Go to detail product
                navController.navigate(KantinHBScreen.DetailScreen.route)
                navController.currentBackStackEntry?.arguments?.putParcelable("product", order.item)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        Divider()
        Spacer(modifier = Modifier.height(20.dp))
        Row {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    order.item.title,
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 18.sp,
                        color = Color.Black
                    ),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                )
                Text(
                    order.item.description,
                    style = TextStyle(
                        fontSize = 12.sp,
                        color = Color.Black
                    ),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    Utils.toRupiah(order.item.price),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 16.sp,
                        color = VividBlue_100
                    ),
                )
            }
            Card(
                modifier = Modifier
                    .size(80.dp)
                    .padding(8.dp)
            ) {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(order.item.image_url)
                        .crossfade(true)
                        .build(),
                    placeholder = painterResource(R.drawable.kantinhb_logo_bg),
                    error = painterResource(id = R.drawable.placeholder),
                    contentDescription = "Product image",
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .size(80.dp)
                        .padding(8.dp)
                )
            }
        }
        Row {
            Spacer(modifier = Modifier.weight(1f))

            if(totalOrder.value == 0){
                LaunchedEffect(Unit) {
                    delay(1500L)

                    //remove product from cart
                    viewModel.removeProductInPayment(
                        productId = order.item.id
                    )

                    navController.navigate(KantinHBScreen.CartScreen.route)
                }

                Row{
                    Spacer(modifier = Modifier.weight(1f))

                    Button(
                        onClick = {},
                        modifier = Modifier
                            .width(150.dp)
                            .padding(vertical = 10.dp),
                        shape = RoundedCornerShape(50),
                        colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                    ) {
                        Text(
                            "Remove Item",
                            color = Color.White
                        )
                    }
                }

            }else {
                AddRemoveButton(
                    totalOrder,
                    modifier = Modifier
                        .fillMaxWidth(.5f),
                    updateOrder = viewModel.updateProductInPayment(
                        productId = order.item.id,
                        total = totalOrder.value
                    )
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ProductItem04Preview(){
    val navController = rememberNavController()
    val dummyOrder = OrderItem(
        item = DataSource.products()[0],
        count = 2
    )
    val viewModel: PaymentViewModel = viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )

    ProductItem04(
        navController = navController,
        order = dummyOrder,
        viewModel = viewModel
    )
}