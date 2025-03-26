package com.example.mylistview;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<List<User>> users = new MutableLiveData<List<User>>();

    public UserViewModel() {
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }

    public void addUser(User user) {
        List<User> currentUser = new ArrayList<>(users.getValue());
        currentUser.add(user);
        users.setValue(currentUser);
    }
}


