import React from "react";
import "./App.css";
import Grid from "./Grid";
class App extends React.Component {
  constructor(props) {
    super(props);

    this.onClick = this.onClick.bind(this);
  }

  onClick(event) {
    let cellsList = document.querySelectorAll(".cell");
    let totalSquares = cellsList.length;
    // Math.pow(
    //   document
    //     .querySelectorAll(".grid")
    //     .item(0)
    //     .childNodes.item(0)
    //     .childNodes.item(0).childNodes.length,
    //   4
    // );
    for (var index = 0; index < totalSquares; index++) {
      if (
        this.checkIfValid(cellsList, Math.sqrt(totalSquares), cellsList[index])
      ) {
        // console.log(true);
        cellsList[index].style.background = "green";
      } else {
        // console.log(false);
        cellsList[index].style.background = "red";
      }
    }
  }

  checkIfValid(cellsList, gridSize, cell) {
    var aux = cell.getAttribute("id").split("-").slice(1);
    return (
      cell.getAttribute("value") !== "" &&
      this.checkRows(cellsList, gridSize, aux[0], cell) &&
      this.checkColumns(cellsList, gridSize, aux[1], cell) &&
      this.checkMediumSquare(cellsList, gridSize, aux[2], cell)
    );
  }

  checkRows(listOfCells, gridSize, columnIndex, cell) {
    var listIndex =
      Math.floor(columnIndex / Math.sqrt(gridSize)) * gridSize +
      (columnIndex % Math.sqrt(gridSize));
    var count = 0;
    // console.log(listIndex);
    while (listIndex < Math.pow(gridSize, 2)) {
      //CHECKING THE CORRECT CELLS
      // console.log(listIndex)
      if (
        listOfCells[listIndex] !== cell &&
        parseInt(listOfCells[listIndex].getAttribute("value"), 10) ===
          parseInt(cell.getAttribute("value", 10))
      ) {
        // console.log(parseInt(listOfCells[listIndex].getAttribute("value"), 10)+" "+value);
        //Make the background of the cells red
        return false;
      }
      if (count === Math.sqrt(gridSize) - 1) {
        listIndex += (gridSize - count) * Math.sqrt(gridSize);
        count = 0;
      } else {
        count++;
        listIndex += Math.sqrt(gridSize);
      }
    }
    return true;
  }

  checkColumns(listOfCells, gridSize, rowIndex, cell) {
    var listIndex =
      Math.floor(rowIndex / Math.sqrt(gridSize)) *
        (gridSize * Math.sqrt(gridSize)) +
      (rowIndex % Math.sqrt(gridSize)) * Math.sqrt(gridSize);
    var count = 0;
    // console.log(listIndex);
    while (listIndex < Math.pow(gridSize, 2)) {
      // console.log(listIndex);
      if (
        listOfCells[listIndex] !== cell &&
        parseInt(listOfCells[listIndex].getAttribute("value"), 10) ===
          parseInt(cell.getAttribute("value", 10))
      ) {
        // console.log(parseInt(listOfCells[listIndex].getAttribute("value"), 10)+" "+value);
        //Make the background of the cells red
        return false;
      }
      if (count === Math.sqrt(gridSize) - 1) {
        listIndex += gridSize - count;
        count = 0;
      } else {
        count++;
        listIndex++;
      }
    }
    return true;
  }

  checkMediumSquare(listOfCells, gridSize, index, cell) {
    // console.log(
    //   index +
    //     " " +
    //     ((index % Math.sqrt(gridSize)) * gridSize * Math.sqrt(gridSize) +
    //       Math.floor(index / Math.sqrt(gridSize)) * gridSize)
    // ); //THIS ONE IS CORRECT
    var aux = Array.from(listOfCells);
    var mediumSquare = aux.slice(
      (index % Math.sqrt(gridSize)) * gridSize * Math.sqrt(gridSize) +
        Math.floor(index / Math.sqrt(gridSize)) * gridSize,
      (index % Math.sqrt(gridSize)) * gridSize * Math.sqrt(gridSize) +
        Math.floor(index / Math.sqrt(gridSize)) * gridSize +
        gridSize
    );
    for (var listIndex = 0; listIndex < gridSize; listIndex++) {
      // console.log(
      //   mediumSquare[listIndex].getAttribute("id").split("-").slice(1)
      // );
      if (
        mediumSquare[listIndex] !== cell &&
        parseInt(mediumSquare[listIndex].getAttribute("value"), 10) ===
          parseInt(cell.getAttribute("value", 10))
      ) {
        // console.log(value);
        //Make the background of the cells red
        return false;
      }
    }
    return true;
  }

  render() {
    return (
      <div id="app">
        <Grid
          id="grid"
          size="9"
          style={{
            position: "absolute",
            left: "50%",
            top: "50%",
            transform: "translate(-50%, -50%)",
          }}
        ></Grid>
        <input
          type="button"
          onClick={this.onClick}
          value="Is It Correct?"
          style={{ fontFamily: "Times New Roman" }}
        />
      </div>
    );
  }
}

export default App;
