package com.les3

import scala.collection.mutable

/**
  * Created by Administrator on 3/29.
  */
object MapTest {
  def main(args: Array[String]): Unit = {
    //指定值的定义方式
    var m = Map("k1"->"v1","k2"->"v2")
    val m2 = Map(("k1","v1"),("k2","v2"))

    var hashmap = new mutable.HashMap[String,String]()
    hashmap.put("a","b")
    println(hashmap.get("a").head)
    println(hashmap("a"))

//    hashmap.keys.foreach(println _)
//    hashmap.values.foreach(println _)

//    hashmap.keys.foreach(k=>println(hashmap(k)))

    val a:Option[Int] = Some(5)
    val b:Option[Int] = None

    println("a.isEmpty: " + a.isEmpty )
    println("b.isEmpty: " + b.isEmpty )


  }





}
