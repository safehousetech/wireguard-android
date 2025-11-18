package com.safehouse.core.api

interface IpAddressFetchingListener {
  fun onFetchSuccess(ipAddress: String)
  fun onFetchFailed()
}
