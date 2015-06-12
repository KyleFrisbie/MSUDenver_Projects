/**
 * This program takes an input data file consisting of various information on
 *  different countries collected over a period of years. Different data is
 *  displayed based on user input.
 *
 * Created by Kyle L Frisbie on 3/31/2015.
 */

import java.io.File
import java.util
import java.util.Scanner
import scala.io.Source

object ExportAnalysis {
  val KB = new Scanner(System.in) // Reads user input from console
  val INPUT_FILE = new Scanner(establishInputFile())  // Scans input file
  val countryList = new util.ArrayList[Country] // Complete list of counties
  // List of countries for a given year
  var yearList = new util.ArrayList[Country]()


  /**
   * Initialize initial input file for Scanning
   * @return File inputFile
   */
  def establishInputFile(): File = {
    val fileIn = new File("InputFile.txt")
    fileIn
  }

  /**
   * Take year from user input (console)
   * @return Int year
   */
  def getYear(): Int = {
    print("Enter the year you are interested in: ")
    return KB.nextInt()
  }

  /**
   * Take operative selection from user (console)
   * @return Int user selection
   */
  def getUserChoice(): Int = {
    println("Make a selection (1 - 7)")
    println(
      "(1) Finding the top 5 exporting countries for a given year." +
        "\n(2) Finding the worst 5 exporting countries for a given year." +
        "\n(3) Finding the 5 countries with the best balance of trade for " +
        "a given year." +
        "\n(4) Finding the 5 countries with the worst balance of trade for " +
        "a given year." +
        "\n(5) Finding the 5 countries with the best ratio of exports to " +
        "balance of trade for a given year." +
        "\n(6) Finding the 5 countries with the worst ratio of exports to " +
        "balance of trade for a given year." +
        "\n(7) Finding all data for a given country.")
    print("Enter your selection: ")
    return KB.nextInt()
  }

  /**
   * Take name of country from user selection (console)
   * @return String name of country
   */
  def getCountryName(): String = {
    print("Enter name of country: ")
    return KB.next()
  }

  /**
   * Build complete array of information parsed from input file
   */
  def buildArray(): Unit = {
    var i = 0
    for (i <- 1 to 20) {
      INPUT_FILE.nextLine()
    }
    while (INPUT_FILE.hasNext) {
      val nextLine = INPUT_FILE.nextLine()
      val tokens = nextLine.split(",")
      val country = new Country
      country.setName(tokens(0))
      country.setExports(tokens(1).toInt)
      country.setTradeBalance(tokens(2).toInt)
      country.setYear(tokens(3).toInt)
      country.setPopulation(tokens(4).toInt)
      country.setArea(tokens(5).toInt)
      countryList.add(country)
    }
  }

  /**
   * Sort yearList for top 5 worst exporting countries
   */
  def sortByWorstExports(): Unit = {
    for (i <- 1 until (yearList.size())) {
      var temp = yearList.get(i)
      var j = i
      while (j > 0 && yearList.get(j - 1).takeExports() > temp.takeExports()) {
        yearList.set(j, yearList.get(j - 1))
        j -= 1
      }
      yearList.set(j, temp)
    }
    for (i <- 0 until 5) {
      println(yearList.get(i).returnAllInfo())
    }
  }

  /**
   * Sort yearList for top 5 best exporting countries
   */
  def sortByBestExports(): Unit = {
    for (i <- 1 until (yearList.size())) {
      var temp = yearList.get(i)
      var j = i
      while (j > 0 && yearList.get(j - 1).takeExports() < temp.takeExports()) {
        yearList.set(j, yearList.get(j - 1))
        j -= 1
      }
      yearList.set(j, temp)
    }
    for (i <- 0 until 5) {
      println(yearList.get(i).returnAllInfo())
    }
  }

  /**
   * Sort yearList for top 5 worst balanced countries
   */
  def sortByWorstBalance(): Unit = {
    for (i <- 1 until (yearList.size())) {
      var temp = yearList.get(i)
      var j = i
      while (j > 0 &&
        yearList.get(j - 1).takeTradeBalance() > temp.takeTradeBalance()) {
        yearList.set(j, yearList.get(j - 1))
        j -= 1
      }
      yearList.set(j, temp)
    }
    for (i <- 0 until 5) {
      println(yearList.get(i).returnAllInfo())
    }
  }

  /**
   * Sort yearList for top 5 best balanced countries
   */
  def sortByBestBalance(): Unit = {
    for (i <- 1 until (yearList.size())) {
      var temp = yearList.get(i)
      var j = i
      while (j > 0 &&
        yearList.get(j - 1).takeTradeBalance() < temp.takeTradeBalance()) {
        yearList.set(j, yearList.get(j - 1))
        j -= 1
      }
      yearList.set(j, temp)
    }
    for (i <- 0 until 5) {
      println(yearList.get(i).returnAllInfo())
    }
  }

  /**
   * Sort yearList for top 5 worst export to balance ratio
   */
  def sortByWorstRatio(): Unit = {
    for (i <- 1 until (yearList.size())) {
      var temp = yearList.get(i)
      var j = i
      while (j > 0 &&
        yearList.get(j - 1).takeExports() /
          yearList.get(j - 1).takeTradeBalance()
          > temp.takeExports() / temp.takeTradeBalance()) {
        yearList.set(j, yearList.get(j - 1))
        j -= 1
      }
      yearList.set(j, temp)
    }
    for (i <- 0 until 5) {
      println(yearList.get(i).returnAllInfo())
    }
  }

  /**
   * Sort yearList for top 5 best export to balance ratio
   */
  def sortByBestRatio(): Unit = {
    for (i <- 1 until (yearList.size())) {
      var temp = yearList.get(i)
      var j = i
      while (j > 0 &&
        yearList.get(j - 1).takeExports() /
          yearList.get(j - 1).takeTradeBalance()
          < temp.takeExports() / temp.takeTradeBalance()) {
        yearList.set(j, yearList.get(j - 1))
        j -= 1
      }
      yearList.set(j, temp)
    }
    for (i <- 0 until 5) {
      println(yearList.get(i).returnAllInfo())
    }
  }

  /**
   * Generate Array List (yearList) of information only in selected year
   * @param year Int user defined year
   */
  def findYear(year: Int): Unit = {
    yearList = new util.ArrayList[Country]()
    var i = 0
    while (i < countryList.size()) {
      if (countryList.get(i).takeYear() == year) {
        yearList.add(countryList.get(i))
      }
      i += 1
    }
  }

  /**
   * Scan countryList for user defined country
   * @param country String user defined country
   */
  def findCountry(country: String): Unit = {
    for (i <- 0 until countryList.size()) {
      if (countryList.get(i).takeName().equals(country)) {
        println(countryList.get(i).returnAllInfo())
      }
    }
  }

  def main(args: Array[String]) {

    val selection = getUserChoice() // Get method selection from user

    buildArray  // Generate countryList ArrayList

    // If year is needed, get year from user
    if (selection < 7) {
      val year = getYear()
      findYear(year)
    }

    selection match {
      case 1 => sortByBestExports()
      case 2 => sortByWorstExports()
      case 3 => sortByBestBalance()
      case 4 => sortByWorstBalance()
      case 5 => sortByBestRatio()
      case 6 => sortByWorstRatio()
      case 7 =>
        // Get country name from user and find data with that name in
        //  countryList
        findCountry(getCountryName())
    }

    KB.close()
    System.exit(0)
  }
}