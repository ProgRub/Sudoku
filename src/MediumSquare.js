import React from 'react';
import Cell from './Cell'

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
            const cols = []
            for (let x = 0; x < this.props.size; x++) {
                cols.push(<td><Cell x={x} y={y} ></Cell></td>);
            }

            rows.push(<tr>{cols}</tr>);
        }

        return (
            <div class='mediumSquare' style={{border:'thick solid #0000FF'}}>
                <table cellspacing="0" >
                    <tbody>
                        {rows}
                    </tbody>
                </table>
            </div>
        )
    }
}

export default MediumSquare;