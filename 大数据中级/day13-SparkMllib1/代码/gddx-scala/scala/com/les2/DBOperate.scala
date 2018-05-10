package com.les2

import scala.beans.BeanProperty

/**
  * Created by Administrator on 3/27.
  */
class DBOperate(id:Int, name:String)   //主构造器
{
//  def this(id:Int,name:String,addr:String)   //辅助构造器
//  {
//    this(id,name)  //主构造器的引用
//    println(addr)
//
//  }
  @BeanProperty var a = 10
  def insert(table:String, id:Int, name :String) =
  {
    println("insert into "+table +","+id+","+name)
  }

  def success() =
  {
    println("success ")
  }

}
