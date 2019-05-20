package logic

class BMIForLbIn(private val mass: Int, private val height: Int) : Bmi {

    override fun calculateBmi(): Double {
        return (mass * 703.0 / (height*height))
    }
}