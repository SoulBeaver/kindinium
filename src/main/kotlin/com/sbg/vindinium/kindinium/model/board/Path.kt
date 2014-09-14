package com.sbg.vindinium.kindinium.model.board

import com.sbg.vindinium.kindinium.model.Position

enum class Direction {
    North
    East
    South
    West
    Stay
}

data class Path(path: List<Position>, directions: List<Direction>)