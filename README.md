# WebServiceAPI [![Build Status](https://travis-ci.org/FoxDenStudio/WebServiceAPI.svg?branch=master)](https://travis-ci.org/FoxDenStudio/WebServiceAPI)
A [SpongeForge](https://github.com/SpongePowered/SpongeForge) Plugin and API that allows for quick and easy web hosting and url mapping.

## Why?
In the past few years, countless plugins have been made for Bukkit and Spigot that have attempted to have a web interface or login, one of the most common plugins being DynMap.
The issue with almost all these plugins is that they often conflicted on ports, or had hard coded urls in them.
This Plugin/API allows for incredibly easy hosting and linking of various web data.

## How?
This plugin uses a system of annotations and interfaces to allow for plugins to be able to use this feature, yet not have a requirement for it.
Essentially in your plugins initialization method, you get the SimpleRegistrationService and then register your plugin using the following data:

1. PluginManager instance - PluginManager
2. Plugin ID - String
3. Plugin Web Root (relative to domain) - String
4. A list of instantiates classes that contains the methods (see more on this in the getting started section)

After your plugin is registered, whenever someone attempts to visit the website: \<yourdomain/ip\>:\<port\>/\<pluginWebRoot\>/*, this plugin/API will automatically find the method with the corresponding name and run it (again, more on this in the next section)

Information on Sponge Services can be found [here](https://docs.spongepowered.org/en/plugin/services.html)

## Getting Started
The first thing that you will need to do is reference this plugin, be it from the compiled sources, or from the sources.
Once that is complete, we can start adding the needed code to your plugin.

The first step is to make a class that will be your main listening class. For this example I will be using a class named `TestHandlerClass`.
Inside this class we will make a method, you can name it whatever you want, but it MUST return an `IWebServiceResponse` object, and take a single parameter object: `IWebServiceRequest`
We will then annotate the method with the `RequestHandler` annotation, which takes one required and one optional parameter.
    The required parameter is the name, and that is the part of the path that is \<yourdomain/ip\>:\<port\>/\<pluginWebRoot\>/`<name/path>`
    
You can do this as many times you want, one for each page.
In a future version, query parameters will be added to the `IWebServiceRequest` interface

Now that you have your listener, you need to tell the api where to look for the methods.  

The first thing you should do is add a soft dependency to the `fds-wsapi` plugin.

Next,you need to get the service and register your plugin in a `GameInitializationEvent` (or similar).

```java
WSAPIMainClass wsapiMainClass = (WSAPIMainClass) pluginManager.getPlugin("fds-wsapi").get().getInstance();
wsapiMainClass.registerPlugin("ffds-wsapi-test", "test", new TestHandlerClass());
```

An example of this code in action can be found inside the WebServiceAPI-Test directory.


# PLEASE REMEMBER
This is still in BETA development, it will change a lot.....
Any questions can be sent to npjoshf@gmail.com or can be posted in the issue tracker.