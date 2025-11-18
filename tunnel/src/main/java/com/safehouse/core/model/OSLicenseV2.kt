package com.safehouse.core.model

class OSLicenseV2(
  val project: String,
  val description: String,
  val version: String,
  val developers: List<String>,
  val url: String,
  val year: String,
  val licenses: List<License>,
  val dependency: String
)

class License(val license: String, val license_url: String)
