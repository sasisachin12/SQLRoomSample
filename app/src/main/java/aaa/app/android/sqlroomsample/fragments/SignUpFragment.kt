package aaa.app.android.sqlroomsample.fragments

import aaa.app.android.sqlroomsample.R
import aaa.app.android.sqlroomsample.databinding.FragmentSignUpBinding
import aaa.app.android.sqlroomsample.viewmodel.SignUpViewModel
import android.os.Bundle
import android.util.Patterns
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.hbb20.CountryCodePicker
import kotlinx.android.synthetic.main.activity_home.*


/**
 * Created by Richard Uzor  on 19/09/2022
 */

class SignUpFragment : Fragment(), CountryCodePicker.OnCountryChangeListener {

    private lateinit var signUpViewModel: SignUpViewModel

    private lateinit var mSignUpUserName: String
    private lateinit var mSignUpEmail: String
    private lateinit var mSignUpPassword: String
    private lateinit var mSignUpConfirmPassword: String
    private lateinit var mSignUpPhoneNumber: String
    private lateinit var mCountryCodePicker: CountryCodePicker
    private lateinit var phoneNumberCode: String



    //set view binding for this class
    private var _binding: FragmentSignUpBinding? = null
    private val binding: FragmentSignUpBinding?
        get() = _binding //we use this get method for the binding cos we want the binding to initialize only when called


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        //initialize class viewModel
        signUpViewModel = ViewModelProvider(requireActivity())[SignUpViewModel::class.java]
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_up, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding = FragmentSignUpBinding.bind(view)


        with(binding){
            mCountryCodePicker = this!!.signUpCountryCodePicker

            this.btnSignUp.setOnClickListener {

                //attach country code to entered phone number
                mCountryCodePicker.registerCarrierNumberEditText(signUpPhoneNumber)

                mSignUpUserName = signUpUserName.text.toString().trim()
                mSignUpEmail = signUpEmail.text.toString().trim()
                mSignUpPassword = signUpPassword.text.toString().trim()
                mSignUpConfirmPassword = signUpConfirmPassword.text.toString().trim()
                mCountryCodePicker.setOnCountryChangeListener(this@SignUpFragment)
                mSignUpPhoneNumber = mCountryCodePicker.fullNumberWithPlus


                validateInput()
            }

        }

    }

    //perform validation on input data
    private fun validateInput(){
        with(binding) {
            //disable error on all input layouts at first
            this!!.textInputLayoutSignUpUserName.error = ""
            this.textInputLayoutSignUpEmail.error = ""
            this.textInputLayoutSignUpUserName.error = ""
            this.textInputLayoutSignUpPassword.error = ""
            this.textInputLayoutSignUpConfirmPassword.error = ""
            this.textInputLayoutSignUpPhoneNumber.error = ""
            // if the username field is empty we display error messages
            if (mSignUpUserName.isEmpty()) {
                this.textInputLayoutSignUpUserName.error = "User Name Required"
                this.signUpUserName.requestFocus()
                return
            }
            // if the email field is empty we display error messages
            else if (mSignUpEmail.isEmpty()) {
                this.textInputLayoutSignUpEmail.error = "Email Required"
                this.signUpEmail.requestFocus()
                return
            }

            //if the email format does not does match that as defined, we display error messages
            else if (!Patterns.EMAIL_ADDRESS.matcher(mSignUpEmail).matches()) {
                this.textInputLayoutSignUpEmail.error = "Valid Email Required"
                this.signUpEmail.requestFocus()
                return
            }
            //if the password contains less than 6 characters we display error message
            else if (mSignUpPassword.isEmpty() || mSignUpPassword.length < 6) {
                this.textInputLayoutSignUpPassword.error = "6 char password required"
                this.signUpPassword.requestFocus()
                return
            }
            //if the confirm password does not correspond with the password we display error message
            else if (mSignUpConfirmPassword != mSignUpPassword) {
                this.textInputLayoutSignUpConfirmPassword.error = "Password Does Not Match"
                this.signUpConfirmPassword.requestFocus()
                return
            }
            //if the phone number field is empty we display error message
            else if (this.signUpPhoneNumber.text.toString().isEmpty()){
                this.textInputLayoutSignUpPhoneNumber.error = "Phone Number Required"
                this.signUpPhoneNumber.requestFocus()
                return
            }
            else{
                //if all validations are passed, we register user
                registerUser(mSignUpEmail, mSignUpPassword)
            }
        }
    }

    //function to register user
    //takes the entered email and password as parameters (for now)
    private fun registerUser(email: String, password: String) {
        Toast.makeText(requireContext(), "Input Valid!", Toast.LENGTH_SHORT).show()

    }

    override fun onCountrySelected() {
        //fetch the selected country code
        phoneNumberCode = mCountryCodePicker.selectedCountryCode
    }

    override fun onResume() {
        super.onResume()
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
    }

    override fun onStop() {
        super.onStop()
        (requireActivity() as AppCompatActivity).supportActionBar?.show()
    }

    override fun onDestroy() {
        super.onDestroy()
        //release memory when view is destroyed
        _binding = null
    }

}