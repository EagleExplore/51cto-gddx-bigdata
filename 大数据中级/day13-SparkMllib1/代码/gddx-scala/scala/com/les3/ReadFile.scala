package com.les3

import scala.io._


object ReadFile {
def main(args: Array[String]): Unit = {
    var file = Source.fromFile("c:/aa.hql","UTF-8")
//    println(file.mkString(""))
//    println(file.mkString)
////    按行读
//    file.getLines().foreach
//    {
//      x =>    //每一行的引用
//     {
////        println("line="+x)
//        val arr = x.split(" ")
//        arr.foreach { x => println("split="+x)  }
//      }
//     println("===================")
//    }
//
   //按每个char读
   val iter = file.buffered
   while(iter.hasNext)
   {
     println(iter.next())
   }


    
}
}