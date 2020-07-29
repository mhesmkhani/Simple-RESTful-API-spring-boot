package com.module.utils;

import com.module.model.Users;

/**
 * Created by mhesmkhani on 7/29/2020.
 */
public interface EncryptionUtils {

    String PasswordEncrypt(Users users);
    String TokenGeneration(int count);
}
