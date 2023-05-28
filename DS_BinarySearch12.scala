import scala.io.StdIn.readLine


object BinSearch {

	def binarySearch(arr: Array[Int], target: Int): Int = {
	  var low = 0
	  var high = arr.length - 1

	  while (low <= high) {
	    val mid = low + (high - low) / 2

	    if (arr(mid) == target) {
	      return mid // Target found at index 'mid'
	    }
	    else if (arr(mid) < target) {
	      low = mid + 1 // Target is in the right half of the array
	    }
	    else {
	      high = mid - 1 // Target is in the left half of the array
	    }
	  }

	  -1 // Target not found in the array
	}
	
    def main(args: Array[String]) {
	// Input array from the user
	val inputArray = readLine("Enter the elements of the array (space-separated): ")
	val arr = inputArray.split(" ").map(_.toInt)

	// Input target value from the user
	val target = readLine("Enter the target value to search: ").toInt

	// Perform binary search
	val result = binarySearch(arr, target)

	if (result != -1) {
	  println(s"Element $target found at index $result")
	} else {
	  println("Element not found in the array")
	}
    }
}









