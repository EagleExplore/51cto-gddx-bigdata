package com.les3

class Person {

}
case class Farmer(age: Int) extends Person
case class Worker(age: Int, addr: String) extends Person



object Person
{
  def main(args: Array[String]): Unit = {
//    val farmer = Farmer(20)
//    println(farmer.age)

////    var p:Person = Worker(30,"北京")   //不需要new
    var p:Person = Worker(50,"bj")

    p match {
      case a:Farmer => println("farmer "+a.age)   //a是实例的引用，主构造函数的参数可以当作字段用
      case b:Worker if p!=null => println("Worker "+b.age+"  ,addr="+b.addr)
      case _ => println("其他 ")
    }
    
    
  }  
}

