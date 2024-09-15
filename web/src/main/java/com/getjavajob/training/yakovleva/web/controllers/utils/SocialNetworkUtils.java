package com.getjavajob.training.yakovleva.web.controllers.utils;

import com.getjavajob.training.yakovleva.common.Account;
import com.getjavajob.training.yakovleva.common.Phone;
import com.getjavajob.training.yakovleva.common.utilsEnum.PhoneType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

public class SocialNetworkUtils {
    private static final Logger logger = LogManager.getLogger(SocialNetworkUtils.class);

    public List<Phone> convertPhones(String phone, String typePhone, Account account) {
        logger.info("convertPhones(phone = {}, typePhone = {}, account = {})", phone, typePhone, account);
        List<Phone> phones = new ArrayList<>();
        Phone newPhone = new Phone();
        newPhone.setPhoneNumber(phone);
        newPhone.setPhoneType(PhoneType.valueOf(typePhone));
        newPhone.setAccountId(account.getId());
        phones.add(newPhone);
        return phones;
    }

    public String getPhoto(byte[] photo) {
        String encodedPhoto = "";
        if (photo != null) {
            byte[] encodedPhotoBytes = Base64.getEncoder().encode(photo);
            try {
                encodedPhoto = new String(encodedPhotoBytes, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                logger.error("UnsupportedEncodingException - ", e);
            }
        }
        return encodedPhoto;
    }

    public List<String> getPhotos(List<Account> accounts) {
        List<String> encodedPhotos = new ArrayList<>();
        for (Account account : accounts) {
            encodedPhotos.add(getPhoto(account.getAccountPhoto().getPhoto()));
        }
        return encodedPhotos;
    }

}