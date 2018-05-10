package com.les2

/**
  * Created by Administrator on 3/27.
  */
class Person(name:String, age:Int, addr:String) extends Father(addr,age){

  override def doEat(food:String)
  {
    println("my eatting .."+food)
  }

  def printInfo()
  {
    super.printInfo2()
    println("name:"+name+",age:"+age+",addr:"+addr)
  }

}
object Person
{
  def main(args: Array[String]): Unit = {
    val p:Person = new Person("zhangsan",30,"beijing")
    p.printInfo()

  }
}