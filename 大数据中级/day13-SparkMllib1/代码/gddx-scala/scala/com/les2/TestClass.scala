package com.les2

/**
  * Created by Administrator on 3/27.
  */
class TestClass {
  def add()
  {}
  var a = ""

  def main(args: Array[String]): Unit = {
    println("112")
  }


}

object TestObject
{
  def add()
  {}
//
//  var a = ""

  def main(args: Array[String]): Unit = {
    println("12345")

//    val t = new TestClass()
//    t.add()
    add()
    TestObject.add()


  }
}
