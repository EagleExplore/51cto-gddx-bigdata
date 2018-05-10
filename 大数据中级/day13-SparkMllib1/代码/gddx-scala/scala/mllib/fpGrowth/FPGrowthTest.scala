package mllib.fpGrowth

import org.apache.spark.mllib.fpm.FPGrowth
import org.apache.spark.rdd.RDD
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/30.
  */
object FPGrowthTest {
  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("NaiveBayesTest").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

    val data = sc.textFile("data/fpGrowth/fpGrowth.txt")

    val transactions: RDD[Array[String]] = data.map(s => s.trim.split(' '))
    //创建一个FPGrowth的算法实列
    val fpg = new FPGrowth()
      //设置训练时候的最小支持度和数据分区
      .setMinSupport(0.2)
      .setNumPartitions(10)

    //计算产生模型
    val model = fpg.run(transactions)

    //查看所有的频繁项集，并且列出它出现的次数
    model.freqItemsets.collect().foreach(itemset=>{
      println( itemset.items.mkString("[", ",", "]")+","+itemset.freq)
    })

println("-----------------")
    val minConfidence = 0.5  //设置最小置信度，用于过滤结果数据
    model.generateAssociationRules(minConfidence).collect().foreach { rule =>
      println(s"${rule.antecedent.mkString("[", ",", "]")}=> " +
        s"${rule.consequent .mkString("[", ",", "]")},${rule.confidence}")

      //规则结果通常在此处写如到关系数据库供日后推荐使用
      //如此处执行mysql 的insert语句
    }

  }
}
