package com.example.ducvietho.moki.data.resource.remote;





import com.example.ducvietho.moki.data.model.User;
import com.example.ducvietho.moki.data.model.UserResponse;

import io.reactivex.Observable;

/**
 * Created by ducvietho on 11/22/2017.
 */

public interface UserDataResource {
    Observable<UserResponse> userLogin(String phonenumber, String pass);
    Observable<User> getUserInfor(int idUser);
}
