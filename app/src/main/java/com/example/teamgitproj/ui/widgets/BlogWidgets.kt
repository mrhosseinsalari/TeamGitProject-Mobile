package com.example.teamgitproj.ui.widgets

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.ParagraphStyle
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.constraintlayout.compose.ConstraintLayout
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.teamgitproj.R
import com.example.teamgitproj.data.model.Blog
import com.example.teamgitproj.ui.theme.cBackground
import com.example.teamgitproj.ui.theme.cPrimary
import com.example.teamgitproj.ui.theme.cText1
import com.example.teamgitproj.ui.theme.cText3
import com.example.teamgitproj.ui.theme.radius3
import com.example.teamgitproj.ui.theme.radius4
import com.example.teamgitproj.util.SITE_BASE_URL
import com.example.teamgitproj.util.getCurrentOrientation
import com.example.teamgitproj.util.toParagraph

@Composable
fun BlogList(
    modifier: Modifier,
    data: List<Blog>,
    onItemClicked: (Blog) -> Unit
) {
    LazyColumn(modifier = modifier.fillMaxSize()) {
        items(data.size) {
            Blog(blog = data[it], onClicked = { blog ->
                onItemClicked.invoke(blog)
            })
        }
    }
}

@Composable
fun Blog(blog: Blog, onClicked: (Blog) -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val configuration = LocalConfiguration.current
    val context = LocalContext.current
    ConstraintLayout(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 8.dp, bottom = 8.dp)
            .clickable(
                interactionSource = interactionSource ,
                indication = null
            ) {
                onClicked.invoke(blog)
            }
    ) {
        val (category , pic , title) = createRefs()
        Text(
            modifier = Modifier
                .constrainAs(category) {
                    top.linkTo(pic.top)
                    start.linkTo(pic.end)
                }
                .padding(start = 18.dp, top = 8.dp),
            text = blog.category,
            textAlign = TextAlign.Right,
            color = cText3,
            style = MaterialTheme.typography.overline
        )
        Text(
            modifier = Modifier
                .fillMaxWidth(if (configuration.orientation == Configuration.ORIENTATION_PORTRAIT) 0.62f else 0.83f)
                .constrainAs(title) {
                    top.linkTo(category.bottom)
                    start.linkTo(pic.end)
                }
                .padding(start = 18.dp, top = 2.dp, end = 16.dp),
            text = blog.title,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            color = cText1,
            style = MaterialTheme.typography.h5,
            textAlign = TextAlign.Right
        )
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(if (blog.image.startsWith("upload/")) SITE_BASE_URL + blog.image else blog.image)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .size(136.dp, 90.dp)
                .padding(start = 16.dp)
                .clip(radius4)
                .constrainAs(pic) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                },
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
    }
}

@Composable
fun BlogToolbar(
    titleArticle: String,
    onBackPressed: () -> Unit,
    onInfoClicked: () -> Unit
) {
    val titleMaxWidth = (LocalConfiguration.current.screenWidthDp.dp / 100) * 65
    ConstraintLayout(
        modifier = Modifier.run {
            fillMaxWidth()
                .height(72.dp)
                .background(cBackground)
        }
    ) {
        val (infoButton, backButton, toolbarTitle) = createRefs()
        Text(
            modifier = Modifier
                .widthIn(min = 5.dp, max = titleMaxWidth)
                .wrapContentWidth(align = Alignment.CenterHorizontally)
                .constrainAs(toolbarTitle) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
            text = titleArticle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            textAlign = TextAlign.Left
        )
        MainButton(modifier = Modifier.constrainAs(infoButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            end.linkTo(parent.end, 16.dp)
        }, R.drawable.ic_info) {
            onInfoClicked.invoke()
        }
        MainButton(modifier = Modifier.constrainAs(backButton) {
            top.linkTo(parent.top)
            bottom.linkTo(parent.bottom)
            start.linkTo(parent.start, 16.dp)
        }, R.drawable.ic_arrow_right) {
            onBackPressed.invoke()
        }
    }
}

