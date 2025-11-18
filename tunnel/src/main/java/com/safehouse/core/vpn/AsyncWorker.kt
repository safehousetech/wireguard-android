package com.safehouse.core.vpn

//import android.os.Handler
//
//import java9.util.concurrent.CompletableFuture
//import java9.util.concurrent.CompletionStage
//
//import java.util.concurrent.Executor
//import javax.inject.Inject
//import javax.inject.Singleton
//
//@Singleton
//class AsyncWorker
//@Inject
//constructor(private val executor: Executor, private val handler: Handler) {
//  fun runAsync(run: () -> Unit): CompletionStage<Void> {
//    val future = CompletableFuture<Void>()
//    executor.execute {
//      try {
//        run()
//        handler.post { future.complete(null) }
//      } catch (t: Throwable) {
//        handler.post { future.completeExceptionally(t) }
//      }
//    }
//    return future
//  }
//
//  fun <T> supplyAsync(get: () -> T?): CompletionStage<T> {
//    val future = CompletableFuture<T>()
//    executor.execute {
//      try {
//        val result = get()
//        handler.post { future.complete(result) }
//      } catch (t: Throwable) {
//        handler.post { future.completeExceptionally(t) }
//      }
//    }
//    return future
//  }
//}
