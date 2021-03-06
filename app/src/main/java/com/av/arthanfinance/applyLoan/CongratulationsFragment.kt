package com.av.arthanfinance.applyLoan

import android.content.Intent
import android.graphics.LinearGradient
import android.graphics.Shader
import android.os.Build
import android.os.Bundle
import android.text.TextPaint
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.annotation.RequiresApi
import com.av.arthanfinance.R


class CongratulationsFragment : Fragment() {
    private lateinit var tvCongrats: TextView
    private lateinit var btnNext: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.layout_loan_congrats, container, false)
        tvCongrats = view.findViewById(R.id.tv_congrats)
        btnNext = view.findViewById(R.id.btn_submit)
        val paint: TextPaint = tvCongrats.getPaint()
        val width = paint.measureText("Upload your Aadhaar Card")

        val shader = LinearGradient(0f, 0f, width, tvCongrats.textSize, resources.getColor(
            R.color.dark_orange2, activity?.theme), resources.getColor(
            R.color.indigoBlue, activity?.theme), Shader.TileMode.CLAMP)
        tvCongrats.paint.shader = shader

        btnNext.setOnClickListener{
            val intent = Intent(activity?.applicationContext, LoanApplicationSubmittedActivity::class.java)
            val loanId = arguments?.getString("loanId")
            intent.putExtra("loanId",loanId)
            startActivity(intent)
        }
        return view
    }

}