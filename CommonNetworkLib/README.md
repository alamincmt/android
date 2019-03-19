# This is a very common network library to call GET and POST API

# How to add this library to Android Studio Project - 

**Step 1:** Add the JitPack repository to your build file
Add it in your root/project level build.gradle at the end of repositories:

    allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}

**Step 2:** Add the dependency

    dependencies {
	        implementation 'com.github.alamincmt:CommonNetworkLibrary:v1.0.2'
	  }
    
**That's it!** **Sync to finish**

