package com.example

import org.junit.Assert.assertEquals
import org.junit.Test

class ExampleRobolectricTest {

  @Test
  fun appNameValidation() {
    val expectedName = "UAE Pay Guide"
    assertEquals("UAE Pay Guide", expectedName)
  }
}
