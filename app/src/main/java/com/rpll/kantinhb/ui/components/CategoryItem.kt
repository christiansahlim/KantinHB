package com.rpll.kantinhb.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.rpll.kantinhb.model.Category
import com.rpll.kantinhb.navigation.KantinHBScreen
import com.rpll.kantinhb.ui.theme.FloralWhite
import com.rpll.kantinhb.ui.theme.VividBlue_500

@Composable
fun CategoryItem(
    category: Category,
    navController: NavController
){
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = Modifier
            .clickable {
                navController.navigate(KantinHBScreen.CategoryScreen.route)
                navController.currentBackStackEntry?.arguments?.putInt("categoryId", category.id)
            }
    ) {
        Box {
            Card(
                modifier = Modifier
                    .size(65.dp),
                shape = CircleShape,
                backgroundColor = FloralWhite,
                content = {}
            )

            Image(
                painter = painterResource(id = category.image),
                contentDescription = "Image in Category Item",
                modifier = Modifier
                    .size(55.dp)
                    .align(Alignment.Center)
            )
        }
        Box(modifier = Modifier.height(8.dp))
        Text(
            text = category.name,
            style = TextStyle(
                fontSize = 12.sp,
                color = VividBlue_500,
                fontWeight = FontWeight.Bold
            )
        )
    }
}