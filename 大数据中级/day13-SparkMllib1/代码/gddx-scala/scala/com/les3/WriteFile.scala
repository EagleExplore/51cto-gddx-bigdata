package com.les3

import java.io.PrintWriter

object WriteFile {

  def main(args: Array[String]): Unit = {
    val out = new PrintWriter("c:/test.txt")
    var s = ""
    for(i <- 0 to 100) 
    {
      out.println(i+"------")
    }
    
    out.println("the end3")
    out.println(s)
    
    
    out.close();
  }
  
}