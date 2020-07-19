---
title: Exploring Recent Kotlin APIs
date: "2020-07-18T16:51:00.000Z"
layout: post
draft: false
path: "/posts/exploring-recent-kotlin-apis/"
category: "Kotlin"
tags:
  - "Kotlin"
  - "Android"
description: "We take a look at some of the more prominent api changes in Kotlin 1.3.7x and 1.4.x preview releases."
---

![Banner graphic Kotlin](https://i.postimg.cc/WzZ36DDw/art.png)

The recent releases of Kotlin have brought about a rich set of APIs particularly the upcoming `1.4` release. Let's take a dive into some of the more prominent changes.

## Fun Interfaces: SAM Conversions

Starting `1.4`, Kotlin allows us to use a more idiomatic lambda syntax for SAM classes written in Kotlin. So your typical interfaces can be prefixed with `fun` which allows for a neat little lambda instantiation as shown below.

`gist:humblerookie/325712f8a2434f1a812b939a2d85b62f`

Below is a comparison of the old and new instantiation.

`gist:humblerookie/2ce3221d5138a109035dce144ad970fc`

So instead of using the `oldInstantation` which has a lot of boilerplate, we now have neat little lambda for our anonymous `Parser` instance as shown in `newInstantiation`.


##Delegate to another property

Kotlin `1.4` allows us to delegate a property initialization to another property. This allows us to rename or refactor fields without breaking the API for existing consumers.

A simple example would be where a field is renamed

`gist:humblerookie/1e265dc580b4eebcccb39b019537d83e`

Notice how we've renamed this field without breaking the contract with the classes that have been using this field.

There is an interesting side effect of this API, let's say we had a class `Vehicle` with only two types a `car` and a `cycle`. We initially decided to model this with a single **boolean** called `isCar`.

`gist:humblerookie/3551fe106f6d758b54c60980c4e3ebc2`

However we realise later that we need to build more variants of these vehicles and need to remodel the data type. We can couple the new feature of delegating to another property along with a small extension function to achieve it.

`gist:humblerookie/b0f36d6dd086ec9692b4a2dcd249be31`

The extension function is merely a way to overload the [getter for the delegate](https://kotlinlang.org/docs/reference/operator-overloading.html#property-delegation-operators). One thing to note is while this syntax may seem more appealing you'd be better served using the `get/set` methods. This is because the extension method can get murkier when more properties start using it. So I recommend using the code below for complicated deprecations instead.

`gist:humblerookie/8b63a87020c51e96cc57fd62be818fa3`


## Standard library additions

- `scan` : This operator was added to the collections framework. Its essentially the equivalent of using `fold` with a `map` operation. `scan` returns a transformed version of the current collection where the transforming lambda has access to the *accumulated* value in order to compute the new value. Here's an illustration from the official documentation.
![Scan diagram](https://blog.jetbrains.com/wp-content/uploads/2020/02/kotlin-scanFold.gif)

- **Builders**: `buildList`, `buildMap` are efficient ways to build read only lists and maps respectively for cases where the members aren't straightforward or need to be computed from multiple sources. Here's how you'd use it `gist:humblerookie/9d23ed7de78739e7d37da7304e236cd9`

There are a few more additions, you can check the entire list [here](https://github.com/JetBrains/kotlin/blob/1.3.70/ChangeLog.md#new-features-5).
##Parting thoughts
Both `1.4` and `1.3.7x` releases have introduced some neat changes which we can make use of especially in the standard library. Do give them a go.