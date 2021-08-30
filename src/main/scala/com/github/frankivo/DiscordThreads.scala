package com.github.frankivo

import discord4j.core.DiscordClient
import discord4j.core.`object`.entity.channel.TextChannel
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
  def connect(): Unit = {
    val gateway = DiscordClient
      .create(DiscordThreads.TOKEN)
      .login()
      .block()

    gateway.on(classOf[MessageCreateEvent])
      .subscribe(e => handle(e))

    gateway.onDisconnect.block
  }

  def handle(event: MessageCreateEvent): Unit = {
    event
      .getMessage
      .getChannel
      .block()
      .asInstanceOf[TextChannel]
      .createMessage("test!")
      .subscribe()
  }
}