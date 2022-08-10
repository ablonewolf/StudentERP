import './App.css';
import '../node_modules/bootstrap/dist/css/bootstrap.min.css';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom'
import LogIn from './Component/LogIn';
import Profile from './Component/Profile';
import ProfileView from './Component/ProfileView';
function App() {
  return (
    <Router>
      <div className="App">
        {/* <nav className="navbar navbar-expand-lg navbar-light fixed-top">
          <div className="container">
            <Link className="navbar-brand" to={'/sign-in'}>
                BJIT Academy
            </Link>
            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
              <ul className="navbar-nav ml-auto">
                <li className="nav-item">
                  <Link className="nav-link" to={'/sign-in'}>
                    Login
                  </Link>
                </li>
                <li className="nav-item">
                  <Link className="nav-link" to={'/sign-up'}>
                    Sign up
                  </Link>
                </li>
              </ul>
            </div>
          </div>
        </nav> */}
        <div >
          <div >
            <Routes>
              <Route exact path="/" element={<LogIn />} />
              <Route path="/sign-in" element={<LogIn />} />
              <Route exact path="/profile" element={<Profile />} />
              <Route exact path="/profileview" element={ <ProfileView/>} />
            </Routes>
          </div>
        </div>
      </div>
    </Router>
  )
}

export default App;
