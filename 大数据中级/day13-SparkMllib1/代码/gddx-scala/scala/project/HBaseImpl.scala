package project

import org.apache.hadoop.hbase.HBaseConfiguration
import org.apache.hadoop.hbase.client.{HTable, Put}
import org.apache.hadoop.hbase.util.Bytes

/**
  * scala 版本
  */
class HBaseImpl(zkQuorum:String) extends Serializable{

  //Spark直接调用的外部类都需要序列化。
  //放在函数里可以减少这样的要求。

  def insert(tableName: String, rowKey: String, family: String, quailifer: String, value: String) {

    val conf = HBaseConfiguration.create()
    conf.set("hbase.zookeeper.quorum", zkQuorum)
    //Put操作
    val table = new HTable(conf, "order")
    val put = new Put(rowKey.getBytes)
    put.add(Bytes.toBytes(family), Bytes.toBytes(quailifer), Bytes.toBytes(value))
    table.put(put)
    table.flushCommits()
  }


}
