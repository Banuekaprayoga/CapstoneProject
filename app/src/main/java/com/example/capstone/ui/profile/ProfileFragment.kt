package com.example.capstone.ui.profile

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.capstone.R
import com.example.capstone.databinding.FragmentProfileBinding
import com.example.capstone.ui.utils.ViewModelFactory

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<ProfileViewModel> {
        ViewModelFactory.getInstance(requireActivity())
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(inflater, container, false)
        val root: View = binding.root
        setupAction()
        setupView()
        return root
    }

    private fun setupAction() {
        binding.imgLogout.setOnClickListener {
            AlertDialog.Builder(requireActivity()).apply {
                setTitle(getString(R.string.logout))
                setMessage(getString(R.string.success_logout))
                setPositiveButton(getString(R.string.yes_alert)) { _, _ ->
                    viewModel.logout()
                }
                setNegativeButton(getString(R.string.no_alert)) { dialog, _ ->
                    dialog.dismiss()
                }
                create()
                show()
            }
        }
        binding.btnEdit.setOnClickListener {
            val intent = Intent(requireActivity(), EditProfileActivity::class.java)
            startActivity(intent)
        }
    }

    private fun setupView() {
        viewModel.getProfile().observe(viewLifecycleOwner) { user ->
            binding.apply {
                imgProfile.setImageURI(user.photoUri.toUri())
                nameTextView.text = user.name
                emailTextView.text = user.email
                genderTextView.text = user.gender
            }
        }
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}