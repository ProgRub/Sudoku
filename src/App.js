import React from "react";
import "./App.css";
import Grid from "./Grid";
import Popup from "reactjs-popup";
import "reactjs-popup/dist/index.css";
class App extends React.Component {
  constructor(props) {
    super(props);

    this.onClick = this.onClick.bind(this);
    // this.newGrid = this.newGrid.bind(this);
  }

  clearGrid() {
    let cellsList = document.querySelectorAll(".cell");
    for (var index = 0; index < cellsList.length; index++) {
      cellsList[index].value = "";
      // console.log(cellsList[index])
      // cellsList[index].ref.setState({ value: "" });
    }
  }

  newGrid(difficulty) {
    // console.log(difficulty);
    let url =
      "https://cors-anywhere.herokuapp.com/https://nine.websudoku.com/?level=" +
      difficulty;
    this.clearGrid();

    const rp = require("request-promise");
    const $ = require("cheerio");

    rp(url)
      .then(function (html) {
        for (
          var row = 0;
          row < Math.sqrt(document.querySelectorAll(".cell").length);
          row++
        ) {
          for (
            var column = 0;
            column < Math.sqrt(document.querySelectorAll(".cell").length);
            column++
          ) {
            // console.log($("#c" + row + column, html)
            // .children()
            // .first()
            // .attr())
            let value = $("#c" + row + column, html)
              .children()
              .first()
              .attr().value;
            // console.log(value);
            if (value) {
              let cell = document.querySelector(
                "[id^='cell-" + column + "-" + row + "']"
              );
              cell.value = value;
              cell.readOnly = true;
              cell.style.backgroundColor = "gray";
              // console.log(cell);
              // cell.setState({ isClue: true });
            } else {
              let cell = document.querySelector(
                "[id^='cell-" + column + "-" + row + "']"
              );
              cell.readOnly = false;
              cell.value = "";
              // cell.setState({ isClue: false });
            }
          }
        }
      })
      .catch(function (err) {
        console.log(err);
      });
  }

  onClick(event) {
    let cellsList = document.querySelectorAll(".cell");
    let totalSquares = cellsList.length;
    for (var index = 0; index < totalSquares; index++) {
      // console.log(index);
      // console.log(cellsList[index]);
      if (!cellsList[index].readOnly) {
        if (
          this.checkIfValid(
            cellsList,
            Math.sqrt(totalSquares),
            cellsList[index]
          )
        ) {
          // console.log(true);
          cellsList[index].style.background = "green";
        } else {
          // console.log(false);
          cellsList[index].style.background = "red";
        }
      }
    }
  }

  checkIfValid(cellsList, gridSize, cell) {
    var aux = cell.getAttribute("id").split("-").slice(1);
    return (
      cell.value.length !== 0 &&
      this.checkColumn(cellsList, gridSize, aux[0], cell) &&
      this.checkRow(cellsList, gridSize, aux[1], cell) &&
      this.checkMediumSquare(cellsList, gridSize, aux[2], cell)
    );
  }

  checkColumn(listOfCells, gridSize, columnIndex, cell) {
    var listIndex =
      Math.floor(columnIndex / Math.sqrt(gridSize)) * gridSize +
      (columnIndex % Math.sqrt(gridSize));
    var count = 0;
    while (listIndex < Math.pow(gridSize, 2)) {
      // console.log(listOfCells[listIndex])
      if (
        listOfCells[listIndex] !== cell &&
        listOfCells[listIndex].value === cell.value
      ) {
        // console.log("COLUMN");
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

  checkRow(listOfCells, gridSize, rowIndex, cell) {
    var listIndex = gridSize * Math.sqrt(gridSize) * Math.floor(rowIndex / Math.sqrt(gridSize)) + ((rowIndex % Math.sqrt(gridSize))*Math.sqrt(gridSize));
    var count = 0;
    var limit = listIndex + Math.sqrt(gridSize) * (gridSize -(Math.sqrt(gridSize) - 1));
    while (listIndex <limit) {
      // console.log(listOfCells[listIndex])
      if (
        listOfCells[listIndex] !== cell &&
        listOfCells[listIndex].value === cell.value
      ) {
        // console.log("ROW");
        // console.log(listIndex);
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
    var aux = Array.from(listOfCells);
    var mediumSquare = aux.slice(
      (index % Math.sqrt(gridSize)) * gridSize * Math.sqrt(gridSize) +
        Math.floor(index / Math.sqrt(gridSize)) * gridSize,
      (index % Math.sqrt(gridSize)) * gridSize * Math.sqrt(gridSize) +
        Math.floor(index / Math.sqrt(gridSize)) * gridSize +
        gridSize
    );
    for (var listIndex = 0; listIndex < gridSize; listIndex++) {
      if (
        mediumSquare[listIndex] !== cell &&
        mediumSquare[listIndex].value === cell.value
      ) {
        // console.log("SQUARE");
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
        <Popup
          trigger={
            <input
              type="button"
              // onClick={this.newGrid}
              value="New Grid"
              style={{ fontFamily: "Times New Roman" }}
            />
          }
          modal
          nested
        >
          {(close) => (
            <div className="modal">
              <button
                onClick={() => {
                  this.newGrid(1);
                  close();
                }}
                style={{ fontFamily: "Times New Roman" }}
              >
                Easy
              </button>
              <button
                onClick={() => {
                  this.newGrid(2);
                  close();
                }}
                style={{ fontFamily: "Times New Roman" }}
              >
                Medium
              </button>
              <button
                onClick={() => {
                  this.newGrid(3);
                  close();
                }}
                style={{ fontFamily: "Times New Roman" }}
              >
                Hard
              </button>
              <button
                onClick={() => {
                  this.newGrid(4);
                  close();
                }}
                style={{ fontFamily: "Times New Roman" }}
              >
                Very Hard
              </button>
            </div>
          )}
        </Popup>
      </div>
    );
  }
}

export default App;
