<img src="logo.png" width="100">

# TOTI

[![](https://jitpack.io/v/ondrej-nemec/MVC.svg)](https://jitpack.io/#ondrej-nemec/MVC)
[![MIT License](http://img.shields.io/badge/license-MIT-green.svg) ](https://github.com/ondrej-nemec/MVC/blob/master/LICENSE)

TOTI - **T**ip **O**f **T**he **I**ceberg - is Java web framework. Provide HTTP(S) interface between users and your Java application. With TOTI you can create Rest API, generate HTML pages from templates or just web server (like f.e. Apache but begging)

Main purpose of this framework is create GUI for java servers f.e. in Industry 4.0 or IoT. TOTI framework can be used as MVC framework (like Symfony or Nette in PHP) but **this is not main purpose**.

* [Include in your project](#include-in-your-project)
* [Get started](#get-started)
	* [Initialize](#initialize)
	* [Modules](#modules)
	* [Tasks](#tasks)
	* [Controllers](#controllers)
	* [Templates](#templates)
	* [Grids](#grids)
	* [Forms](#forms)
	* [Routing](#routing)
	* [Responses](#responses)
	* [Request parameters validation](#request-parameters-validation)
	* [Injections](#injections)
	* [Permissions](#permissions)
* [How to do](#how-to-do)
	* [Login](#login)
	* [Change language](#change-language)
	* [Controllers method for grid and form](#controllers-method-for-grid-and-form)
* Documentation
	* [List of filters and inputs](doc/controls.md)
	* [List of template tags](doc/tags.md)
	* [Validation rules](doc/validation-rules.md)
	
## Include in your project

TOTI uses for publication <a href="https://jitpack.io/">JitPack</a>. Allow you include this project by using Gradle or Maven.

### Include using Gradle

Add this line to repositories section:

```gradle
maven { url 'https://jitpack.io' }
```
And this line to dependencies:

```gradle
implementation 'com.github.ondrej-nemec:TOTI:Tag'
```

If you would like use TOTI templating system on computer without JDK (JRE only) you have to add this to dependencies, too:

```gradle
compile files("${System.properties['java.home']}/../lib/tools.jar")
```

## Get started

### Initialize

#### Quick initialization

#### Custom initialization

### Modules

### Tasks

### Controllers

#### Routing

#### Responses

#### Request parameters validation

#### Injections

#### Permissions

### Grids

### Forms

### Templates

## How to do

### Login

### Change language

### Controllers method for grid and form