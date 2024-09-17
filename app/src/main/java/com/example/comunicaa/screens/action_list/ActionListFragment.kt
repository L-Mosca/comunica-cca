package com.example.comunicaa.screens.action_list

import android.view.LayoutInflater
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import com.example.comunicaa.base.BaseFragment
import com.example.comunicaa.databinding.FragmentActionListBinding
import com.example.comunicaa.screens.host.HostViewModel
import com.example.comunicaa.utils.onBackPressed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ActionListFragment : BaseFragment<FragmentActionListBinding>() {

    override val bindingInflater: (LayoutInflater) -> FragmentActionListBinding =
        FragmentActionListBinding::inflate
    override val viewModel: ActionListViewModel by viewModels()
    private val hostViewModel: HostViewModel by activityViewModels()

    override fun initViews() {
        setupBackAction()
    }

    override fun initObservers() {}

    private fun setupBackAction() {
        onBackPressed(
            action = { requireActivity().moveTaskToBack(true) },
            closeDrawer = { hostViewModel.hideDrawer() },
            drawerIsOpen = { hostViewModel.drawerIsOpen.value ?: false }
        )
    }
}