package ru.netology.nmedia

class PostLogic (val post: Post) {
    private var likes : Int = 0
    private var share : Int = 0

    fun clickLike(){
        likes++
        post.numberLickes = roundingNumbers(likes)
    }

    fun cancelingClickLike(){
        likes--
        post.numberLickes = roundingNumbers(likes)
    }

    fun clickShare(){
        share++
        post.numberShare = roundingNumbers(share)
    }

    fun roundingNumbers(numbers: Int) : String{
        if(numbers < 1000) {
            return numbers.toString()
        }else if (numbers >= 1000 && numbers <= 10000 ){
            val numbersDouble: Double = (numbers / 100).toDouble() / 10
            return numbersDouble.toString() + "K"
        }else if(numbers >= 10000 && numbers < 1000000) {
            val numbersDouble: Int = numbers / 1000
            return numbersDouble.toString() + "K"
        }else {
            val numbersDouble: Double = numbers.toDouble() / 1000000
            return numbersDouble.toString() + "M"
        }
    }
}