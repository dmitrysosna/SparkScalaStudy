import java.io.PrintWriter
import scala.io.Source

object lab01 extends App {
  val datafile = "ml-100k/u.data"
  val itemfile = "ml-100k/u.item"
  val filmId = 269

  var histsumone = 0
  var histsumtwo = 0
  var histsumthree = 0
  var histsumfour = 0
  var histsumfive = 0

  var mysumone = 0
  var mysumtwo = 0
  var mysumthree = 0
  var mysumfour = 0
  var mysumfive = 0

  def getval(r: String, fnum: Int): String ={
        val array = r.split('\t')
    array(fnum)
  }

def histcasesum (s:Int): Unit ={
    s match {
      case 1 => histsumone += 1
      case 2 => histsumtwo += 1
      case 3 => histsumthree += 1
      case 4 => histsumfour += 1
      case 5 => histsumfive += 1
      case _ => println("nomatch")
    }
  }

  def mycasesum (s:Int): Unit ={
    s match {
      case 1 => mysumone += 1
      case 2 => mysumtwo += 1
      case 3 => mysumthree += 1
      case 4 => mysumfour += 1
      case 5 => mysumfive += 1
      case _ => println("nomatch")
    }
  }

  val lines = Source.fromResource(datafile).getLines()
  for (elem <- lines) {
   var itemId = getval(elem.toString,1).toInt
   var score = getval(elem.toString,2).toInt
    if (itemId ==filmId){
      println(score)
      mycasesum(score)
    }
    histcasesum(score)
  }

  val jsonString = "{\n   \"hist_film\": [  \n      "+mysumone+",\n      "+mysumtwo+",\n      "+mysumthree+",\n      "+mysumfour+",\n      "+mysumfive+"\n   ],\n   \"hist_all\": [\n      "+histsumone+",\n      "+histsumtwo+",\n      "+histsumthree+",\n      "+histsumfour+",\n      "+histsumfive+"\n   ]\n  }"

  println(jsonString)
  new PrintWriter("result.json") {write(jsonString);close}
}