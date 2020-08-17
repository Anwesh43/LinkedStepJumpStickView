package com.anwesh.uiprojects.stepjumpstickview

/**
 * Created by anweshmishra on 17/08/20.
 */

import android.view.View
import android.view.MotionEvent
import android.graphics.Paint
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.RectF
import android.content.Context
import android.app.Activity

val colors : Array<String> = arrayOf("#3F51B5", "#4CAF50", "#F44336", "#009688", "#03A9F4")
val parts : Int = 4
val scGap : Float = 0.02f / parts
val sizeFactor : Float = 3.8f
val backColor : Int = Color.parseColor("#BDBDBD")
val delay : Long = 20
