package com.sf.architecture.app

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.Flow

interface MoleculePresenter<VE: Any, VM: Any> {
    @Composable
    fun viewModel(events: Flow<VE>): VM
}