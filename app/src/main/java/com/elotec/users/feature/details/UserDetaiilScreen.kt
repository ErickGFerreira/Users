package com.elotec.users.feature.details

import androidx.activity.compose.LocalActivity
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.elotec.users.R
import com.elotec.users.domain.model.LabelValueData
import com.elotec.users.domain.model.User
import com.elotec.users.feature.UsersViewModel
import com.elotec.users.ui.color.BrandPrimary48
import com.elotec.users.ui.color.Neutral100
import com.elotec.users.ui.color.Neutral58
import com.elotec.users.ui.color.Neutral69
import com.elotec.users.ui.component.LabelValue
import com.elotec.users.ui.component.LabelValueText
import com.elotec.users.ui.component.SpacerVertical
import com.elotec.users.ui.dimen.FontSize
import com.elotec.users.ui.dimen.Size
import com.elotec.users.ui.dimen.Spacing
import com.elotec.users.ui.theme.FontStyle

@Composable
fun UserDetailScreen(
    viewModel: UserDetailViewModel,
    flowData: UsersViewModel.FlowData,
) {
    val activity = LocalActivity.current as FragmentActivity

    LaunchedEffect(key1 = Unit) {
        viewModel.setup(flowData = flowData)
    }

    Screen(
        uiState = viewModel.uiState,
        onActionEvent = viewModel::onActionEvent,
    )

    EventConsumer(
        activity = activity,
        viewModel = viewModel,
    )
}

@Composable
private fun EventConsumer(
    activity: FragmentActivity,
    viewModel: UserDetailViewModel,
) = LaunchedEffect(key1 = Unit) {
    viewModel.uiEvent.collect { event ->
        when (event) {
            UserDetailUiEvent.Event.Back -> activity.onBackPressedDispatcher.onBackPressed()
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun Screen(
    onActionEvent: (UserDetailScreenAction) -> Unit,
    uiState: UserDetailUiState,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Neutral58)
    ) {
        TopAppBar(
            title = {
                Text(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    text = stringResource(R.string.user_details_title),
                    style = FontStyle.bodyMedium
                )
            },
            navigationIcon = {
                IconButton(
                    onClick = { onActionEvent(UserDetailScreenAction.BackButtonAction) },
                    modifier = Modifier.testTag("back_button")
                ) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(R.string.back_button)
                    )
                }
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = Neutral100,
                titleContentColor = BrandPrimary48,
                navigationIconContentColor = BrandPrimary48
            )
        )

        ScreenContent(uiState = uiState)
    }
}

@Composable
private fun ScreenContent(
    uiState: UserDetailUiState
) {
    val presentation by uiState.presentation.collectAsStateWithLifecycle()

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .testTag("detail_content"),
        contentPadding = PaddingValues(all = Spacing.SM),
        verticalArrangement = Arrangement.spacedBy(Spacing.SM)
    ) {
        item {
            DetailSection(
                tag = "personal_info_card",
                title = stringResource(R.string.personal_info),
                items = listOf(
                    LabelValueData(
                        label = stringResource(R.string.user_name),
                        value = presentation.name
                    ),
                    LabelValueData(
                        label = stringResource(R.string.user_username),
                        value = presentation.username
                    ),
                    LabelValueData(
                        label = stringResource(R.string.user_email),
                        value = presentation.email
                    ),
                    LabelValueData(
                        label = stringResource(R.string.user_phone),
                        value = presentation.phone
                    ),
                    LabelValueData(
                        label = stringResource(R.string.user_website),
                        value = presentation.website
                    )
                )
            )
        }

        item {
            DetailSection(
                tag = "address_info_card",
                title = stringResource(R.string.address_info),
                items = listOf(
                    LabelValueData(
                        label = stringResource(R.string.user_street),
                        value = presentation.street
                    ),
                    LabelValueData(
                        label = stringResource(R.string.user_suite),
                        value = presentation.suite
                    ),
                    LabelValueData(
                        label = stringResource(R.string.user_city),
                        value = presentation.city
                    ),
                    LabelValueData(
                        label = stringResource(R.string.user_zipcode),
                        value = presentation.zipcode
                    ),
                    LabelValueData(
                        label = stringResource(R.string.user_geo_lat),
                        value = presentation.lat
                    ),
                    LabelValueData(
                        label = stringResource(R.string.user_geo_lng),
                        value = presentation.lng
                    )
                )
            )
        }

        item {
            DetailSection(
                tag = "company_info_card",
                title = stringResource(R.string.company_info),
                items = listOf(
                    LabelValueData(
                        label = stringResource(R.string.company_name),
                        value = presentation.companyName
                    ),
                    LabelValueData(
                        label = stringResource(R.string.company_catchphrase),
                        value = presentation.catchphrase
                    ),
                    LabelValueData(
                        label = stringResource(R.string.company_bs),
                        value = presentation.bs
                    )
                )
            )
        }
    }
}

@Composable
private fun DetailSection(
    tag: String,
    title: String,
    items: List<LabelValueData>
) {
    Card(
        shape = RoundedCornerShape(Size.Micro),
        colors = CardDefaults.cardColors(containerColor = Neutral100),
        elevation = CardDefaults.cardElevation(defaultElevation = Size.Pico),
        modifier = Modifier
            .fillMaxWidth()
            .testTag(tag)
    ) {
        Column(
            modifier = Modifier.padding(all = Spacing.SM)
        ) {
            Text(
                text = title,
                style = FontStyle.bodyMedium.copy(
                    fontSize = FontSize.SM,
                    fontWeight = FontWeight.Bold
                ),
                color = BrandPrimary48,
                modifier = Modifier.padding(bottom = Spacing.SM)
            )

            items.forEachIndexed { index, item ->
                LabelValue(
                    modifier = Modifier.fillMaxWidth(),
                    label = LabelValueText(
                        text = item.label,
                        textStyle = FontStyle.bodyMedium,
                        color = Neutral69
                    ),
                    value = LabelValueText(
                        text = item.value,
                        textStyle = FontStyle.bodyRegular
                    )
                )
                if (index < items.size - 1) {
                    SpacerVertical(dp = Spacing.SM)
                }
            }
        }
    }
}

@Preview
@Composable
private fun DefaultPreview() {
    Screen(
        onActionEvent = {},
        uiState = UserDetailUiState().apply {
            showUserDetail(User.mockUser())
        })
}