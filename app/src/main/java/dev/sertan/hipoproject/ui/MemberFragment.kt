package dev.sertan.hipoproject.ui

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import dev.sertan.hipoproject.R
import dev.sertan.hipoproject.databinding.FragmentMemberBinding

private const val GITHUB = "https://github.com"

internal class MemberFragment : Fragment() {
    private val args by navArgs<MemberFragmentArgs>()

    private var _binding: FragmentMemberBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMemberBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpComponents()
    }

    private fun setUpComponents(): Unit = binding.run {
        // To use a specific photo, remove next line from comment
        // Picasso.get().load('MEMBER PHOTO URL').into(imageViewMember)

        val member = args.member
        textViewName.text = getString(R.string.member_name_row, member.name, member.age)
        textViewLocation.text = getString(R.string.member_location_row, member.location)
        textViewPosition.text = getString(R.string.member_position_row, member.hipoInfo?.position)
        textViewYears.text = getString(R.string.member_year_row, member.hipoInfo?.yearsInHipo)
        buttonGitHub.text = getString(R.string.member_github_button, member.github)

        buttonGitHub.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW)
            intent.data = Uri.parse("$GITHUB/${member.github}")
            startActivity(intent)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
