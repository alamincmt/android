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

# How to call POST method API 
	NetRequest netRequest = new NetRequest(context);
	netRequest.setNetLibListener(new NetRequest.NetLibListener() {
	    @Override
	    public void onError(String message) {
	    	// Handle error here
		Log.d("onError", message);
	    }

	    @Override
	    public void onSuccess(final JSONObject jsonObject) {
	    	// Parse your jsonObject and user where you want to
		Log.d("onSuccess", jsonObject.toString());
	    }

	    @Override
	    public void onFailure(String message, int errorCode) {
		Log.d("onFailure", message);
	    }

	    @Override
	    public void onNetworkFailed(String message) {
		Log.d("onNetworkFailed", message);
	    }
	});
	HashMap<String, String> hashMap = new HashMap<>();
	hashMap.put("name", "morpheus");
	hashMap.put("job", "leader");
	
	// Here you can add params or you can pass null if you don't want pass any params. 
	netRequest.POST("api_url", null);

# How to call GET method API 
	NetRequest netRequest = new NetRequest(MainActivity.this);
	netRequest.setNetLibListener(new NetRequest.NetLibListener() {
	    @Override
	    public void onError(String message) {
		Log.d("onError", message);
	    }

	    @Override
	    public void onSuccess(final JSONObject jsonObject) {
	    	// Parse your jsonObject and user where you want to
		Log.d("onSuccess", jsonObject.toString());
	    }

	    @Override
	    public void onFailure(String message, int errorCode) {
		Log.d("onFailure", message);
	    }

	    @Override
	    public void onNetworkFailed(String message) {
		Log.d("onNetworkFailed", message);
	    }
	});
	
	// Here you can add params or you can pass null if you don't want pass any params. 
	netRequest.GET("https://reqres.in/api/users", null);

