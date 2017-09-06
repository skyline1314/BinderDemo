// BinderInterface.aidl
package com.example.administrator.binderdemo;

// Declare any non-default types here with import statements

interface BinderInterface {
    /**
     * Demonstrates some basic types that you can use as parameters
     * and return values in AIDL.
     */
    void sendMessage(String obj);
    String getMessage();
}
