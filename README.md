# Harmonie IRC

An IRC client for minecraft made by [Harmonie Durrant](https://github.com/harmonie-durrant).

## Commands

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