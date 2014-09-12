# Kindinium

## Vindinium for Kotlin

Welcome to [Vindinium](vindinium.org), the fun AI challenge that lets you battle heroes and goblins!
This client is written in Kotlin and is fully functional, allowing you to jump right in, write a bot,
and smash those heroes in epic battle!

## What does it offer?

Kindinium boasts:

- Training and Arena mode
- Customization of training mode (number of turns, map)
- A MetaBoard. It analyzes the json board and gives you information on the locations of heroes, mines, and taverns

## What do I need?

If you haven't registered yourself yet, please head to the [Vindinium Registration Page](http://vindinium.org/register)
and create a bot. Once you have registered you should see an API Key such as 'hie44sww'- this API Key
is bound to *dummy123*.

Open the file *VindiniumClient.kt* and modify the line

    val API_KEY = ""
   
That's technically it! A random bot will now participate in a training game whenever you launch the app.

## How do I change the game mode?

The program supports both the *arena* and *training* modes. By default a training mode with 300
turns and a random map is chosen.

### I want to play in Arena mode!

Run the game with *-m arena*

### I want to play in a predefined map!

(Only in training mode) Run the game with *-map m#* where m# is in range of m1..m6

### I want to change the number of turns a game is played!

(Only in training mode) Run the game with *-t #* where # is the number of turns you'd like to play.

## How do I change my bot?

The random bot is simple, stupid, and not likely to win you any prizes. You *could* simply extend
the random bot, but then it wouldn't be a RandomBot anymore :) Instead, head to *Main.kt* and
modify the line

    val client = VindiniumClient(RandomBot())
   
With the bot of your choosing. Please implement the *Bot* trait.

## What's in store for future iterations?

I'd like to expand the MetaBoard. It's great that you can see the location of everything in the game, or
even go straight to the entire board and create your own pathfinding algorithms. However, I'd like to take
care of the pathfinding in future versions, so that you can focus on the algorithms necessary to defeat
your opponent!

## I have further questions!

Don't hesitate to ask me anything. My knowledge so far isn't unlimited, but I enjoy helping out
as much as possible.