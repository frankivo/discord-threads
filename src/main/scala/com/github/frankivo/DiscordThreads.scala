package com.github.frankivo

import discord4j.common.util.Snowflake
import discord4j.core.DiscordClient
import discord4j.core.event.domain.message.MessageCreateEvent

object DiscordThreads {
  val TOKEN: String = sys.env("discord4j.token")

  def main(args: Array[String]): Unit = {
    println("Hello, World!")

    val discord = new DiscordThreads
    discord.connect()
  }
}

class DiscordThreads {
  var self: Snowflake = _

  def connect(): Unit = {
    val gateway = DiscordClient
      .create(DiscordThreads.TOKEN)
      .login()
      .block()

    self = gateway.getSelfId

    gateway
      .on(classOf[MessageCreateEvent])
      .subscribe(e => handle(e))

    gateway
      .onDisconnect
      .block()
  }

  def handle(event: MessageCreateEvent): Unit = {
    if (event.getMember.get().getId == self) return

    event
      .getMessage
      .getChannel()
      .block

      .createMessage("test!")
      .subscribe()
  }
}