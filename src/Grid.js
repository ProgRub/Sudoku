import React from 'react';
import MediumSquare from './MediumSquare';

class Grid extends React.Component {
  constructor(props) {
    super(props);
    this.grid = new Array();
  }

  render() {
    const rows = [];
    for (let y = 0; y < Math.sqrt(this.props.size); y++) {
      const cols = []
      for (let x = 0; x < Math.sqrt(this.props.size); x++) {
        cols.push(<td><MediumSquare size={Math.sqrt(this.props.size)} x={x} y={y} ></MediumSquare></td>);
      }
      rows.push(<tr>{cols}</tr>);
    }

    return (
      <div class='grid' style={this.props.style}>
        <table cellspacing="0" style={{borderCollapse:'collapse',border:0}}>
          <tbody>
            {rows}
          </tbody>
        </table>
      </div>
    )
  }
}

export default Grid;