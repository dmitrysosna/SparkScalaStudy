import java.io.{File, PrintWriter}

import org.apache.commons.io.FileUtils
import play.api.libs.json._

import scala.collection.mutable.ArrayBuffer
import scala.io.Source

object lab01v2 {

  val datafile = "ml-100k/u.data"
  val filmId = "269"
  val resultfile = "lab01.json"
  val resultfile2 = "lab01_old.json"
  val allMarks = new ArrayBuffer[String]
  val myMarks = new ArrayBuffer[String]

  def readData(f: String, Id: String): Unit = {
    println(f)
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

  def writeJson(my:Map[String,Int],all:Map[String,Int],name:String):Unit={

    val filmMarks = my.toSeq.sortBy(_._1).map(x=> x._2).toList
    val allMarks = all.toSeq.sortBy(_._1).map(x=> x._2).toList
    val json = Json.obj("hist_film"->filmMarks,"hist_all"->allMarks)
    val file = new File(resultfile)
    FileUtils.write(file, json.toString(), "UTF-8")

    val head = "{\n   \"hist_film\": [  \n      "
    val filmMarksS = my.toSeq.sortBy(_._1).map(x=> x._2).mkString(",\n      ")
    val middle = "\n   ],\n   \"hist_all\": [\n      "
    val allMarksS = all.toSeq.sortBy(_._1).map(x=> x._2).mkString(",\n      ")
    val tail = "\n   ]\n}"

    val json2 = head + filmMarksS+ middle +allMarksS+ tail
    println(json2)
    new PrintWriter(resultfile2) {write(json2);close}
  }

  def main(args: Array[String]): Unit = {
    println("reading data")
    readData(datafile, filmId)

    val myMarksList = myMarks.groupBy(x=>x).view.mapValues(_.size).toMap
    val allMarksList = allMarks.groupBy(x=>x).view.mapValues(_.size).toMap
    println(myMarksList)
    println(allMarksList)

    println("write json result")
    writeJson(myMarksList,allMarksList,resultfile)

  }

}
