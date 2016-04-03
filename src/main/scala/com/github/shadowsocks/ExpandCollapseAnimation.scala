package com.github.shadowsocks

import android.view.View
import android.view.ViewGroup.LayoutParams
import android.view.animation.{Animation, Transformation}

/**
  * @author Mygod
  */
final class ExpandCollapseAnimation(view: View, expanding: Boolean = false) extends Animation {
  setDuration(500)
  view.setVisibility(View.VISIBLE)
  private val maxHeight = if (expanding) {
    // Older versions of android (pre API 21) cancel animations for views with a height of 0.
    view.getLayoutParams.height = 1
    view.measure(0, 0)
    view.getMeasuredHeight
  } else view.getHeight

  override protected def applyTransformation(interpolatedTime: Float, t: Transformation) = if (interpolatedTime < 1) {
    val t = if (expanding) 1 - interpolatedTime else interpolatedTime
    view.getLayoutParams.height = ((1 - t * t) * maxHeight).toInt
    view.requestLayout
  } else if (expanding) {
    view.getLayoutParams.height = LayoutParams.WRAP_CONTENT
    view.requestLayout
  } else view.setVisibility(View.GONE)

  override def willChangeBounds = true
}
