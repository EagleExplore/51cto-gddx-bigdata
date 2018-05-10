package mllib.classification

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.DecisionTree
import org.apache.spark.mllib.tree.model.DecisionTreeModel
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by Administrator on 4/30.
  */
object xiangqinTree {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("test").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类

    val persons = sc.textFile("data/tree/相亲训练集.txt")
    val personsForTest = sc.textFile("data/tree/相亲测试集.txt")

    //转换成向量
    val trainingData = persons.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }
//测试集
    val testData = personsForTest.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }
    //分类
    val numClasses = 2  //分几类，这里见面与不见面 共2类
    val categoricalFeaturesInfo = Map[Int, Int]()
    val impurity = "entropy"    //entropy    gini
    //最大深度
    val maxDepth = 5
    //最大分支
    val maxBins = 8
    //模型训练
    val model = DecisionTree.trainClassifier(trainingData, numClasses, categoricalFeaturesInfo,
      impurity, maxDepth, maxBins)

//    model.save(sc,"")  //保存模型

    //通过测试集评估模型
    val labelAndPreds = testData.map { labeledPoint =>
      val prediction = model.predict(labeledPoint.features)
      (labeledPoint.label, prediction)  // 原值和预测值
    }

    //测试值与真实值对比
    val print_predict = labelAndPreds.take(5000)
    println("label" + "\t" + "prediction")
    for (i <- 0 to print_predict.length - 1) {
      println(print_predict(i)._1 + "\t" + print_predict(i)._2)
    }

    //计算错误率
    val testErr = labelAndPreds.filter(r => r._1 != r._2).count.toDouble / testData.count()
    println("Test Error = " + testErr)
    //打印树的判断值
    println("Learned classification tree model:\n" + model.toDebugString)

    // Save and load model
    model.save(sc, "target/tmp/myDecisionTreeClassificationModel")  //通常保存在HDFS的目录下

    // 其他作业使用Model
//    val sameModel = DecisionTreeModel.load(sc, "target/tmp/myDecisionTreeClassificationModel")

  }
}
