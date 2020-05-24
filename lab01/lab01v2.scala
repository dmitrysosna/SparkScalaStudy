import java.io.PrintWriter

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object lab01v2 {

  val datafile = "ml-100k/u.data"
  val filmId = "269"
  val resultfile = "lab01.json"
  val allMarks = new ArrayBuffer[String]
  val myMarks = new ArrayBuffer[String]

  def readData(f: String, Id: String): Unit = {
    val lines = Source.fromResource(f).getLines()
    lines.foreach { x =>
      val arr = x.toString.split('\t')
      if (arr(1) == Id) {
        myMarks += arr(2)
      }
      allMarks += arr(2)
    }
    println(myMarks)
    println(allMarks)
  }

    def countMarks(f:String):Unit={
      val myMarksList = myMarks.sortBy(x=>x).groupBy(x=>x).mapValues(_.size)
      val allMarksList = allMarks.sortBy(x=>x).groupBy(x=>x).mapValues(_.size)
      println(myMarksList)
      println(allMarksList)

      println("write json result")
      writeJson(myMarksList,allMarksList,resultfile)
    }

  def writeJson(my:Map[String,Int],all:Map[String,Int],name:String):Unit={
    val head = "{\n   \"hist_film\": [  \n      "
    val filmMarks = my.toSeq.sortBy(_._1).map(x=> x._2).mkString(",\n      ")
    val middle = "\n   ],\n   \"hist_all\": [\n      "
    val allMarks = all.toSeq.sortBy(_._1).map(x=> x._2).mkString(",\n      ")
    val tail = "\n   ]\n}"

    val json = head + filmMarks+ middle +allMarks+ tail
    println(json)
    new PrintWriter(resultfile) {write(json);close}
  }

  def main(args: Array[String]): Unit = {
    println("reading data")
    readData(datafile, filmId)

    println("count marks for filmId")
    countMarks(filmId)
  }

}

