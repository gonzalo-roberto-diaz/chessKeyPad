/**
  * An object that just outputs the solution for chess pieces to the console
  */
object Solution extends KeyPadTrait{

  def main(args: Array[String]): Unit ={


    //a list of the pieces, plus their respective derivating functions
    val pieces = List((peon,  peonDerivFunc), (knight, knightDerivFunc), (bishop, bishopDerivFunc), (rook, rookDerivFunc), (queen, queenDerivFunc), (king, kingDerivFunc))


    println ("%1$-20s %2$20s %3$20s".format("Piece name", "Numbers with repetition", "       Numbers without repetition"))

    pieces.foreach(elem => {

      val withRepetition = calculate(elem._2, true, 6)
      val withoutRepetition = calculate(elem._2, false, 6)

      println ("%1$-20s %2$20d %3$20d".format(elem._1.name, withRepetition, withoutRepetition))

    });



    def calculate(pieceDerivator: Point=> List[Point], withRepetition: Boolean, numberLength: Int): Int ={
      var pieceHydras: List[Hydra[Point]] = Nil

      for (endPoint <- keyPad.getPoints) {
        val endHydra = Hydra(List(endPoint), Nil)
        pieceHydras = pieceHydras ::: endHydra.repeatedlyProcess(pieceDerivator, withRepetition, numberLength)
      }
      //remove numbers starting with 0 and 1
      pieceHydras = pieceHydras.map(kh => Hydra(kh.heads diff List(kp0, kp1), kh.tail)).filter(h => h.heads.nonEmpty)

      //sum the heads
      pieceHydras.map(kh => kh.heads.size).sum
    }



  }

}
