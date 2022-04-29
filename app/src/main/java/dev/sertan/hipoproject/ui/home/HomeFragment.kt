package dev.sertan.hipoproject.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint
import dev.sertan.hipoproject.data.model.HipoInfo
import dev.sertan.hipoproject.data.model.Member
import dev.sertan.hipoproject.databinding.FragmentHomeBinding

@AndroidEntryPoint
internal class HomeFragment :
    Fragment(),
    MemberAdapter.MemberListener,
    SearchView.OnQueryTextListener {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<HomeViewModel>()
    private val memberAdapter: MemberAdapter by lazy { MemberAdapter(this) }

    private val member = Member(
        name = "Sertan Canpolat",
        age = 22,
        location = "Elazığ",
        github = "scnplt",
        hipoInfo = HipoInfo("Intern", 0)
    )

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
        setUpComponents()
        subscribeUi()
    }

    private fun setUpComponents(): Unit = binding.run {
        recyclerViewMembers.adapter = memberAdapter
        searchView.setOnQueryTextListener(this@HomeFragment)
        buttonAddMember.setOnClickListener { viewModel.addMember(member) }
    }

    override fun onQueryTextSubmit(queryText: String?): Boolean = false

    override fun onQueryTextChange(queryText: String?): Boolean {
        memberAdapter.filter.filter(queryText)
        return false
    }

    private fun subscribeUi() = viewModel.serviceResponse.observe(viewLifecycleOwner) { state ->
        val showProgressIndicator = {
            binding.buttonAddMember.visibility = View.GONE
            binding.nestedScrollView.visibility = View.GONE
            binding.progressIndicator.visibility = View.VISIBLE
        }

        val hideProgressIndicator = {
            binding.progressIndicator.visibility = View.GONE
            binding.buttonAddMember.visibility = View.VISIBLE
            binding.nestedScrollView.visibility = View.VISIBLE
        }

        when {
            state.isLoading -> showProgressIndicator()
            state.isSuccess -> {
                memberAdapter.data = state.data?.members ?: emptyList()
                hideProgressIndicator()
            }
            state.isFailure -> {
                hideProgressIndicator()
                Toast.makeText(
                    requireContext(),
                    state?.exception?.localizedMessage ?: return@observe,
                    Toast.LENGTH_LONG
                ).show()
            }
            else -> hideProgressIndicator()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onMemberClicked(member: Member) {
        val direction = HomeFragmentDirections.actionHomeToMember(member)
        findNavController().navigate(direction)
    }
}
