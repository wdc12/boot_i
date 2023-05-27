object Calculator {
    def main(args: Array[String]) {
        var ch:Char = 0
        var num1:Int = 0
        var num2:Int = 0
        var result:Int = 0

        print("Choose an operation to perform (+, -, *, /, %): ")
        ch = scala.io.StdIn.readChar()

        print("Enter the first number: ")
        num1 = scala.io.StdIn.readInt()

        print("Enter the second number: ")
        num2 = scala.io.StdIn.readInt()

        ch match {
            case '+' => result = num1 + num2
            case '-' => result = num1 - num2
            case '*' => result = num1 * num2
            case '/' => result = num1 / num2
            case '%' => result = num1 % num2
            case _ => print("Invalid operation\n")
        }

        println("Result: " + result)
    }
}