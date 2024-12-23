# Harmonie IRC


[![Java CI with Gradle](https://github.com/harmonie-durrant/harmonie-irc/actions/workflows/gradle.yml/badge.svg)](https://github.com/harmonie-durrant/harmonie-irc/actions/workflows/gradle.yml)
<img src="https://cf.way2muchnoise.eu/harmonie-irc.svg" alt="CurseForge Downloads" style="margin-left: 25px;">

After making a project at 42 school 👩‍🎓 called ft_irc where I create an IRC server, I was inspired to make my own client, but having a bunch of clients already in the market makes the idea of making another one boring 🙄.
So like any sane person 🙃 I challenged myself to at least learn something new in the process since I already know about how to use sockets from the server side, so I turned to my favourite game... Minecraft 🎮!
The game is coded in Java so a perfect opportunity to dive into how my favourite game works to learn a new language 🔤.
So from that moment onwards I strive to make a working IRC client in Minecraft.

*Harmonie IRC, an IRC client made for your Minecraft world by [Harmonie Durrant](https://github.com/harmonie-durrant).*

## 💻 Commands

All commands start with `irc_` to avoid conflicting commands.

`<>`: obligatory argument

`{}`: optional argument


### /irc_help

Shows info about the mod and it's commands

### /irc_connect <ip> <port> <password>

Connects you to an IRC server. (ref: [ft_irc](https://github.com/harmonie-durrant/ft_irc))

`<ip>`: The address of the server.

`<port>`: The port the IRC server is hosted on.

`<password>`: The password needed to access the server.

### /irc_nick <new_nick>

Changes your nickname in the currently connected IRC server.

`<new_nick>`: The address of the server

### /irc_msg <target> <message>

Sends a message to a user/channel in the currently connected IRC server.

`<target>`: The nick of the user or name of the channel you want to send your message to.

`<message>`: The message you want to send

### /irc_disconnect

Disconnects you from the currently connected IRC server.

### /irc_join <channel(s)> {key(s)}

Join one or multiple channels in the connected server

`<channel(s)>` Channels to join separated by spaces.

`<key(s)>` Passwords to the corresponding channels in the same order separated by spaces.

### /irc_leave <channel(s)>

`<channel(s)>` Channels to leave separated by spaces.

<br>

## What servers are supported?

The client is built using my [ft_irc](https://github.com/harmonie-durrant/ft_irc) repository as the server. [irssi](https://irssi.org) was used as a reference client to make the server.

Tests for more official servers are planned for the future.

<br>

## 🗺️ Roadmap

Planned updates
- [x] Auto connect to a configured server
- [ ] Better irc_help
- [ ] Achievements
- [ ] Handle server replies (errors etc...)
- [ ] Custom GUI
- [ ] GUI configurable to be put into a handheld item or block
- [ ] irc_connect optional password
- [ ] Handle duplicate Nicknames
- [ ] Limit nickname length
- [ ] irc_whois command
- [ ] Operator commands ban, kick, topic
- [ ] Ping server to check connection
- [ ] Auto reply mode (a sort of DND mode)
- [ ] Customize text coloring (themes)
- [ ] CAP [LS, REQ, END...] IRC commands

Planned for much later
- [ ] Ability to spin up custom IRC server for current world/server
