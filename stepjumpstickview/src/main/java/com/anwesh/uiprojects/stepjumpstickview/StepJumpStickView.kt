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
val sizeFactor : Float = 6.8f
val backColor : Int = Color.parseColor("#BDBDBD")
val delay : Long = 20

fun Int.inverse() : Float = 1f / this
fun Float.maxScale(i : Int, n : Int) : Float = Math.max(0f, this - i * n.inverse())
fun Float.divideScale(i : Int, n : Int) : Float = Math.min(n.inverse(), maxScale(i, n)) * n
fun Float.sinify() : Float = Math.sin(this * Math.PI).toFloat()

fun Canvas.drawStepJumpStick(scale : Float, w : Float, h : Float, paint : Paint) {
    val sc1 : Float = scale.divideScale(0, parts)
    val sc2 : Float = scale.divideScale(1, parts)
    val sc3 : Float = scale.divideScale(2, parts)
    val sc4 : Float = scale.divideScale(3, parts)
    val size : Float = Math.min(w, h) / sizeFactor
    val sc11 : Float = sc1.divideScale(0, 2)
    val sc12 : Float = sc1.divideScale(1, 2)
    val sc21 : Float = sc2.divideScale(0, 2)
    val sc22 : Float = sc2.divideScale(1, 2)
    val sf1 : Float = sc12.sinify()
    val sf2 : Float = sc22.sinify()
    save()
    translate(w / 2, h)
    for (j in 0..1) {
        val r : Float = size * (sc1 - sc4)
        save()
        scale(1f - 2 * j, 1f)
        translate(-w / 2 * (1 - sf1) * (1 - sf2), -(h / - size) * (sc2 + sc3))
        drawRect(RectF(-r, -r, r, r), paint)
        restore()
    }
    restore()
}

fun Canvas.drawSJSNode(i : Int, scale : Float, paint : Paint) {
    val w : Float = width.toFloat()
    val h : Float = height.toFloat()
    paint.color = Color.parseColor(colors[i])
    drawStepJumpStick(scale, w, h, paint)
}

class StepJumpStickView(ctx : Context) : View(ctx) {

    override fun onDraw(canvas : Canvas) {

    }

    override fun onTouchEvent(event : MotionEvent) : Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {

            }
        }
        return true
    }

    data class State(var scale : Float = 0f, var dir : Float = 0f, var prevScale : Float = 0f) {

        fun update(cb : (Float) -> Unit) {
            scale += scGap * dir
            if (Math.abs(scale - prevScale) > 1) {
                scale = prevScale + dir
                dir = 0f
                prevScale = scale
                cb(prevScale)
            }
        }

        fun startUpdating(cb : () -> Unit) {
            if (dir == 0f) {
                dir = 1f - 2 * prevScale
                cb()
            }
        }
    }

    data class Animator(var view : View, var animated : Boolean = false) {

        fun animate(cb : () -> Unit) {
            if (animated) {
                cb()
                try {
                    Thread.sleep(delay)
                    view.invalidate()
                } catch(ex : Exception) {

                }
            }
        }

        fun start() {
            if (!animated) {
                animated = true
                view.postInvalidate()
            }
        }

        fun stop() {
            if (animated) {
                animated = false
            }
        }
    }
 }
