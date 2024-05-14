package com.gpersist.utils;

import java.lang.annotation.*;

import com.gpersist.enums.*;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuthMethod {
    String Menus() default "";

    MenuAuth Auth() default MenuAuth.Browse;

    ActionOutType OutType() default ActionOutType.Bean;
}
