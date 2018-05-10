package com.les2

/**
  * Created by Administrator on 3/27.
  */
class BanSheng(id:Int) {
  def add2(a:Int,b:Int): Int =
  {
    a+b
  }

}
object BanSheng
{
  def apply(id:Int) =
  {
    println("---apply---")
    new BanSheng(id)
  }

  def add(a:Int,b:Int): Int =
  {
    a+b
  }

  def main(args: Array[String]): Unit = {
//    BanSheng.add(1,2)//静态函数
//    val b = new BanSheng(11)
//    b.add2(3,4)

    val c = BanSheng(200)
//    val cc = BanSheng.apply(200)
  }
}

