package org.data.redroleplay.enums;

// Authority is like role , I just like to call it authority

public enum Authority {
    SIMPLE_ACCESS, // This is the default authority for all users , if you remove this they will not be able to access the website
    ADMIN,
    WHITELISTER,
    MANAGER

}
