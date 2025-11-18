package com.safehouse.core.api

object Headers {
    val CONTENT_TYPE = "Content-Type"
    val ACCEPT = "Accept"
    val ACCEPT_LANGUAGE = "Accept-Language"
    val AUTHORIZATION = "Authorization"
    val APP_VERSION = "X-App-Version"
    val REFRESH_TOKEN = "x-refresh-token"
    val USER_IP = "x-forwarded-for"

    object Values {
        val APPLICATION_JSON = "application/json"
    }
}