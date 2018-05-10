package com.les1

import scala.collection.mutable.ArrayBuffer

/**
  * Created by Administrator on 3/25.
  */
object Test {

  def main(args: Array[String]): Unit = {
    var arr = new ArrayBuffer[Int]()

    arr.+=(2,34)
    arr(0)
    arr.head

    arr.tail

    var arr2 = arr.map(i=>i+1)


    var a = 10
    if(a>0)
    {
      println("大于0")
    }else if(a<0)
    {
      println("小于0")
    }
    else{
      println("=0")
    }

//    arr.foreach(println _)

    for(i <-  1 to 10 if i!=2) {  // 1 to 10 产生一个Range对象
      println(i)
    }
    //until 相当于<
    // to 相当于<=


//    var i=0
//    0 until 5 foreach {
//      i += _
//    }
//    println(i)


  }
}
