package com.example.ducvietho.moki.data.resource.remote;





import com.example.ducvietho.moki.data.model.User;
import com.example.ducvietho.moki.data.model.UserResponse;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 11/22/2017.
 */

public class UserDataRepository implements UserDataResource {
    private UserDataResource mResource;

    public UserDataRepository(UserDataResource resource) {
        mResource = resource;
    }

    @Override
    public Observable<UserResponse> userLogin(String phonenumber, String pass) {
        return mResource.userLogin(phonenumber,pass);
    }

    @Override
    public Observable<User> getUserInfor(int idUser) {
        return mResource.getUserInfor(idUser);
    }
}
