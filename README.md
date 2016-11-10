# Soundify - Data transmission via sound waves


Soundify Library tries to offer to you the data transmission via sound waves. This library specifically makes the transformation and transmission of certain information into sound waves in the application that it is implemented and also makes the receipt of the same information transmitted. 
It uses the same concept as [Chirp.io](https://www.chirp.io/) technology.

Support for Android 4.0.3 and up.

Feel free to fork or issue pull requests on github. Issues can be reported on the github issue tracker.


Soundify | 
---- | ----
![Soundify](https://github.com/RF177/Soundify/blob/master/gem2.gif) | 

## Table of Contents
1. [Setup](#setup)
2. [Using Soundify](#using-soundify)
  1. [Initialize a Instance](#Initialize-a-instance)	
  2. [Send Data](#send-data)
  3. [Receive Data](#receive-data-listener)
  4. [Others](#others-configs)
3. [Additional Options](#additional-options)
4. [FAQ](#faq)
5. [Potential Improvements](#potential-improvements)
6. [License](#license)


## Setup
The easiest way to add the Soundify library to your project is by adding it as a dependency to your `build.gradle`
```java
dependencies {
  compile 'com.github.rf177.soundify:soundify:0.0.1'
}
```

To declare that your app requires audio permissions, add the following to your `AndroidManifest.xml`, inside the bottom of the `<manifest>` element.
``` java
<uses-permission android:name="android.permission.RECORD_AUDIO"/>
```

## Initialize a Instance
The first step is to initialize a Soundify library instance, and pass as a parameter the context of your Activity.
```java
Soundify soundify = new Soundify(this);
```

## Send Data

```java
soundify.send(Soundify.stringToBytes(msg));
```

## Receive Data
For a basic implementation to receive data, you'll need to:

1. Start listening;
2. Implement an `SoundifyListener`.
3. Convert bytes receive in your data type.
4. Use the data you received the way you want it.

In order to receive the data, you will need to implemente the `SoundifyListener`, typically this will be the `Activity` or `Fragment`.

```java
soundify.startListening();
soundify.setSoundifyListener((data) -> {
	String stringData = Soundify.bytesToString(data);// Example of convert bytes to string
	// Use the data you received the way you want it	
});
```
