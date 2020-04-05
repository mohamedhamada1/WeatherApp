/*
 *  Copyright (C) 2017 MINDORKS NEXTGEN PRIVATE LIMITED
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      https://mindorks.com/license/apache-v2
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License
 */

package uae.weather.myapplication.ui.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModel
import dagger.android.support.AndroidSupportInjection



abstract class BaseFragment<T : ViewDataBinding, V : ViewModel> : Fragment(){

    var baseActivity: BaseActivity<*, *>? = null
        private set
    var viewDataBinding: T? = null
        private set
    private var mViewModel: V? = null
    private var mRootView: View? = null



    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract val viewModel: V



    /**
     * @return layout resource id
     */
    @get:LayoutRes
    abstract val layoutId: Int


    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = viewModel
        setHasOptionsMenu(false)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewDataBinding = DataBindingUtil.inflate(inflater, layoutId, container, false)
        mRootView = viewDataBinding!!.root
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewDataBinding!!.executePendingBindings()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
            activity!!.onFragmentAttached()
        }
    }


    fun setViewDataBinding(viewDataBinding: T?) {
        this.viewDataBinding = viewDataBinding
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }


    interface Callback {

        fun onFragmentAttached()

        fun onFragmentDetached(tag: String)
    }


}
