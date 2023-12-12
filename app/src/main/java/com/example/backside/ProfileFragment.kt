package com.example.backside

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.backside.utils.SessionManager

class ProfileFragment : Fragment() {

    companion object {
        fun newInstance(): ProfileFragment {
            return ProfileFragment()
        }
    }

    private lateinit var sessionManager: SessionManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sessionManager = SessionManager(requireContext())
    }

    private fun openFragment(fragment: Fragment) {
        requireActivity().supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                com.google.android.material.R.anim.design_bottom_sheet_slide_in,
                com.google.android.material.R.anim.design_bottom_sheet_slide_out
            )
            .replace(R.id.fragmentprofile, fragment, fragment.javaClass.simpleName)
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        var fragment = VoteBookProfileFragment.newInstance()
        val votebooks = view.findViewById<FrameLayout>(R.id.votebooks)
        val requestedbooks = view.findViewById<FrameLayout>(R.id.requestedbooks)
        val create = view.findViewById<FrameLayout>(R.id.createvote)
        val editprofil = view.findViewById<FrameLayout>(R.id.editprofile)
        var doubleBackPressedOnce = false

        requireActivity().onBackPressedDispatcher.addCallback(viewLifecycleOwner, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (!doubleBackPressedOnce) {
                    Toast.makeText(requireContext(), "Press again to exit", Toast.LENGTH_SHORT).show()
                    doubleBackPressedOnce = true
                    Handler(Looper.getMainLooper()).postDelayed({
                        doubleBackPressedOnce = false
                    }, 2000)
                } else {
                    requireActivity().finish()
                }
            }
        })

        requestedbooks.setOnClickListener{
            requestedbooks.setBackgroundResource(R.drawable.radiusbutton)
            votebooks.setBackgroundResource(R.drawable.radiusbutton2)
            var frg = RequestBookProfileFragment.newInstance()
            openFragment(frg)

        }

        votebooks.setOnClickListener{
            requestedbooks.setBackgroundResource(R.drawable.radiusbutton2)
            votebooks.setBackgroundResource(R.drawable.radiusbutton)
            var  frg = VoteBookProfileFragment.newInstance()
            openFragment(frg)

        }

        openFragment(fragment)

        //pindah ke buat
        create.setOnClickListener {
            val intent = Intent(requireContext(), CreateVotingUserActivity::class.java)
            startActivity(intent)
        }

        //pindah ke edit
        editprofil.setOnClickListener {
            val intent = Intent(requireContext(), EditProfileActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
