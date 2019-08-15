package com.blcs.customview

import android.animation.AnimatorInflater
import android.content.Intent
import android.graphics.drawable.Animatable
import android.os.Bundle
import android.support.graphics.drawable.AnimatedVectorDrawableCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import com.blcs.customview.anim.FrameAnimation
import com.blcs.customview.anim.ObjectAnimation
import com.blcs.customview.anim.TweenAnimation
import com.blcs.customview.anim.ValueAnimation
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), View.OnClickListener {
    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.btn_alpha -> {
                val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.alpha)
                custom_view.startAnimation(TweenAnimation.getAlpha())
            }
            R.id.btn_rotate -> {
                val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.rotate)
                custom_view.startAnimation(TweenAnimation.getRotate())
            }
            R.id.btn_scale -> {
                val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.scale)
                custom_view.startAnimation(TweenAnimation.getScale())
            }
            R.id.btn_translate -> {
                val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.translate)
                custom_view.startAnimation(TweenAnimation.getTranslate())
            }
            R.id.btn_set -> {
                val loadAnimation = AnimationUtils.loadAnimation(this, R.anim.set)
                custom_view.startAnimation(TweenAnimation.getSet())
            }
            R.id.btn_frame -> {
//                val animationDrawable = iv_main.drawable as AnimationDrawable
//                animationDrawable.start()
                //代码实现
                val animation = FrameAnimation.getAnimation(this)
                iv_main.setBackgroundDrawable(animation)
                animation.start()
            }
            R.id.btn_value_animation -> {
                ValueAnimation.startAnimator(iv_main)
            }
            R.id.btn_object_animation -> {
                  //  对象动画
//                val animator = ObjectAnimator.ofFloat(iv_main, "alpha", 0f, 1f)
//                animator.repeatCount = Animation.INFINITE
//                animator.repeatMode = REVERSE
//                animator.duration = 250
//                animator.start()
                //组合动画
//                ObjectAnimation.start(iv_main)
                //xml动画
                val loadAnimator = AnimatorInflater.loadAnimator(this, R.animator.object_animation)
                loadAnimator.setTarget(iv_main)
                loadAnimator.start()
            }
            R.id.btn_propertyValuesHolder ->{
                ObjectAnimation.property(iv_main)
            }
            R.id.btn_keyFrame ->{
                ObjectAnimation.keyFrame(iv_main)
            }
            R.id.btn_animate ->{
                ObjectAnimation.viewProperty(iv_main)
            }
            R.id.btn_svg ->{
                val create = AnimatedVectorDrawableCompat.create(this, R.drawable.svg_animation)
                iv_main.setImageDrawable(create)
                val animatable = iv_main.getDrawable() as Animatable
                animatable.start()
            }

            R.id.btn_loupeView ->{
                startActivity(Intent(this,LoupeViewActivity::class.java))
            }
            R.id.btn_scratch ->{
                startActivity(Intent(this,ScratchCardActivity::class.java))
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        btn_alpha.setOnClickListener(this)
        btn_rotate.setOnClickListener(this)
        btn_scale.setOnClickListener(this)
        btn_translate.setOnClickListener(this)
        btn_set.setOnClickListener(this)
        btn_frame.setOnClickListener(this)
        btn_value_animation.setOnClickListener(this)
        btn_object_animation.setOnClickListener(this)
        btn_propertyValuesHolder.setOnClickListener(this)
        btn_keyFrame.setOnClickListener(this)
        btn_animate.setOnClickListener(this)
        btn_svg.setOnClickListener(this)
        btn_loupeView.setOnClickListener(this)
        btn_scratch.setOnClickListener(this)
    }

}
