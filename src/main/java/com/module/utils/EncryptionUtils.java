package com.module.utils;

import com.module.model.Users;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * Created by mhesmkhani on 7/29/2020.
 */
public interface EncryptionUtils {

    String PasswordEncrypt(Users users);

}
