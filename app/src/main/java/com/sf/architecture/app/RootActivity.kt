package com.sf.architecture.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sf.architecture.data.FakeMessageDatabase
import com.sf.architecture.data.FakeMessageNetwork
import com.sf.architecture.domain.RealMessageUseCase
import com.sf.architecture.repo.RealMessageRepo
import com.sf.architecture.workflow.hello.GetMessageWorker
import com.sf.architecture.workflow.hello.InvertMessageWorker
import com.sf.architecture.workflow.root.GetAuthWorker
import com.sf.architecture.workflow.root.RootWorkflow
import com.squareup.workflow1.ui.WorkflowLayout
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.renderWorkflowIn
import kotlinx.coroutines.flow.StateFlow

@OptIn(WorkflowUiExperimentalApi::class)
class RootActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        (applicationContext as RootApplication).appComponent.inject(this)
        super.onCreate(savedInstanceState)

        // This ViewModel will survive configuration changes. It's instantiated
        // by the first call to viewModels(), and that original instance is returned by
        // succeeding calls.
        val model: RootViewModel by viewModels()
        setContentView(
            WorkflowLayout(this).apply { start(model.renderings) }
        )
    }
}

class RootViewModel(savedState: SavedStateHandle) : ViewModel() {
    @OptIn(WorkflowUiExperimentalApi::class)
    val renderings: StateFlow<Any> by lazy {
        val useCase = RealMessageUseCase(
            RealMessageRepo(
                viewModelScope,
                FakeMessageNetwork(),
                FakeMessageDatabase()
            )
        )

        renderWorkflowIn(
            workflow = RootWorkflow(viewModelScope, GetAuthWorker(), GetMessageWorker(useCase), InvertMessageWorker(useCase)),
            scope = viewModelScope,
            savedStateHandle = savedState
        )
    }
}


