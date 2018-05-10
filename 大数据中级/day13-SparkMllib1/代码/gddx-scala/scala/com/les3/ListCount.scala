package com.les3

/**
  * Created by Administrator on 3/29.
  */
object ListCount {

  def main(args: Array[String]): Unit = {
    val list = List("hello world ni hao he he",
                    "hello world ni hao he he",
                    "a b c")

    val result = list.flatMap(a => a.split(" "))  //对逐个元素进行split处理，然后进行flat（打平，即组合）
                    .map {a => (a,1)}   //List[Tuple2]
                    .groupBy(_._1)
      .map(t=>(t._1,t._2.size))

    //set array 的函数和list 一模一样




    println(result)
  }
}
