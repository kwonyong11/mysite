package com.bitacademy.security;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.SOURCE)
public @interface Auth {
	public String value() default "";
	public Role role() default Role.USER;
}
