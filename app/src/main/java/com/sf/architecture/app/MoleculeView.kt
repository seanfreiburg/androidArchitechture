package com.sf.architecture.app

import androidx.compose.runtime.Composable
import kotlinx.coroutines.flow.MutableSharedFlow

interface MoleculeView<VE: Any, VM: Any> {
    fun setEventReceiver(events: MutableSharedFlow<VE>)

    @Composable
    fun Render(viewModel: VM)
}
