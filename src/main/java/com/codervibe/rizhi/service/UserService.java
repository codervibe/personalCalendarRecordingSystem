package com.codervibe.rizhi.service;

import com.codervibe.rizhi.model.User;

public interface UserService {

    public User findUser(String usercode, String password);

    public String findUserName(Integer  user_id);

    public String findUserPicture(Integer user_id);

    public void addUser(String usercode,String password);

}
