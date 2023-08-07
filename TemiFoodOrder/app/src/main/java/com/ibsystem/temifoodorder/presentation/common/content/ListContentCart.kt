package com.ibsystem.temifoodorder.presentation.common.content

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Divider
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import com.ibsystem.temifoodorder.R
import com.ibsystem.temifoodorder.domain.model.ProductItem
import com.ibsystem.temifoodorder.ui.theme.*

@Composable
fun ListContentCart(
    modifier: Modifier = Modifier,
    cartProducts: List<ProductItem>,
    onClickDeleteCart: (ProductItem) -> Unit
) {
    if (cartProducts.isNotEmpty()) {
        LazyColumn(
            modifier = modifier.fillMaxWidth(),
            contentPadding = PaddingValues(top = DIMENS_32dp),
            verticalArrangement = Arrangement.spacedBy(DIMENS_8dp)
        ) {
            items(cartProducts) { items ->
                ContentCart(productItem = items,
                    onClickDeleteCart = { productItem ->
                        onClickDeleteCart.invoke(productItem)
                    }
                )
            }
        }
    } else {
        EmptyContent()
    }
}

@Composable
fun ContentCart(
    modifier: Modifier = Modifier,
    productItem: ProductItem,
    onClickDeleteCart: (ProductItem) -> Unit
) {
    Column {
        Divider(modifier = Modifier.height(DIMENS_1dp), color = GrayBorderStroke)

        Row(
            modifier = modifier
                .fillMaxWidth()
                .padding(top = DIMENS_8dp)
        ) {
//            Image(
//                modifier = Modifier
//                    .size(width = DIMENS_64dp, height = DIMENS_64dp)
//                    .padding(start = DIMENS_8dp),
//                painter = painterResource(id = productItem.image),
//                contentDescription = stringResource(id = R.string.image_product)
//            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .align(Alignment.CenterVertically)
                    .padding(start = DIMENS_16dp),
            ) {
                Text(
                    text = productItem.prodName!!,
                    fontFamily = GilroyFontFamily,
                    fontWeight = FontWeight.Bold,
                    color = Black,
                    fontSize = TEXT_SIZE_16sp
                )

            }

            Text(
                modifier = Modifier.align(Alignment.CenterVertically),
                text = "$${productItem.prodPrice}",
                fontFamily = GilroyFontFamily,
                fontWeight = FontWeight.Bold,
                color = Black,
                fontSize = TEXT_SIZE_18sp,
            )

            Image(
                modifier = Modifier
                    .align(Alignment.CenterVertically)
                    .padding(start = DIMENS_16dp, end = DIMENS_16dp)
                    .clickable {
                        onClickDeleteCart.invoke(productItem)
                    },
                imageVector = Icons.Default.Delete,
                contentDescription = stringResource(R.string.image_delete),
                colorFilter = ColorFilter.tint(color = Color.DarkGray)
            )

        }
    }
}

@Preview(showBackground = true)
@Composable
fun ContentCartPreview() {
    ContentCart(
        productItem = ProductItem(
            catId = "aishhvaisuhg",
            prodAvail = true,
            prodDesc = "god damn",
            prodId = "foauhvauhuahg",
            prodImage = "https://burpple-2.imgix.net/foods/18701ea9eb80bcd299c1559365_original.",
            prodPrice = 598,
            prodName = "焼肉"
        ),
        onClickDeleteCart = {}
    )
}

@Preview(showBackground = true)
@Composable
fun ListContentCartPreview() {
    ListContentCart(cartProducts = emptyList(), onClickDeleteCart = {})
}