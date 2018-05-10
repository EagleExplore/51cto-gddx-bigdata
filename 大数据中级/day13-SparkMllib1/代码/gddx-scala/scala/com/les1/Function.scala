package com.les1

/**
  * Created by Administrator on 3/25.
  */
object Function {

  def add( a:Int, b:Int)=
  {

    a+b
  }

  def print()=
  {
    println("test")
  }

  val add2 = (x:Int , y:Int)=> x+y //匿名函数


  def printEvery(c:String*)=
  {
    c.foreach(x => println(x))
  }

  def func(a:String , b:String ="bvalue", c:String="cValue")=
  {
    a+"_"+b+"_"+c
  }

  def main(args: Array[String]): Unit = {
//    println(add(12,23))
//    print()
//    print
//    println(add2(4,5))
//
//    printEvery("abc")
//    printEvery("abc","dsf","456")
    println(func("a","b","c"))
    println(func("a"))
    println(func("a","b"))
    println(func("a",c="c"))
  }
}
