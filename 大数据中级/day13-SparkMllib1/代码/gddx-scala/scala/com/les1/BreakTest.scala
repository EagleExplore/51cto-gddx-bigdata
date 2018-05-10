package com.les1

import scala.util.control.Breaks._

/**
  * Created by Administrator on 3/25.
  */
object BreakTest {
  def main(args: Array[String]): Unit = {
    breakable{
      for(i <-  1 to 10 ) {
        if (i == 2) {
          break()
        }
        println(i)

      }
    }
// continue
      for(i <-  1 to 10 ) {
        breakable{
          if (i == 2) {
            break()
          }
          println(i)
        }
    }

    var a:Int = 123



  }

}
