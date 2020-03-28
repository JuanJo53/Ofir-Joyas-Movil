package com.example.ofir_joyas_movil.ui.joyas;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class JoyasViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public JoyasViewModel() {
        mText = new MutableLiveData<>();
    }

    public LiveData<String> getText() {
        return mText;
    }
}