package com.example.ducvietho.moki.data.resource.remote.api;



import com.example.ducvietho.moki.data.model.User;
import com.example.ducvietho.moki.data.model.UserResponse;
import com.example.ducvietho.moki.data.resource.remote.UserDataResource;
import com.example.ducvietho.moki.data.resource.remote.api.service.MokiApi;

import io.reactivex.Observable;
import io.reactivex.functions.Function;

/**
 * Created by ducvietho on 11/22/2017.
 */

public class UserRemoteDataResource extends BaseRemoteDataResource implements UserDataResource {
    public UserRemoteDataResource(MokiApi api) {
        super(api);
    }

    @Override
    public Observable<UserResponse> userLogin(String phonenumber, String pass) {
        return mApi.userLogin(phonenumber, pass);
    }

    @Override
    public Observable<User> getUserInfor(int idUser) {
        return mApi.getUserInfor(idUser).map(new Function<UserResponse, User>() {
            @Override
            public User apply(UserResponse userResponse) throws Exception {
                return userResponse.getUser();
            }
        });
    }
}
