package mllib.kmeans


import org.apache.spark.mllib.clustering._
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.{SparkConf, SparkContext}

object KMeansTest {

  def main(args: Array[String]) {
    //1 构建Spark对象
    val conf = new SparkConf().setAppName("KMeans").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)

    // 读取样本数据
    val data = sc.textFile("data/kmeans/product.txt")
    val parsedData = data.map(s => Vectors.dense(s.split('\t').map(_.toDouble))).cache()

    // 新建KMeans聚类模型，并训练
    val initMode = "k-means||"
    val numClusters = 2
    val numIterations = 100
    val model = new KMeans().
      setInitializationMode(initMode).
      setK(numClusters).
      setMaxIterations(numIterations).
      run(parsedData)

    val centers = model.clusterCenters
    println("centers")
    for (i <- 0 to centers.length - 1) {
      println(centers(i)(0) + "\t" + centers(i)(1))
    }

    //测试
    val testData = sc.textFile("data/kmeans/test.txt").map(s => Vectors.dense(s.split('\t').map(_.toDouble))).cache()
    testData.foreach { line =>
      val predictedClusterIndex: Int = model.predict(line)
      println("The data " + line.toString + " belongs to cluster " +
        predictedClusterIndex)
    }

    // 误差计算
    val modelCost = model.computeCost(testData)
    println("Sum of Squared modelCost = " + modelCost)

    //保存模型
    //    val ModelPath = "/user/root/KMeans_Model"
    //    model.save(sc, ModelPath)
    //    val sameModel = KMeansModel.load(sc, ModelPath)

  }

}
