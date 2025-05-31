package com.example.bibliosphere.presentation.drawerScreens.about

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat.startActivity
import com.example.bibliosphere.R
import com.example.bibliosphere.presentation.components.AppIcon
import com.example.bibliosphere.presentation.components.AppIconAboutUs
import com.example.bibliosphere.presentation.theme.BiblioSphereTheme

@Composable
fun AboutScreen() {

    val scrollState = rememberScrollState()
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState),
        horizontalAlignment = Alignment.Start,
    ) {
        Spacer(modifier = Modifier.height(20.dp))
        AppIconAboutUs(width = 100.dp)

        Box(
            modifier = Modifier
                .padding(20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(BiblioSphereTheme.dimens.roundedShapeLarge))
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = BiblioSphereTheme.dimens.paddingMedium),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    stringResource(R.string.about_title),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.headlineSmall.copy(fontWeight = FontWeight.Bold),
                    color = MaterialTheme.colorScheme.background,
                )
            }
        }
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, bottom = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        top = 20.dp,
                        bottom = 20.dp,
                        start = BiblioSphereTheme.dimens.paddingMedium,
                        end = BiblioSphereTheme.dimens.paddingMedium
                    ),
            ) {
                Text(
                    text = (stringResource(R.string.about_body0)),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.background,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = (stringResource(R.string.about_body1)),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.background,
                )
                Spacer(modifier = Modifier.height(20.dp))
                Text(
                    text = (stringResource(R.string.about_body2)),
                    textAlign = TextAlign.Start,
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.background,
                )

            }
        }
        Box(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth()
                .clip(RoundedCornerShape(16.dp))
                .background(MaterialTheme.colorScheme.secondary),
            contentAlignment = Alignment.Center
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 20.dp, bottom = 20.dp, start = 16.dp, end = 16.dp),
            ) {
                Text(text = stringResource(R.string.contact), textAlign = TextAlign.Start,style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.background)
                Spacer(modifier = Modifier.height(BiblioSphereTheme.dimens.spacerNormal))
                Text(
                    text = stringResource(R.string.contact_email),
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.inversePrimary,
                        textDecoration = TextDecoration.Underline,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Start,
                    modifier = Modifier.clickable {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:${context.getString(R.string.email_address)}")
                            putExtra(Intent.EXTRA_SUBJECT, context.getString(R.string.email_subject))
                        }
                        startActivity(context, intent, null)
                    }
                )
            }

        }

    }
}

@Preview(showBackground = true,showSystemUi = true)
@Composable
fun AboutScreenPreview() {
    AboutScreen()
}