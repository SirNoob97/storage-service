package com.sirnoob97.storageservice.util;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

public class RandomValueGenerator {

  public static String randomString() {
    return UUID.randomUUID().toString();
  }

  public static long randomLong() {
    return ThreadLocalRandom.current().nextLong();
  }

  public static byte[] randomByteArray() {
    var bytes = new byte[10];
    ThreadLocalRandom.current().nextBytes(bytes);
    return bytes;
  }
}
