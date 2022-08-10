import React, {  useState } from "react";
import jwtDecode from "jwt-decode";
import { employeeLogin } from "../API/api";
import { useNavigate } from "react-router-dom";
import '../css/login.css'
import ReactModal from "react-modal";

const LogIn = () => {
  const navigate = useNavigate()
  const [values, setValues] = useState({
    username: '',
    password: ''
  });
  const Styles = {
    content: {
      top: '35%',
      left: '50%',
      right: '40%',
      bottom: 'auto',
      marginRight: '-40%',
      width: '40%',
      transform: 'translate(-50%, -20%)',
    },
    
  };
  const [enteredLogin, setEnteredLogin] = useState(false)
  const [loginModal, setLoginModal] = useState(false)
  const [validCredentials,setValidCredentials] = useState(true)
  const loginHandler = (e) => {
    e.preventDefault()
    if (values.username.trim().length === 0 || values.password.trim().length === 0) {
      setValidCredentials(true)
      setEnteredLogin(false)
      setLoginModal(true)
    }
    else {
      setLoginModal(false)
      setEnteredLogin(true)
      submitHandler(e)
    }
  }
const changeHandler = (e) => {
    e.persist();
    setValues(values => ({
    ...values,
    [e.target.name]: e.target.value
    }))
}
  const showLoginModal = () => {
    setLoginModal(true)
    setEnteredLogin(false)
  }
  
  const submitHandler = (e) => {
    e.preventDefault()
  if (enteredLogin) {
    employeeLogin(values)
      .then((res) => {
        console.log(res.data);
        // localStorage.setItem('USER_KEY',res.data.jwtToken);
        if (res.data.success===false) {
          console.log("Invalid credentials")
          setValidCredentials(false)
        }
        else {
          const token = jwtDecode(res.data.access_token)
          console.log(token)
          console.log(token.roles)
          localStorage.setItem('roles', token.roles)
          localStorage.setItem('token', res.data.access_token)
          localStorage.setItem('username', token.sub)
          console.log(jwtDecode(res.data.access_token))
          // localStorage.setItem('USER_NAME',res.data.username);

          //  window.location.assign('/dashboard');
          navigate('/profileview')
        }
      }).catch((err) => {
        console.log(err);
      })
  }
  
}
  const hideLoginModal = () => {
    setLoginModal(false)
    setEnteredLogin(true)
    
  }
  const hideCredentialModal = () => {
    setValidCredentials(true)
  }
  
    return (
      <>
        <div className="auth-wrapper">
        <h1>Welcome To BJIT Academy</h1>
          <div className="auth-inner">
            <form onSubmit={(e) => {
              loginHandler(e)
            }}>
        <h3>Sign In</h3>
        <div className="mb-3">
          <label>Username</label>
          <input
            type="text"
              className="form-control"
              id="name"
              placeholder="Enter username"
              name="username"
              onChange={changeHandler}
              value={values.username}
          />
        </div>
        <div className="mb-3">
          <label>Password</label>
          <input
            type="password"
              className="form-control"
              id="password"
              placeholder="Enter password"
              name="password"
              onChange={changeHandler}
              value={values.password}
          />
        </div>
        
        <div className="d-grid">
          <button type="submit"  className="btn btn-primary">
            Submit
          </button>
        </div>
       
            </form>
            </div>
        </div>
        <ReactModal isOpen={loginModal} style={Styles}>
        <h3 className="loginh3">Please enter your username and password to login.</h3>
                        <div className="d-grid">
                            <button className="btn btn-info" id="loginButton" onClick={hideLoginModal}>
                                OK
                            </button>
                        </div>
        </ReactModal>
        <ReactModal isOpen={!validCredentials} style={Styles}>
        <h3 className="loginh3">Incorrect Username or Password. Please try again.</h3>
                        <div className="d-grid">
                            <button type="button" className="btn btn-info" id="loginButton"  onClick={hideCredentialModal}>
                                OK
                            </button>
                        </div>
        </ReactModal>
        </>
    )
}
export default LogIn