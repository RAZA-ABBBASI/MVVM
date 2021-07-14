package com.smarwey.mvvm.util

import kotlinx.coroutines.*

/*
* Custom lazy block
* It will use the coroutine scope
* Lazy blocks are used to instantiate an object ONLY when it is needed
* Now if we need a lazy block within a Coroutine, then we use our custom lazy block like following
 */

fun<T> lazyDeferred(block: suspend CoroutineScope.() -> T): Lazy<Deferred<T>>{
    return lazy {
        GlobalScope.async(start = CoroutineStart.LAZY) {
            block.invoke(this)
        }
    }
}