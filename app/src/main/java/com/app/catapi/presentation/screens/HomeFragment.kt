package com.app.catapi.presentation.screens

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.app.catapi.R
import com.app.catapi.databinding.FragmentHomeBinding
import com.app.catapi.model.CatItem
import com.app.catapi.presentation.adapters.CatAdapter
import com.app.catapi.presentation.viewmodel.HomeViewModel
import kotlinx.coroutines.flow.collectLatest
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = requireNotNull(_binding)

    private val homeViewModel: HomeViewModel by viewModel()

    private val catAdapter: CatAdapter by lazy(LazyThreadSafetyMode.NONE) {
        CatAdapter { catItem: CatItem ->
            findNavController().navigate(
                R.id.action_homeFragment_to_detailFragment,
                bundleOf(ITEM_ARG_NAME to catItem)
            )
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {

            recyclerView.apply {
                layoutManager = GridLayoutManager(requireContext(), SPAN_COUNT)
                recyclerView.adapter = catAdapter
            }

            viewLifecycleOwner.lifecycleScope.launchWhenStarted {
                homeViewModel.data.collectLatest {
                    catAdapter.submitData(it)
                }
            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private companion object {
        private const val SPAN_COUNT = 2
        private const val ITEM_ARG_NAME = "detail_item"
    }
}