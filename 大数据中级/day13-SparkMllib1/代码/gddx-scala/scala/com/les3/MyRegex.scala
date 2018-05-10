package com.les3

import scala.util.matching.Regex

object MyRegex {

  
  def main(args: Array[String]): Unit = {
//    val regex = "[a-zA-Z0-9]+".r   //

    val pattern:Regex = "[0-9]+".r
    //如果格式中有\或",则用"""..."""符
//    """\d+""".r replaceAllIn ("July 15", "hello")
    
    val str = "10 2 3a 43"
//    for(i <- pattern.findAllIn(str))
//    {
//      println(i)
//    }
//    println("----------")
//    for(i <- pattern.findFirstIn(str))
//    {
//      println(i)
//    }
//
//    println("----replaceFirstIn---")
//
//    var s1 =  pattern.replaceFirstIn("10 23 a43","aa")
//    for(i <- pattern.replaceFirstIn("10 23 a43","aa"))
//    {
//      println(i)
//    }
//    println("----replaceAllIn---")
//    for(i <- pattern.replaceAllIn("10 23a 43","aa"))
//    {
//      println(i)
//    }
//
//
    println("----正则表达式组的用法---")
    val p = "([0-9]+) ([a-z]+)".r
    val s = "123 abc"
    val p(a,b) = s
//    println(a+"--"+b)

    for(p(a,b) <- p.findAllIn(s))
    {
      println(a+"--"+b)
    }


  }
}