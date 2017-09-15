package com.binary.hdwallpaper.di;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by duong on 9/12/2017.
 */
@Scope
@Documented
@Retention(value = RetentionPolicy.RUNTIME)
public @interface ActivityScope {
}
