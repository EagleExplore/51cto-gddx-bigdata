package com.les3

import scala.util.matching.Regex

object Match {
  def main(args: Array[String]): Unit = {
    //简单例子，判断字符、字符串等
    var backCode = 10
    val value: String = "fail"
   
    value match {
      case "success" => backCode = 1
      case "fail" => backCode = -1
      case _ if value.equals("hello") => backCode = -10     //if语句进行过滤，称作守卫
      case _ => backCode = 0
    }
    println(backCode)
    
    
    //使用match  ,模式匹配。  case when  ,Java 里swich
    // 或复杂的if语句
     println("使用match：")  
     val regex1 = new Regex("""([0-9]+) ([a-z]+)""")
     val regex2 = """([0-9]+) ([a-zA-Z]+)""".r
     val regex3 = """([0-9A-Z]+) ([a-z]+)""".r
     
     val content4 = "123 yy"  
     content4 match{  
       case regex1(num,str) => {
          println("regex1:"+num + "\t" + str) 
          println("===匹配成功==")              
       }
       case regex2(num,str) => println("regex2:"+num + "\t" + str)  
       case regex3(num,str) => println("regex3:"+num + "\t" + str)  
       case _=> println("Not matched")  
     }

    
    
    
    
  }

  
}