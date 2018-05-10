import scala.collection.mutable.ListBuffer

var list2 = new ListBuffer[Int]()

val list = List(1,2,3)

list2 = list2 ++ list

list2 += 5
list2 += (6,7,8)

list2.append(9)

list2.head
list2.last
list2.length
list2.reduce(_ + _)  //list中元素两两相加

list2.map(x => x+1)  //map对集合类中每个元素逐个处理

list2.filter(x => x%2==0)  //过滤不符合条件的元素



