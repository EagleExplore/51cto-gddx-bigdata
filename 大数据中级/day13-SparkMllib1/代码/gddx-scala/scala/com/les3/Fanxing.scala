package com.les3

class Fanxing[A,B](a:A,b:B) {   //泛型类

  def fun[T](i:T):Unit=      //泛型函数
  {
    println(i.toString())    //正确，因为任何类型都有toString()函数
//    println(i.map(_ + 1))       //提示有误，原因是无法确保T类型存在map()函数，如何解决？
    
  }
  
}

object Fanxing
{
  def main(args: Array[String]): Unit = {
    val t1 = new Fanxing(1,"hello")      //可以传入任意类型，编译器会自动推断类型
    val t2 = new Fanxing("aa",Array(12,334,566)) //试想，如果类型A和B是明确类型，是无法同时满足t1和t2的入参格式的。
    //泛型就是提供这种便利：
    //1、定义class或函数时，使用泛型，不明确指定参数的类型。
    //2、引用的时候根据入参，编译器会自动推断参数的类型。
    
    t2.fun("123")
    t2.fun(233)
    t2.fun(Array(12,334,566))
    
  }

}