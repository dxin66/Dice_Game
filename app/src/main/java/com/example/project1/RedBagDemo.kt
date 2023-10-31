package com.example.project1

import java.util.*


/**
 * 群主发红包，其余人抢红包(群主也可以抢红包)
 */
sealed class User(var name:String,var money: Double,var receivedMoney: Double){


    /*创建密封类的两个子类*/
    class MasterUser(name: String,money: Double,receivedMoney: Double):User(name,money,receivedMoney)

    class CommonUser(name: String,money: Double,receivedMoney: Double):User(name,money,receivedMoney)


}

class RedPacket(private val totalAmount: Double, private val totalPackets: Int) {
    init {
        if(totalAmount<=0||totalPackets<=0) throw RedPacketException("总金额和总红包数量必须大于零")
    }
    private val random = Random()
    private var remainingAmount = totalAmount
    private var remainingPackets = totalPackets
    /*抢红包*/
    fun grabPacket(): Double {
        if (remainingPackets <= 0) {
            throw RedPacketException("红包已抢完")
        }

        if (remainingPackets == 1) {
            remainingPackets--
            return String.format("%.2f",remainingAmount).toDouble()
        }
        val maxAmount=remainingAmount.div(remainingPackets)*2
        val amount=random.nextDouble()*maxAmount
        remainingAmount -= amount

        remainingPackets--

        return String.format("%.2f",amount).toDouble()
    }
    override fun toString(): String {
        return "总金额: $totalAmount, 总红包数量: $totalPackets, 剩余金额:0.0, 剩余红包数量: 0.0"
    }
}
class RedPacketException(override val message: String?):Exception(message)
fun main() {
    val users = mutableListOf(
        User.MasterUser("Alice", 100.0, 0.0),
        User.CommonUser("Bob",50.0, 0.0),
        User.CommonUser("Cic",50.0, 0.0),
    )
    val redPacket = RedPacket(users[0].money, users.size)
    users[0].money=0.0
    println("红包信息: $redPacket")

    println("红包抢夺信息：")
    users.forEach { user: User ->
        val receivedMoney = redPacket.grabPacket()
        user.receivedMoney+=receivedMoney
        user.money = user.money.plus(receivedMoney)
        println("${user.name} 抢到了 $receivedMoney 元")
    }
}
