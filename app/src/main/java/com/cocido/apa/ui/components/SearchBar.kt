package com.cocido.apa.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color as ComposeColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.cocido.apa.ui.theme.APABlue
import com.cocido.apa.ui.theme.APALightGray
import com.cocido.apa.ui.theme.APAGray
import com.cocido.apa.ui.theme.APADarkBlue

@Composable
fun SearchBar(
    searchText: String,
    onSearchTextChange: (String) -> Unit,
    onFilterClick: () -> Unit = {},
    onClick: (() -> Unit)? = null,
    modifier: Modifier = Modifier,
    showFilters: Boolean = false
) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = 40.dp, max = 44.dp)
            .background(
                ComposeColor.White,
                RoundedCornerShape(16.dp)
            )
            .border(
                1.dp,
                APALightGray,
                RoundedCornerShape(16.dp)
            )
            .then(if (onClick != null) Modifier.clickable(onClick = onClick) else Modifier)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Search,
                    contentDescription = "Buscar",
                    tint = if (searchText.isNotEmpty()) APABlue else APAGray,
                    modifier = Modifier.size(24.dp)
                )
                if (searchText.isEmpty()) {
                    Text(
                        text = "Buscar productos",
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = APAGray
                    )
                } else {
                    Text(
                        text = searchText,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal,
                        color = APADarkBlue,
                        modifier = Modifier.weight(1f)
                    )
                }
            }
            
            if (showFilters) {
                IconButton(
                    onClick = onFilterClick,
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        imageVector = Icons.Filled.FilterList,
                        contentDescription = "Filtros",
                        tint = APALightGray,
                        modifier = Modifier.size(24.dp)
                    )
                }
            }
        }
    }
}
