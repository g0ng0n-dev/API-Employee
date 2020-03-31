package com.disid.api.departments.utils;


import org.apache.commons.lang3.StringUtils;

public class DepartmentValidators {

    public static void checkIfNameIsValid(String name){

        if (StringUtils.isEmpty(name)){
            throw new IllegalArgumentException("The Name field is mandatory");
        }
    }
}
