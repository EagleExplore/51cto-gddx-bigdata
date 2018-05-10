package com

/**
  * Created by Administrator on 4/5.
  */
object Test {

  def main(args: Array[String]): Unit = {

    val words = List("yes i do", "no thanks", "yes yes yes", "bye bye","you are holy shit","what the fuck","bitch go away")
    val map = words.flatMap { line => line.split(" ") }.foldLeft(Map.empty[String, Int]){(count, word) => count + (word -> (count.getOrElse(word, 0) + 1))}
    println(map)


  }


}
