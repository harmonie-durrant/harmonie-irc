# Harmonie IRC

An IRC client for minecraft made by [Harmonie Durrant](https://github.com/harmonie-durrant).

## 💻 Commands

All commands start with `irc_` to avoid conflicting commands.

`<>`: obligatory argument

`{}`: optional argument

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

<br>

## 🗺️ Roadmap

Basic IRC functionality
- [ ] Receive info back from the server
- [ ] irc_msg command
- [ ] irc_disconnect command
- [ ] irc_join command
- [ ] irc_leave command

Advanced updates
- [ ] irc_disconnect optional password
- [ ] Handle duplicate Nicknames
- [ ] Limit nickname length
- [ ] irc_whois command
- [ ] Operator commands ban, kick, topic
- [ ] Ping server to check connection
- [ ] Auto reply mode (a sort of DND mode)
- [ ] Customize text coloring
- [ ] CAP [LS, REQ, END...] IRC commands
- [ ] Spin up custom server for current world/server
