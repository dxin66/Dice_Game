package com.example.project1

import java.util.*

fun main() {
    val scanner = Scanner(System.`in`)

    // 获取用户输入的年份和月份
    print("请输入年份：")
    val year = scanner.nextInt()
    print("请输入月份（1-12）：")
    val month = scanner.nextInt()

    // 打印当月的日历
    printCalendar(year, month)

    scanner.close()
}

fun printCalendar(year: Int, month: Int) {
    /*设置一个日期实例*/
    val calendar = Calendar.getInstance()
    calendar.set(year, month - 1, 1)

    /*计算星期一的偏移量（日历从星期一开始）*/
    val dayOfWeek = (calendar.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY + 7) % 7
    /*得到这个月的总天数*/
    val maxDay = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

    println("          ${year}年${month}月  ")
    println("Mon Tue Wed Thu Fri Sat Sun")
    /*打印空格，直到星期一开始*/
    /*左闭右开*/
    for (i in 0 until dayOfWeek) {
        print("    ")
    }
    /*左闭右闭*/
    for (day in 1..maxDay) {
        print(String.format("%3d", day))
        /*每遇7换行或者这个月已经结束换行*/
        if ((day + dayOfWeek) % 7 == 0 || day == maxDay) {
            println()
        } else {
            print(" ")
        }
    }
}