@Composable
fun BlogContent(blog: Blog, onImageClicked: () -> Unit) {
    val interactionSource = remember { MutableInteractionSource() }
    val context = LocalContext.current
    val paragraphStyle = ParagraphStyle(textAlign = TextAlign.Justify)
    val annotatedText = buildAnnotatedString {
        withStyle(SpanStyle(color = cText1)) {
            append(blog.content.toParagraph(3))
        }
        addStyle(paragraphStyle, 0, length)
    }
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
    ) {
        val width = 328
        val height = 246
        val aspectRatio = width.toFloat() / height.toFloat()
        AsyncImage(
            model = ImageRequest.Builder(context)
                .data(if (blog.image.startsWith("upload/")) SITE_BASE_URL + blog.image else blog.image)
                .crossfade(true)
                .build(),
            modifier = Modifier
                .aspectRatio(aspectRatio)
                .padding(top = 8.dp, start = 16.dp, end = 16.dp)
                .clip(radius3)
                .clickable(
                    interactionSource = interactionSource,
                    indication = null
                ) {
                    onImageClicked.invoke()
                },
            contentScale = ContentScale.Crop,
            contentDescription = null
        )
        Text(
            modifier = Modifier.padding(top = 24.dp, start = 16.dp, end = 16.dp, bottom = 24.dp),
            text = annotatedText,
            color = cText1,
            textAlign = TextAlign.Right,
            style = MaterialTheme.typography.body1,
        )
    }
}

@Composable
fun BlogInfoDialog(blog: Blog, onDismiss: () -> Unit) {
    val context = LocalContext.current
    val orientation = getCurrentOrientation()
    Dialog(onDismissRequest = onDismiss) {
        Card(
            shape = RoundedCornerShape(size = 24.dp)
        ) {
            Column(modifier = if (orientation == 1) Modifier.verticalScroll(rememberScrollState()) else Modifier) {
                val width = 288
                val height = 216
                val aspectRatio = width.toFloat() / height.toFloat()
                AsyncImage(
                    model = ImageRequest.Builder(context)
                        .data(if (blog.image.startsWith("upload/")) SITE_BASE_URL + blog.image else blog.image)
                        .crossfade(true)
                        .build(),
                    modifier = Modifier
                        .aspectRatio(aspectRatio)
                        .padding(top = 4.dp, start = 4.dp, end = 4.dp)
                        .clip(RoundedCornerShape(size = 20.dp)),
                    contentScale = ContentScale.Crop,
                    contentDescription = null
                )
                Spacer(modifier = Modifier.height(26.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        tint = cPrimary,
                        painter = painterResource(id = R.drawable.dialog_title),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 12.dp),
                        text = "عنوان مقاله",
                        color = cPrimary,
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.overline,
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 48.dp, top = 6.dp , end = 8.dp),
                    text = blog.title,
                    color = cText1,
                    textAlign = TextAlign.Right,
                    style = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.height(26.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        tint = cPrimary,
                        painter = painterResource(id = R.drawable.dialog_category),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 12.dp),
                        text = "دسته بندی",
                        color = cPrimary,
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.overline,
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 48.dp, top = 6.dp),
                    text = blog.category,
                    color = cText1,
                    textAlign = TextAlign.Right,
                    style = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.height(26.dp))
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 16.dp),
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        modifier = Modifier.size(20.dp),
                        tint = cPrimary,
                        painter = painterResource(id = R.drawable.dialog_tag),
                        contentDescription = null
                    )
                    Text(
                        modifier = Modifier.padding(start = 12.dp),
                        text = "نویسنده",
                        color = cPrimary,
                        textAlign = TextAlign.Right,
                        style = MaterialTheme.typography.overline,
                    )
                }
                Text(
                    modifier = Modifier.padding(start = 48.dp, top = 6.dp),
                    text = blog.author,
                    color = cText1,
                    textAlign = TextAlign.Right,
                    style = MaterialTheme.typography.body2,
                )
                Spacer(modifier = Modifier.height(26.dp))
            }
        }
    }
}