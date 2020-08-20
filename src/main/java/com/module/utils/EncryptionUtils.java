package com.module.utils;

import com.module.model.Users;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by mhesmkhani on 7/29/2020.
 */
public interface EncryptionUtils {

    String PasswordEncrypt(String password);
    String TokenGeneration(int count);
}
