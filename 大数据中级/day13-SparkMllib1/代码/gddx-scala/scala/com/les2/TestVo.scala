package com.les2

import scala.beans.BeanProperty

/**
  * Created by Administrator on 3/27.
  */
class TestVo {

  @BeanProperty var  id = null
  @BeanProperty var name = null
  @BeanProperty var addr = null


}

object TestVo
{
  def main(args: Array[String]): Unit = {
    val t = new TestVo()

  }
}


