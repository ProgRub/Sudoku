import React from "react";

class Cell extends React.Component {
  constructor(props) {
    super(props);

    this.state = {
      x: this.props.x,
      y: this.props.y,
      value: "",
    };

    this.handleKeyDown = this.handleKeyDown.bind(this);
    this.onClick = this.onClick.bind(this);
  }

  onClick(event) {
    let cellsList = document.querySelectorAll(".cell");
    for (var index = 0; index < cellsList.length; index++) {
      if (!cellsList[index].readOnly)
        cellsList[index].style.background = "white";
    }
  }

  handleKeyDown(event) {
    if (
      !document.querySelector(
        "[id^='cell-" + this.state.x + "-" + this.state.y + "']"
      ).readOnly
    ) {
      if (event.key >= 1 && event.key <= 9 && this.state.value.length === 0) {
        this.setState({ value: this.state.value + event.key });
      } else if (event.key === "Backspace" && this.state.value.length === 1) {
        this.setState({
          value: this.state.value.substring(0, this.state.value.length - 1),
        });
      }
    }
  }

  render() {
    return (
      <form>
        <input
          id={`cell-${this.state.x}-${this.state.y}-${this.props.mediumSquareIndex}`}
          autoComplete="off"
          className="cell"
          type="text"
          value={this.state.value}
          onKeyDown={this.handleKeyDown}
          onClick={this.onClick}
          style={{
            width: "45px",
            height: "45px",
            fontSize: "30px",
            fontFamily: "Times New Roman",
            textAlign: "center",
          }}
        />
      </form>
    );
  }
}

export default Cell;
