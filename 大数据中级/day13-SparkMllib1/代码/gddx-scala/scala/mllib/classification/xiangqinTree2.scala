package mllib.classification

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/30.
  */
object xiangqinTree2 {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

    val persons = sc.textFile("data/tree/相亲训练集.txt")
//    val personsForTest = sc.textFile("data/tree/相亲测试集.txt")

    //转换成向量
    val datas = persons.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }

    val splits = datas.randomSplit(Array(0.7,0.3))
    val (trainingData,testData) = (splits(0),splits(1))

//    val testData = personsForTest.map { line =>
//      val parts = line.split(',')
//      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
//    }
    //分类
    val numClasses = 2  //分几类，这里见面与不见面 共2类
    val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = "gini"
    //最大深度
    val maxDepth = 5
    //最大分支
    val maxBins = 16
    //模型训练
    val model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      impurity, maxDepth, maxBins)

    //模型预测
    val labelAndPreds = testData.map { point =>
      val prediction = model.predict(point.features)
      (point.label, prediction)  // 原值和预测值
    }

    //测试值与真实值对比
    val print_predict = labelAndPreds.take(50)
    println("label" + "\t" + "prediction")
    for (i <- 0 to print_predict.length - 1) {
      println(print_predict(i)._1 + "\t" + print_predict(i)._2)
    }

    //树的错误率
    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
    println("Test Error = " + testErr)
    //打印树的判断值
    println("Learned classification tree model:\n" + model.toDebugString)

  }
}
