package com.rpll.kantinhb.ui.screen.detail

import android.content.res.Configuration
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.rpll.kantinhb.di.Injection
import com.rpll.kantinhb.model.OrderItem
import com.rpll.kantinhb.model.ProductItem
import com.rpll.kantinhb.navigation.KantinHBScreen
import com.rpll.kantinhb.ui.ViewModelFactory
import com.rpll.kantinhb.ui.common.UiState
import com.rpll.kantinhb.ui.components.AddRemoveButton
import com.rpll.kantinhb.ui.components.Title
import com.rpll.kantinhb.ui.theme.Marigold_100
import com.rpll.kantinhb.ui.theme.VividBlue_100
import com.rpll.kantinhb.ui.theme.VividBlue_300
import com.rpll.kantinhb.utils.Utils.toRupiah
import com.rpll.kantinhb.R

@Composable
fun DetailScreen(
    navController: NavController,
    product: ProductItem,
    viewModel: DetailViewModel = androidx.lifecycle.viewmodel.compose.viewModel(
        factory = ViewModelFactory(Injection.provideRepository())
    )
) {
    //Screen config
    val configuration = LocalConfiguration.current

    //Total order
    val totalOrder = remember { mutableStateOf(1) }

    //list favorites
    val listOfFavorite: MutableState<ArrayList<Long>> = remember { mutableStateOf(arrayListOf()) }
    viewModel.favoriteUiState.collectAsState(initial = UiState.Loading).value.let { uiState ->
        when (uiState) {
            is UiState.Loading -> {
                viewModel.getMyFavorites()
            }
            is UiState.Success -> {
                listOfFavorite.value = uiState.data as ArrayList<Long>
            }
            is UiState.Error -> {}
        }
    }

    //Check product in cart
    val productInCart: List<OrderItem> = viewModel.getOrderById(productId = product.id)
    LaunchedEffect(Unit) {
        if (productInCart.isNotEmpty()) {
            totalOrder.value = productInCart[0].count
        }
    }


    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        CustomTopNavigationBar(
            navController,
            viewModel = viewModel,
            listOfFavorites = listOfFavorite,
            productId = product.id
        )

        when (configuration.orientation) {
            Configuration.ORIENTATION_LANDSCAPE -> {
                LandscapeVersion(product, totalOrder, productInCart, viewModel, navController)
            }
            else -> {
                PortraitVersion(product, totalOrder, productInCart, viewModel, navController)
            }
        }
    }
}

@Composable
private fun LandscapeVersion(
    product: ProductItem,
    totalOrder: MutableState<Int>,
    productInCart: List<OrderItem>,
    viewModel: DetailViewModel,
    navController: NavController
) {
    Row {
        TopSection(
            product,
            modifier = Modifier
                .weight(1f)
        )

        Column(
            modifier = Modifier.weight(3f)
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .verticalScroll(rememberScrollState())
            ) {
                DescriptionSection(product)
                WebsiteSection(product.web_url)

                AddRemoveButton(
                    totalOrder,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp),
                    isInCart = productInCart.isNotEmpty()
                )
            }

            if (productInCart.isNotEmpty() && totalOrder.value == 0) {
                Button(
                    onClick = {
                        //remove from cart
                        viewModel.removeProductInCart(product.id)

                        //go to previous page
                        navController.navigateUp()
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text(
                        "Remove from cart",
                        color = Color.White
                    )
                }
            } else if (productInCart.isNotEmpty() && totalOrder.value != 0) {
                Button(
                    onClick = {
                        //update cart
                        viewModel.updateProductInCart(
                            productId = product.id,
                            total = totalOrder.value
                        )
                        //go to success screen
                        navController.navigate(KantinHBScreen.SuccessAddToCartScreen.route)
                        navController.currentBackStackEntry?.arguments?.putBoolean("isUpdate", true)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(50),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Marigold_100)
                ) {
                    Text(
                        "Update Order",
                        color = VividBlue_100
                    )
                }
            } else {
                Button(
                    onClick = {
                        //add to cart
                        viewModel.addProductToCart(
                            product = product,
                            total = totalOrder.value
                        )
                        //go to success screen
                        navController.navigate(KantinHBScreen.SuccessAddToCartScreen.route)
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                    shape = RoundedCornerShape(50)
                ) {
                    Text(
                        "Add to Order"
                    )
                }
            }
        }
    }
}

