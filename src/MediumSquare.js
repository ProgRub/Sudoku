import React from "react";
import Cell from "./Cell";

class MediumSquare extends React.Component {
  constructor(props) {
    super(props);
    this.state = {
      x: this.props.x,
      y: this.props.y,
    };
  }

  render() {
    const rows = [];
    for (let y = 0; y < this.props.size; y++) {
      const cols = [];
      for (let x = 0; x < this.props.size; x++) {
        cols.push(
          <td>
            <Cell
              x={this.state.x * this.props.size + x}
              y={this.state.y * this.props.size + y}
              mediumSquareIndex={this.state.x * this.props.size + this.state.y}
            ></Cell>
          </td>
        );
      }

      rows.push(<tr>{cols}</tr>);
    }

    return (
      <div className="mediumSquare" style={{ border: "thick solid #0000FF" }}>
        <table cellSpacing="0">
          <tbody>{rows}</tbody>
        </table>
      </div>
    );
  }
}

export default MediumSquare;
