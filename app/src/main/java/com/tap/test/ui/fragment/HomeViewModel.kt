package com.tap.test.ui.fragment

import androidx.lifecycle.ViewModel
import com.tap.utils.ThreadManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val threadManager: ThreadManager
) : ViewModel() {

    private val className = ""

}