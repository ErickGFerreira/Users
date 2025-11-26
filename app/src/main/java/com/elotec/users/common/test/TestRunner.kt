package com.elotec.users.common.test

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.TestScope

interface TestRunner {
    fun prepareObservers(block: suspend CoroutineScope.() -> Unit)

    fun runTest(
        testDispatcher: TestDispatcher,
        testBody: suspend TestScope.() -> Unit,
    )

}