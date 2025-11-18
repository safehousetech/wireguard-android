package com.safehouse.core.vpn

import com.safehouse.crypto.KeyPair

object WireguardUtils {

  fun generatePublicAndPrivateKey(): Pair<String, String> {
    val keyPair = KeyPair()
    return Pair(keyPair.publicKey.toBase64(), keyPair.privateKey.toBase64())
  }
}