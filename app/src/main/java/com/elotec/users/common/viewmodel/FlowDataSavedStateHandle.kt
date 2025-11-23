package com.elotec.users.common.viewmodel

import androidx.lifecycle.SavedStateHandle
import java.io.Serializable

interface BaseFlowData : Serializable

class FlowDataSavedStateHandle<T : BaseFlowData>(
    private val handle: SavedStateHandle,
    private val key: String
) {

    fun init(default: T): T {
        val currentSavedFlowData = handle.get<T>(key)
        val flowData = currentSavedFlowData ?: default
        if (currentSavedFlowData == null) save(flowData)
        return flowData
    }

    fun save(flowData: T) {
        handle[key] = flowData
    }

}