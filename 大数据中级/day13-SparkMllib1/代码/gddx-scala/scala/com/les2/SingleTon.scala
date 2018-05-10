package com.les2

/**
  * Created by Administrator on 3/27.
  */
class SingleTon {

}

object SingleTon
{
  private var s:SingleTon = null

  def getInstance():SingleTon=
  {
    if(s == null)
    {
        new SingleTon()
    }else{
      s
    }
  }

}

object MainTest
{

  def main(args: Array[String]): Unit = {
    val s = SingleTon.getInstance()
    println(s)
  }
}