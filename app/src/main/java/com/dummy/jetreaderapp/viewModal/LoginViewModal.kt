package com.dummy.jetreaderapp.viewModal

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModal @Inject constructor(): ViewModel() {
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading
    val loadingStateFlow = MutableStateFlow(LoadingState.IDLE)
    val firbaseAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val _loading = MutableLiveData(false)
    val loading: MutableLiveData<Boolean> = _loading

    fun authWithEmailAndPassword(email: String, password: String, mode: String,onCompleteListener: () -> Unit,onFailureListener: (String) -> Unit) {
        viewModelScope.launch {
            when (mode) {
                "login" -> {
                    _isLoading.value = true
                  firbaseAuth.signInWithEmailAndPassword(email,password).addOnSuccessListener {

                  }.addOnSuccessListener {
                      _isLoading.value = false

                      onCompleteListener.invoke()

                      Log.d("firebaseAuth", "authWithEmailAndPassword: Success ")

                  }.addOnFailureListener {
                      onFailureListener.invoke(it.message.toString())
                      Log.d("firebaseAuth", "authWithEmailAndPassword: Failure ")
                  }

                }

                "signup" -> {
                    _isLoading.value = true
                    firbaseAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener {
                        if (it.isSuccessful) {
                            val displayName = it.result.user?.email.toString().split("@")[0]
                            val userId = it.result.user?.uid
                            val user = mutableMapOf<String, Any>()
                            user["user_id"] = userId.toString()
                            user["display_name"] = displayName.toString()

                            FirebaseFirestore.getInstance().collection("users").add(user).addOnSuccessListener {
                                _isLoading.value=false
                                onCompleteListener.invoke()
                            }

                        }

                        Log.d("firebaseAuth", "authWithEmailAndPassword: Success ")
                    }.addOnFailureListener {
                        onFailureListener.invoke(it.message.toString())
                        Log.d("firebaseAuth", "authWithEmailAndPassword: Failure ")
                    }


                }
            }
        }

    }

}