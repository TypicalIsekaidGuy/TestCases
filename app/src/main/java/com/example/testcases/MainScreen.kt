package com.example.testcases

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalUriHandler
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.testcases.model.ArticleEntity
import com.example.testcases.model.NewsCategory
import com.example.testcases.ui.theme.DarkBlack
import com.example.testcases.ui.theme.LightBlack

@Composable
fun MainScreen(viewmodel:MainViewModel){
    val newsList = viewmodel.newsList.value

    LazyColumn(verticalArrangement = Arrangement.spacedBy(16.dp),modifier = Modifier

        .padding(top = 16.dp)
        .padding(horizontal = 8.dp)){

        item{
            Box(){Text(text = "News", fontSize = 32.sp, textAlign = TextAlign.Center, modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center))}


        }
        item{ SearchField() }
        item{ CategoriesList(list = newsList) }
    }
}

@Composable
fun SearchField() {
    var text = remember { mutableStateOf("") }
    Box(modifier = Modifier.background(Color.Gray)){
        Row(modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)) {
            Icon(Icons.Default.Search,null, modifier = Modifier.size(24.dp))

            BasicTextField(value = text.value, onValueChange = {text.value = it},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp),
                textStyle = TextStyle(color = Color.Black)
            )
        }
    }
}

@Composable
fun CategoriesList(list: List<NewsCategory>){
    Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
        for (category in list)
            CategoryBlock(category)
    }
}
@Composable
fun CategoryBlock(category: NewsCategory){
    Text(category.name, fontSize = 24.sp, modifier = Modifier
        .padding(start = 16.dp))
    LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
        items(category.articleList.size){
            NewsArticle(category.articleList[it])
        }
    }
}
@Composable
fun NewsArticle(article: ArticleEntity){
    val uriHandler = LocalUriHandler.current
    Box(
        modifier = Modifier
            .size(width = 150.dp, height = 250.dp)
            .padding(12.dp)
            .clickable {
                uriHandler.openUri(article.url)
            }
    ) {
        if(article.imageBitmap == null){
            Image(
                painter = painterResource(id = R.drawable.test_img),
                contentDescription = null,
                contentScale = ContentScale.FillHeight,
                modifier = Modifier
                    .height(250.dp)
            )
        }
        else{
                    Image(
                        bitmap = article.imageBitmap,
                        contentDescription = null,
                        contentScale = ContentScale.FillHeight,
                        modifier = Modifier
                            .height(250.dp)
                    )
        }



        Box(modifier = Modifier
            .align(Alignment.BottomStart)
            .background(Brush.verticalGradient(listOf(Color.Transparent, LightBlack, DarkBlack)))){
            Text(
                text = article.title,
                fontSize = 20.sp,
                color = Color.White,
                textAlign = TextAlign.Start,
                modifier = Modifier
                    .padding(12.dp),
                maxLines = 6,
                overflow = TextOverflow.Ellipsis,
            )
        }
    }
}