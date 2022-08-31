repositories {
  mavenCentral()
  mavenLocal()
}

plugins {
  kotlin("jvm") version "1.7.20"
  id("com.adarshr.test-logger") version "3.2.0"
}

dependencies {
  implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
  implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
  testImplementation(kotlin("test"))
}

sourceSets {
  main {
    java.setSrcDirs(listOf("main"))
  }
  test {
    java.setSrcDirs(listOf("test"))
  }
}

kotlin {
  jvmToolchain {
    languageVersion.set(JavaLanguageVersion.of(17))
  }
}

tasks.test {
  useJUnitPlatform()
}
