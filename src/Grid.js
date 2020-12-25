import React from "react";
import MediumSquare from "./MediumSquare";

class Grid extends React.Component {
  constructor(props) {
    super(props);
    this.state = { size: this.props.size };
  }

  render() {
    const rows = [];
    for (let y = 0; y < Math.sqrt(this.state.size); y++) {
      const cols = [];
      for (let x = 0; x < Math.sqrt(this.state.size); x++) {
        cols.push(
          <td
          key={x+y}>
            <MediumSquare
              size={Math.sqrt(this.state.size)}
              x={x}
              y={y}
            ></MediumSquare>
          </td>
        );
      }
      rows.push(<tr
        key={y}>{cols}</tr>);
    }

    return (
      <div className="grid" style={this.props.style}>
        <table
          cellSpacing="0"
          style={{ borderCollapse: "collapse", border: 0 }}
        >
          <tbody>{rows}</tbody>
        </table>
      </div>
    );
  }
}

export default Grid;
