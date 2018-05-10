package com.les1

import java.text.SimpleDateFormat
import java.util.{Calendar, Date}

/**
  * Created by Administrator on 3/25.
  */
object UseJava {

  def main(args: Array[String]): Unit = {

    val sdf = new SimpleDateFormat("yyyy-MM-dd")

    val c = Calendar.getInstance()

    println(c.getTime)

    println(sdf.format(c.getTime))

  }

}
