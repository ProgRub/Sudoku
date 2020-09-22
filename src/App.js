import React from 'react';
// import logo from './logo.svg';
import './App.css';
import Grid from './Grid'
class App extends React.Component {
  constructor(props) {
    super(props);

    this.onClick = this.onClick.bind(this);
  }

  onClick(e) {

  }

  render() {
    return (
      <div id='app'>
        <Grid size="9" style={{
        position: 'absolute', left: '50%', top: '50%',
        transform: 'translate(-50%, -50%)'
    }}></Grid>
        <input type='button' onClick={this.onClick} value='Reset' />
      </div >
    );
  }
}
// function App() {
//   return (
//     // <div className="App">
//     //   <header className="App-header">
//     //     <img src={logo} className="App-logo" alt="logo" />
//     //     <p>
//     //       Edit <code>src/App.js</code> and save to reload.
//     //     </p>
//     //     <a
//     //       className="App-link"
//     //       href="https://reactjs.org"
//     //       target="_blank"
//     //       rel="noopener noreferrer"
//     //     >
//     //       Learn React
//     //     </a>
//     //   </header>
//     // </div>
//     // <Grid />
//     <div id='app'>
//         <Grid width="3" height="3" playerOneColor="blue" playerTwoColor="pink"></Grid>
//         <input type='button' onClick={ this.onClick } value='Reset' />
//       </div>
//   );
// }

export default App;
