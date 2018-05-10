import scala.collection.mutable.ArrayBuffer

val arr2 = new Array[String](10)

val arr3 = Array(1,2,3)

var arr = new ArrayBuffer[Int]()

arr += (2,34)
arr(0)
arr.head
arr.tail

var arr7 = new ArrayBuffer[Int]()

arr7 += (2,34)

arr ++ arr7

8 +: arr
arr += (7)

arr.max
arr.min
arr.head
arr.tail
arr.take(2)

