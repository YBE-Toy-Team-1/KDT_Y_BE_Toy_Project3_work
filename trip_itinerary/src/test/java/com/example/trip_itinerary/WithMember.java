package com.example.trip_itinerary;

import org.springframework.security.test.context.support.WithSecurityContext;

import java.lang.annotation.Documented;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@WithSecurityContext(factory = WithMemberSecurityFactory.class)
public @interface WithMember {

    String value() default "user";
    String username() default "";
    String password() default "";
}
