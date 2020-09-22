import React from 'react';

class Cell extends React.Component {
  constructor(props) {
    super(props);


    this.state = {
      x: this.props.x,
      y: this.props.y,
      value: ''
    };

    // this.handleChange = this.handleChange.bind(this);
    this.handleKeyDown = this.handleKeyDown.bind(this);
  }

  // onClick(e) {
  //   e.currentTarget.style.backgroundColor = this.props.colors[this.props.colorIndex];
  // };
  // handleChange(event) {
  //   // console.log(event.target)
  //   this.setState({value: event.target.value});
  // };

  handleKeyDown(event) {
    // console.log(event.key);
    if (event.key >= 0 && event.key <= 9 && this.state.value.length === 0) {
      this.setState({ value: this.state.value + event.key });
    }
    else if (event.key === "Backspace" && this.state.value.length === 1) {
      this.setState({ value: this.state.value.substring(0, this.state.value.length - 1) });
    }
  }

  render() {
    return (
      <form>
        <input id={`cell-${this.props.x}-${this.props.y}`} autocomplete="off" className='cell' type="text" value={this.state.value} onKeyDown={this.handleKeyDown} style={{ width: '45px', height: '45px', fontSize: '30px', fontFamily: 'Times New Roman', textAlign: 'center' }} />
      </form>
    );
  };
};

export default Cell;