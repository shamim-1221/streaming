import org.apache.spark.SparkConf
import org.apache.spark.streaming._
object NetworkWordCount {
def main(args: Array[String]): Unit = {
val sparkConf = new
SparkConf().setMaster("local[2]")setAppName("NetworkWordCount")
val ssc = new StreamingContext(sparkConf, Seconds(10))
val lines = ssc.socketTextStream("localhost",9999)
val words = lines.flatMap(_.split(" "))
val tuples = words.map(word => (word ,1))
val wordCounts = tuples.reduceByKey((t, v) => t + v)
wordCounts.print()
ssc.start()
ssc.awaitTermination()
}
}
