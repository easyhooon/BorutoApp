package com.kenshi.borutoapp.presentation.common

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ContentAlpha
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil.annotation.ExperimentalCoilApi
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.kenshi.borutoapp.R
import com.kenshi.borutoapp.domain.model.Hero
import com.kenshi.borutoapp.navigation.Screen
import com.kenshi.borutoapp.presentation.components.RatingWidget
import com.kenshi.borutoapp.presentation.components.ShimmerEffect
import com.kenshi.borutoapp.ui.theme.HERO_ITEM_HEIGHT
import com.kenshi.borutoapp.ui.theme.LARGE_PADDING
import com.kenshi.borutoapp.ui.theme.MEDIUM_PADDING
import com.kenshi.borutoapp.ui.theme.SMALL_PADDING
import com.kenshi.borutoapp.ui.theme.topAppBarContentColor
import com.kenshi.borutoapp.util.Constants.BASE_URL

@ExperimentalCoilApi
@Composable
fun ListContent(
    heroes: LazyPagingItems<Hero>,
    navController: NavHostController
) {
    val result = handlePagingResult(heroes = heroes)

    // only result true
    if (result) {
        // Log.d("ListContent", heroes.loadState.toString())
        LazyColumn(
            contentPadding = PaddingValues(all = SMALL_PADDING),
            // 아이템 간의 간격을 직접 결정 해주는 함수
            verticalArrangement = Arrangement.spacedBy(SMALL_PADDING)
        ) {
            items(
                items = heroes,
                key = { hero ->
                    hero.id
                }
            ) { hero ->
                // item 에 navController 를 파라미터로 넘겨
                // item 클릭시 detail screen 으로 넘어갈 수 있도록 함
                hero?.let {
                    HeroItem(hero = it, navController = navController)
                }
            }
        }
    }
}

@Composable
fun handlePagingResult(
    heroes: LazyPagingItems<Hero>
): Boolean {
    heroes.apply {
        val error = when {
            loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
            loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
            loadState.append is LoadState.Error -> loadState.append as LoadState.Error
            else -> null
        }

        return when {
            loadState.refresh is LoadState.Loading -> {
                ShimmerEffect()
                false
            }
            error != null -> {
                // error not noll -> have some error in our response
                EmptyScreen(error = error, heroes = heroes)
                false
            }

            // no receive (결과 없음)
            heroes.itemCount < 1 -> {
                EmptyScreen()
                false
            }
            else -> true
        }
    }
}

@ExperimentalCoilApi
@Composable
fun HeroItem(
    hero: Hero,
    navController: NavHostController
) {
//    val painter = rememberImagePainter(data = "$BASE_URL${hero.image}") {
//        placeholder(R.drawable.ic_placeholder)
//        error(R.drawable.ic_placeholder)
//    }
    Box(
        modifier = Modifier
            .height(HERO_ITEM_HEIGHT)
            .clickable {
                // Detail 화면으로 이동
                navController.navigate(Screen.Details.passHeroId(heroId = hero.id))
            },
        contentAlignment = Alignment.BottomStart
    ) {
        Surface(shape = RoundedCornerShape(size = LARGE_PADDING)) {
            AsyncImage(
                modifier = Modifier.fillMaxSize(),
                model = ImageRequest.Builder(LocalContext.current)
                    .data(data = "$BASE_URL${hero.image}")
                    .placeholder(drawableResId = R.drawable.ic_placeholder)
                    .error(drawableResId = R.drawable.ic_placeholder)
                    .build(),
                contentDescription = stringResource(id = R.string.hero_image),
                contentScale = ContentScale.Crop
            )
//            Image(
//                modifier = Modifier.fillMaxSize(),
//                painter = painter,
//                contentDescription = stringResource(R.string.hero_image),
//                contentScale = ContentScale.Crop
//            )
        }
        Surface(
            modifier = Modifier
                .fillMaxHeight(0.4f)
                .fillMaxWidth(),
            color = Color.Black.copy(alpha = ContentAlpha.medium),
            shape = RoundedCornerShape(
                bottomStart = LARGE_PADDING,
                bottomEnd = LARGE_PADDING
            )
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = MEDIUM_PADDING)
            ) {
                Text(
                    text = hero.name,
                    color = MaterialTheme.colors.topAppBarContentColor,
                    fontSize = MaterialTheme.typography.h5.fontSize,
                    fontWeight = FontWeight.Bold,
                    maxLines = 1,
                    //ellipsis 처리 하는 법(overflow)
                    overflow = TextOverflow.Ellipsis
                )
                Text(
                    text = hero.about,
                    color = Color.White.copy(alpha = ContentAlpha.medium),
                    fontSize = MaterialTheme.typography.subtitle1.fontSize,
                    maxLines = 3,
                    overflow = TextOverflow.Ellipsis
                )
                Row(
                    modifier = Modifier.padding(top = SMALL_PADDING),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RatingWidget(
                        modifier = Modifier.padding(end = SMALL_PADDING),
                        rating = hero.rating
                    )
                    Text(
                        text = "(${hero.rating})",
                        textAlign = TextAlign.Center,
                        color = Color.White.copy(alpha = ContentAlpha.medium)
                    )
                }
            }
        }
    }
}

@ExperimentalCoilApi
@Composable
@Preview()
fun HeroItemPreview() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Some random text...",
            rating = 4.5,
            power = 100,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        navController = rememberNavController()
    )
}

@ExperimentalCoilApi
@Composable
@Preview(uiMode = UI_MODE_NIGHT_YES)
fun HeroItemDarkPreview() {
    HeroItem(
        hero = Hero(
            id = 1,
            name = "Sasuke",
            image = "",
            about = "Some random text...",
            rating = 4.5,
            power = 100,
            month = "",
            day = "",
            family = listOf(),
            abilities = listOf(),
            natureTypes = listOf()
        ),
        navController = rememberNavController()
    )
}