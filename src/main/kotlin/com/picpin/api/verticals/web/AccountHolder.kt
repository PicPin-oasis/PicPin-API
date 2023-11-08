package com.picpin.api.verticals.web

object AccountHolder {
    private val holder = ThreadLocal<Long>()
    fun getAccountId(): Long = holder.get()
    fun setAccountId(userId: Long): Unit = holder.set(userId)
    fun refresh() = holder.remove()
}
