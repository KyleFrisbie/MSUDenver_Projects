/**
 * Special class for holding all of country information in input data file,
 *  with getters and setters
 *
 * Created by Kyle L Frisbie on 4/3/2015.
 */
class Country {
  private var NAME = ""
  private var EXPORTS = 0
  private var TRADE_BALANCE = 0
  private var YEAR = 0
  private var POPULATION = 0
  private var AREA = 0

  def takeName(): String = {
    NAME
  }

  def setName(inName:String): Unit = {
    NAME = inName
  }

  def takeExports(): Int = {
    EXPORTS
  }

  def setExports(inExports:Int): Unit = {
    EXPORTS = inExports
  }

  def takeTradeBalance(): Int = {
    TRADE_BALANCE
  }

  def setTradeBalance(inTradeBalance:Int): Unit = {
    TRADE_BALANCE = inTradeBalance
  }

  def takeYear(): Int = {
    YEAR
  }

  def setYear(inYear:Int): Unit = {
    YEAR = inYear
  }

  def takePopulation(): Int = {
    POPULATION
  }

  def setPopulation(inPopulation:Int): Unit = {
    POPULATION = inPopulation
  }

  def takeArea(): Int = {
    AREA
  }

  def setArea(inArea:Int): Unit = {
    AREA = inArea
  }

  // Return all of Country information in a formatted string
  def returnAllInfo(): String = {
    "%43s %10s %10s %5s %10s %5s".format(
      NAME, EXPORTS, TRADE_BALANCE, YEAR, POPULATION, AREA)
  }

}
