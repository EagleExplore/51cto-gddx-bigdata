package mllib.classification

import org.apache.spark.mllib.classification.NaiveBayes
import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.{SparkConf, SparkContext}

/**
  * Created by xubo on 2016/5/23.
  */
object NaiveBayesTest {
  def main(args: Array[String]) {
    val conf = new SparkConf().setAppName("NaiveBayesTest").setMaster("local")
    conf.set("spark.testing.memory", "471859200")
    val sc = new SparkContext(conf)   //Driver类


    val data = sc.textFile("data/tree/相亲训练集.txt")
    val parsedData = data.map { line =>
      val parts = line.split(',')
      LabeledPoint(parts(0).toDouble, Vectors.dense(parts(1).split(' ').map(_.toDouble)))
    }
    // Split data into training (60%) and test (40%).
    val splits = parsedData.randomSplit(Array(0.6, 0.4), seed = 11L)
    val training = splits(0)
    val test = splits(1)

    val model = NaiveBayes.train(training, lambda = 1.0, modelType = "multinomial")//多项式或伯努利
    //lambda的作用：
    //贝叶斯公式推导能够成立有个重要前提，就是各个证据（evidence）不能为0。也即对于任意特征Fx，P(Fx)不能为0。
    // 而显示某些特征未出现在测试集中的情况是可以发生的。
    // 因此实现上通常要做一些小的处理，例如把所有计数进行+1，即加法平滑(additive smoothing)

    val predictionAndLabel = test.map(p => (model.predict(p.features), p.label))
    //计算错误率
    val errorRate = 1.0 * predictionAndLabel.filter(x => x._1 != x._2).count() / test.count()

    println("result:")
    println("training.count:" + training.count())
    println("test.count:" + test.count())
    println("errorRate:" + errorRate)

    // 保存和加载 model
//    val iString = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date())
//    val path = "file/data/mllib/output/classification/NaiveBayesModel" + iString + "/result"
//    model.save(sc, path)
//    val sameModel = NaiveBayesModel.load(sc, path)
//    println(sameModel.modelType)

    sc.stop
  }
}