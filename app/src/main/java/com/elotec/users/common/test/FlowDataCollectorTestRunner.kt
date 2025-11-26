package com.elotec.users.common.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope

class FlowCollectorsTestRunner : TestRunner {
    internal var collectorsJob: Job? = null
        private set

    private var flowCollectors: suspend CoroutineScope.() -> Unit = {}

    override fun prepareObservers(block: suspend CoroutineScope.() -> Unit) {
        this.flowCollectors = block
    }

    override fun runTest(
        testDispatcher: TestDispatcher,
        testBody: suspend TestScope.() -> Unit,
    ) = kotlinx.coroutines.test.runTest {
        collectorsJob = launch(context = testDispatcher, block = flowCollectors)
        testBody()
        collectorsJob?.cancel()
    }
}