package com.les3

/**
  * Created by Administrator on 3/29.
  */
class ImplicitTest {

  def print(a:String): Unit =
  {
    println(a)
  }
}
object Int2String
{
  // 在object中定义implicit 函数
  implicit def int2Str(a : Int) = {
    println("into implicit")
    a.toString
  }
  implicit def int2Str5(a : Int, b:String) = {
    println("into implicit")
    a.toString
  }
}
object Main{
  def main(args: Array[String]): Unit = {
    val i = new ImplicitTest()
    import com.les3.Int2String._
    i.print(100)
    i.print("abc")

  }
}