@Composable
private fun PortraitVersion(
    product: ProductItem,
    totalOrder: MutableState<Int>,
    productInCart: List<OrderItem>,
    viewModel: DetailViewModel,
    navController: NavController
) {
    Column {
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
        ) {
            TopSection(product)
            DescriptionSection(product)
            WebsiteSection(product.web_url)

            AddRemoveButton(
                totalOrder,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                isInCart = productInCart.isNotEmpty()
            )
        }

        if (productInCart.isNotEmpty() && totalOrder.value == 0) {
            Button(
                onClick = {
                    //remove from cart
                    viewModel.removeProductInCart(product.id)

                    //go to previous page
                    navController.navigateUp()
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
            ) {
                Text(
                    "Remove from cart",
                    color = Color.White
                )
            }
        } else if (productInCart.isNotEmpty() && totalOrder.value != 0) {
            Button(
                onClick = {
                    //update cart
                    viewModel.updateProductInCart(
                        productId = product.id,
                        total = totalOrder.value
                    )
                    //go to success screen
                    navController.navigate(KantinHBScreen.SuccessAddToCartScreen.route)
                    navController.currentBackStackEntry?.arguments?.putBoolean("isUpdate", true)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50),
                colors = ButtonDefaults.buttonColors(backgroundColor = Marigold_100)
            ) {
                Text(
                    "Update Order",
                    color = VividBlue_100
                )
            }
        } else {
            Button(
                onClick = {
                    //add to cart
                    viewModel.addProductToCart(
                        product = product,
                        total = totalOrder.value
                    )
                    //go to success screen
                    navController.navigate(KantinHBScreen.SuccessAddToCartScreen.route)
                },
                modifier = Modifier
                    .fillMaxWidth(),
                shape = RoundedCornerShape(50)
            ) {
                Text(
                    "Add to Order"
                )
            }
        }
    }
}

@Composable
fun WebsiteSection(webUrl: String) {
    val uriHandler = LocalUriHandler.current

    Title(title = "Website", modifier = Modifier.padding(top = 16.dp))

    Text(
        webUrl,
        style = TextStyle(
            fontSize = 14.sp,
            textDecoration = TextDecoration.Underline,
            color = VividBlue_300
        ),
        modifier = Modifier.clickable {
            uriHandler.openUri(webUrl)
        },
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
    )
}

@Composable
private fun CustomTopNavigationBar(
    navController: NavController,
    listOfFavorites: MutableState<ArrayList<Long>>,
    productId: Long,
    viewModel: DetailViewModel
) {
    val context = LocalContext.current

    IconButton(onClick = {
        navController.navigateUp()
    }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back Button")

            Text(
                text = "Detail Product",
                style = TextStyle(fontWeight = FontWeight.Bold, fontSize = 18.sp),
                modifier = Modifier
                    .padding(start = 8.dp)
                    .weight(1f)
            )

            IconButton(
                onClick = {
                    if (listOfFavorites.value.contains(productId)) {
                        //remove
                        viewModel.removeFromFavorite(productId)

                        navController.navigate(KantinHBScreen.FavoriteStatusScreen.route)
                        navController.currentBackStackEntry?.arguments?.putBoolean("isAdd", false)
                    } else {
                        //add
                        viewModel.addToFavorite(productId)

                        navController.navigate(KantinHBScreen.FavoriteStatusScreen.route)
                        navController.currentBackStackEntry?.arguments?.putBoolean("isAdd", true)
                    }
                }
            ) {
                Card(
                    modifier = Modifier
                        .size(35.dp),
                    shape = CircleShape
                ) {
                    Box(
                        modifier = Modifier.size(15.dp)
                    ) {
                        if (listOfFavorites.value.contains(productId)) {
                            Icon(
                                imageVector = Icons.Filled.Favorite,
                                contentDescription = "Favorite Icon",
                                tint = Color.Red,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        } else {
                            Icon(
                                imageVector = Icons.Default.FavoriteBorder,
                                contentDescription = "Unfavorite Icon",
                                tint = Color.LightGray,
                                modifier = Modifier.align(Alignment.Center)
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun TopSection(
    product: ProductItem,
    modifier: Modifier = Modifier
        .fillMaxWidth()
        .height(300.dp)
) {
    Column {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(product.image_url)
                .crossfade(true)
                .build(),
            placeholder = painterResource(R.drawable.kantinhb_logo_bg),
            error = painterResource(id = R.drawable.placeholder),
            contentDescription = "Product image",
            contentScale = ContentScale.Fit,
            modifier = modifier
        )

        Text(
            product.title,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = 28.sp
            )
        )

        Text(
            toRupiah(product.price),
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = VividBlue_100,
                fontSize = 20.sp
            )
        )
    }
}

@Composable
private fun DescriptionSection(product: ProductItem) {
    Title(title = "Description", modifier = Modifier.padding(top = 16.dp))

    Text(
        product.description,
        style = TextStyle(
            color = Color.Black,
            fontSize = 14.sp
        )
    )
}