
import React from 'react'
import './App.css';
import RegisterPage from './Pages/RegisterPage';
import LoginPage from './Pages/LoginPage';
import HomePage from './Pages/HomePage'
import { Route, BrowserRouter as Router, Switch, Redirect } from 'react-router-dom';


class App extends React.Component {

 
  render() {
    return (
      <div className="App" style={{display:"flex", width:'100%',height:'100%',justifyContent:'center'}}>
        <Router>
          <Switch>
            <Route exact path="/" component={LoginPage} />
            <Route path="/register" component={RegisterPage} />
            <Route path="/home" component={HomePage} />
            <Redirect from="*" to="/"/>
          </Switch>
        </Router>
      </div>
    )
  }
}

export default App;
