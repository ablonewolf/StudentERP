import '../css/ProfileView.css'
import '../css/Modal-form.css'
import { Link, useNavigate } from 'react-router-dom';
import { getEmployee, updateCurrentEmployee } from '../API/api';
import { useEffect, useState } from 'react';
import ReactModal from "react-modal";
const ProfileView = () => {
  const navigate = useNavigate()
  const role = localStorage.getItem('roles');

  const [employee, setEmployee] = useState({
    name: '',
    username: '',
    password: '',
    id: '',
    email:''
  });
  const [currentInfo, setCurrentInfo] = useState({
    name:'',
    email: '',
  })
  const [updateModal, setUpdateModal] = useState(false)
  const customStyles = {
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
  const [enteredAllFields,setEnteredAllFields] = useState(true)
  
  const username = localStorage.getItem('username')
  const openModal = () => {
    setUpdateModal(true)
    // console.log("Modal open function called")
  }
  const hideModal = () => {
    setUpdateModal(false)
  }

  const hideErrorModal = () => {
    setEnteredAllFields(true)
  }
  const handleLogOut = (e) => {
    e.persist();
    localStorage.setItem('token',null);
        localStorage.setItem('roles',null);
        localStorage.setItem('username',null);

        navigate('/')
  }
  const submitHandler = (e) => {
    e.preventDefault()
    updateCurrentEmployee(username, currentInfo)
      .then((res) => {
        // console.log(res.data)
        console.log(currentInfo)
        setEmployee({
          ...employee,
          name: currentInfo.name,
          email: currentInfo.email
        })
      })
      .catch((err) => {
        if (err.response.data.status === 400) {
          setEnteredAllFields(false)
      }
      })
    setUpdateModal(false)
  }

  const nameChangeHandler = (e) => {
    e.persist()
    setCurrentInfo({
      ...currentInfo,
      name:e.target.value
    })

  }
  const emailChangeHandler = (e) => {
    e.persist()
    setCurrentInfo({
      ...currentInfo,
      email:e.target.value
      })
  }
  // const changeHandler = (e) => {
  //   e.persist()
  //   setCurrentInfo(currentInfo => ({
  //     ...currentInfo,
  //     [e.target.name]: e.target.value
  //   }))
  // }
// console.log('current info',currentInfo)
  useEffect(() => {
      // console.log(username)
      if (role.includes("Employee")) {
          getEmployee(username).then((response) => {
            // console.log(response.data)
            setEmployee({
              name: response.data.name,
              email: response.data.email,
              id: response.data.id,
              password: response.data.password,
              username:response.data.username
              
            })
            setCurrentInfo({
              name: response.data.name,
              email: response.data.email,
              
            })
          }).catch((err) => {
              console.log(err)
          })
      }
  }, [])
  

    return (
        <>
         <nav className="navbar navbar-expand-lg navbar-light fixed-top">
            <div className="container">
               <Link className="navbar-brand" to={'/profileview'}>
                BJIT Academy
              </Link>
            <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
              <ul className="navbar-nav ml-auto">
            <li className="nav-item">
            <Link className="nav-link" to={'/profile'}>
              All Employee
            </Link>
                </li>
                <li className="nav-item">
              <button className="btn btn-success" onClick={handleLogOut}>Log Out</button>
               </li>
          {/* <li className="nav-item">
            <Link className="nav-link" to={'/sign-up'}>
              Sign up
            </Link>
          </li> */}
        </ul>
      </div>
    </div>
     </nav>
     {(role.includes('Employee')) ? (
            <div align='center'>
            <div className="card">
              <h1>Welcome {employee.name}</h1>      
              <p className='profile-text'>Email : {employee.email}</p>
                <p className='profile-text'>ID : {employee.id}</p>
              <p className='buttons'>
                <button onClick={openModal}>Edit Profile  </button>
              </p>
            </div>
            {(updateModal) ? (
                        <ReactModal isOpen={updateModal} style={customStyles}>
                        <form onSubmit={submitHandler}>
                          <h3>Update your profile</h3>
                          <div className="mb-3">
                          <label>Name</label>
                          <input
                          type="text"
                          className="form-control"
                          placeholder="Name"
                          name='name'
                          onChange={nameChangeHandler}
                          value={currentInfo.name}
                            />
                          </div>
                            <div className="mb-3">
                            <label>Email address</label>
                            <input
                               type="email"
                               className="form-control"
                              placeholder="Enter email"
                              name='email'
                              onChange={emailChangeHandler}
                              value={currentInfo.email}
                                  />
                            </div>
                            <div className="d-grid">
                             <button className="btn btn-primary">
                              Submit
                             </button>
                           <button onClick={hideModal} className='btn btn-secondary'>
                             Cancel
                             </button>
                              </div>
                </form>
                </ReactModal>
            ) : (<></>)}
            {
                (!enteredAllFields) ?
                    (<ReactModal isOpen={!enteredAllFields} style={customStyles}>
                         <h3 className="loginh3">Please Enter all the details</h3>
                            <button type="button" className="btn btn-info" id="loginButton"  onClick={hideErrorModal}>
                                OK
                            </button>
                    </ReactModal>)
                    :
                    (<></>)
            }
          </div>
        ) : (<></>)}
        </>
    )
}
export default ProfileView