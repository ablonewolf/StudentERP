import React, {  useState, useEffect } from "react";
import { getEmployeeData,updateEmployee,deleteEmployee,addEmployee,addRoletoEmployee } from "../API/api"
import { Link } from "react-router-dom";
import '../css/table.css';
import ReactModal from "react-modal";
import '../css/login.css'
import '../css/ProfileView.css'

export default function Profile() {
    const role = localStorage.getItem('roles');
    const [employess, setEmployees] = useState([]);
    const [currentInfo, setCurrentInfo] = useState({
        name: '',
        username: '',
        id: '',
        email: '',
        password:''
    })
    const [openUpdateModal, setOpenUpdateModal] = useState(false)
    const [openDeleteModal, setOpenDeleteModal] = useState(false)
    const [showAddModal, setShowAddModal] = useState(false)
    const [traineeRole, setTraineeRole] = useState(false)
    const [trainerRole, setTrainerRole] = useState(false)
    const [adminRole, setAdminRole] = useState(false)
    const [showPositionModal, setShowPositionModal] = useState(false)
    const [reload, setReload] = useState(false)
    const [enteredAllFields, setEnteredAllFields] = useState(true)
    const [alreadyAdded, setAlreadyAdded] = useState(false)
    const [showProfile, setShowProfile] = useState(false)
    const [invalidSelection, setInvalidSelection] = useState(false)
    const roleToUserForm = {
        username: '',
        rolename: ''
    }

    const [username,setUsername] = useState()
    const reloadPage = () => {
        if (reload) {
            window.location.reload()
        }
    }
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
    const initCurrentInfo = () => {
        setCurrentInfo({
            name: '',
        username: '',
        id: '',
        email: '',
        password:''
        })
    }
    // const username = localStorage.getItem('username')
    useEffect(()=>{
        if(role.includes('Employee')) {
            getEmployeeData()
            .then((res)=>{
            //    console.log(res);
                setEmployees(res.data);
            }).catch((err) => {
                console.log(err);
            })
        } 
    }, [])

    const UpdateHandler = (employee) => {
        
        setCurrentInfo(employee)
        setOpenUpdateModal(true)
        // console.log("clicked",employee)
        
    }
    const deleteHandler = (employee) => {
        setCurrentInfo(employee)
        setOpenDeleteModal(true)
        
    }
    const openAddModal = () => {
        setShowAddModal(true)
    }

    const addEmployeeHandler = (e) => {
        e.preventDefault()
        addEmployee(currentInfo)
            .then((response) => {
                // console.log(response.data)
                initCurrentInfo()
                hideAddModal()
                setReload(true)
            })
            .catch((err) => {
                console.log(err)
                if (err.response.data.status === 400) {
                    setEnteredAllFields(false)
                    initCurrentInfo()
                }
                if (err.response.data.status === 409) {
                    setAlreadyAdded(true)
                    initCurrentInfo()
                }
                hideAddModal()
                
            })
        
        
    }
    reloadPage()
    const hideErrorModal = () => {
        setEnteredAllFields(true)
    }
    const hideShowProfile = () => {
        setAdminRole(false)
        setTraineeRole(false)
        setTrainerRole(false)
        setShowProfile(false)
    }
    const hideAlreadyAddModal = () => {
        setAlreadyAdded(false)
    }

    const viewProfileHandler = (employee) => {
        
        const roles = employee.roles
        roles.forEach(role => {
            if (role.name === "Trainer") {
                setTrainerRole(true)
            }
            if (role.name === "Trainee") {
                setTraineeRole(true)
            }
            if (role.name === "Admin") {
                setAdminRole(true)
            }
        })
        setCurrentInfo(employee)
        setShowProfile(true)
    }
    const positionModalHandler = (employee) => {
        setUsername(employee.username)
        setShowPositionModal(true)
        // console.log(employee)
        const roles = employee.roles
        //  console.log(roles)
        roles.forEach(role => {
            if (role.name === 'Trainee') {
                setTraineeRole(true)
            }
            if (role.name === 'Trainer') {
                setTrainerRole(true)
            }
            if (role.name === 'Admin') {
                setAdminRole(true)
            }
        });
        // console.log(traineeRole)
        // console.log(trainerRole)
        // console.log(adminRole)
    }

    const traineeRoleHandler = (e) => {
        setTraineeRole(e.target.checked)
    }

    const trainerRoleHandler = (e) => {
        setTrainerRole(e.target.checked)
    }
    const adminRoleHandler = (e) => {
        setAdminRole(e.target.checked)
    }

    const submitRole = (roleToUserForm) => {
        // console.log(roleToUserForm)
        addRoletoEmployee(roleToUserForm)
            .then((res) =>{
            console.log(res)
            })
            .catch((err) => {
            console.log(err)
            })     
    }

    const hideInvalidModal = () => {
        setInvalidSelection(false)
        hidePositionModal()
    }

    const submitRoleHandler = (e) => {
        let admin = adminRole
        let trainee = traineeRole
        let trainer = trainerRole
        e.preventDefault()
        
            if (trainee && (trainer|| admin)) {
                console.log("An employee cannot be both trainee and trainer at the same time.")
                showInvalidModal()
                
            }
           else if (trainee && !(trainer || admin)) {
                roleToUserForm.rolename = 'Trainee'
                roleToUserForm.username = username
                submitRole(roleToUserForm)
                trainee = false
                hidePositionModal()
            } 
            else {
                if (trainer && admin) {
                    roleToUserForm.rolename = 'Trainer'
                    roleToUserForm.username = username
                    submitRole(roleToUserForm)
                    trainer = false
                    roleToUserForm.username = username
                    roleToUserForm.rolename = 'Admin'
                    submitRole(roleToUserForm)
                    admin = false
                    hidePositionModal()
                }
                else {
                    if (trainer) {
                        roleToUserForm.rolename = 'Trainer'
                        roleToUserForm.username = username
                        submitRole(roleToUserForm)
                        trainer = false
                        hidePositionModal()
                        
                    }
                    if (admin) {
                        roleToUserForm.username = username
                        roleToUserForm.rolename = 'Admin'
                        submitRole(roleToUserForm)
                        admin = false
                        hidePositionModal()
                    }
                }
            }   
       
    }
    const showInvalidModal = () => {
        setInvalidSelection(true)
    }
    const hidePositionModal = () => {
        setAdminRole(false)
        setTrainerRole(false)
        setTraineeRole(false)
        setUsername('')
        setShowPositionModal(false)
        if (!invalidSelection) {
            reloadPage()
        }
        
    }
    
    const submitUpdateHandler = (e) => {
        e.preventDefault()
        updateEmployee(currentInfo)
            .then((response) => {
                // console.log(response)
                setCurrentInfo()
                hideUpdateModal()
                window.location.reload()
            })
            .catch((err) => {
                // console.log(err)
                // console.log(err)
                if (err.response.data.status === 400) {
                    setEnteredAllFields(false)
                }
            })
        // console.log(employess)
        
    }
    const submitDeleteHandler = (e) => {
        e.preventDefault()
        console.log(currentInfo)
        deleteEmployee(currentInfo)
            .then((response) => {
            // console.log(response)
            })
            .catch((err) => {
                console.log(err)
            })
        setCurrentInfo()
        hideDeleteModal()
        window.location.reload()
    }
    const hideAddModal =() => {
        setShowAddModal(false)
    }
    const hideUpdateModal = () => {
        initCurrentInfo()
        setOpenUpdateModal(false)
    }
    const hideDeleteModal = () => {
        initCurrentInfo()
        setOpenDeleteModal(false)
    }
    console.log(employess)
    const nameChangeHandler = (e) => {
        e.persist()
        setCurrentInfo({
            ...currentInfo,
            name: e.target.value
        })
    }
    const idChangeHandler = (e) => {
        e.persist()
        setCurrentInfo({
            ...currentInfo,
            id:e.target.value
        })
    }
    const emailChangeHandler = (e) => {
        e.persist()
        setCurrentInfo({
            ...currentInfo,
            email:e.target.value
        })
    }
    const usernameChangeHandler = (e) => {
        e.persist()
        setCurrentInfo({
            ...currentInfo,
            username:e.target.value
        })
    }
    const passwordChangeHandler = (e) => {
        e.persist()
        setCurrentInfo({
            ...currentInfo,
            password:e.target.value
        })
    }
    const handleLogOut = (e) => {
        e.persist();
        localStorage.setItem('token',null);
            localStorage.setItem('roles',null);
            localStorage.setItem('username',null);

            window.location.assign('/');
    }
    return ( 
        <>
    <nav className="navbar navbar-expand-lg navbar-light fixed-top">
    <div className="container">
      <Link className="navbar-brand" to={'/profile'}>
          BJIT Academy
                    </Link>
      <div className="collapse navbar-collapse" id="navbarTogglerDemo02">
                        <ul className="navbar-nav ml-auto">
                        <li className="nav-item">
            <Link className="nav-link" to={'/profileview'}>
              Your Profile
            </Link>
          </li>
          <li className="nav-item">
              <button className="btn btn-success" onClick={handleLogOut}>Log Out</button>
          </li>
         </ul>
        </div>
            </div>
            </nav>
        
        
            {(role.includes('Employee')) ? (
                <>
            <table className="tables">
                <thead>
                    <tr>
                        <th><b>Name</b></th>
                        <th><b>Employee ID</b></th>
                                <th><b>Email</b></th>
                                {((role.includes('Admin') || role.includes('Trainer'))) ? (<th>Actions</th>) : <></>}
                                {((role.includes('Admin') || role.includes('Trainer')) || role.includes('Trainee') )? (<th>Profiles</th>) : <></> }
                    </tr>
                </thead>
                <tbody>
                            {employess.map((employee) => {
                       
                    return (
                        <tr key={employee.username}>
                            <td>{employee.name}</td>
                            <td>{employee.id}</td>
                            <td>{employee.email}</td>
                            {(role.includes('Admin') || role.includes('Trainer')) ? (
                                <td>
                                    {(role.includes('Admin') || role.includes('Trainer')) ?
                                (<button className="btn btn-secondary" onClick={() => {
                                    UpdateHandler(employee)
                                        }}>Update</button>) : (<></>)}
                                    {role.includes('Admin') ? (
                                        <>
                                    <button className="btn btn-danger" onClick={() => {
                                        deleteHandler(employee)
                                        }}>Delete</button>
                                            <button className="btn btn-info" onClick={() => {
                                                positionModalHandler(employee)
                                            }
                                            }>Assign Position</button>
                                            
                                            </>
                                    ) : (<></>)}
                                
                                </td>) : <></>}
                            {(role.includes('Admin') || role.includes('Trainer') || role.includes('Trainee')) ?
                                (<td>
                                    <button className="btn btn-success" onClick={() => {
                                        viewProfileHandler(employee)
                                    }}>Show Profile</button>
                            </td>) : (<></>)}
                        </tr>
                    )
                    })}
                </tbody>
                    </table>
                    </>
        ) : (<></>)}
        <br/>
                {(role.includes('Admin'))?<button id="addButton" className="btn btn-dark btn-lg" onClick={openAddModal}>Add New Employee</button>:<></>}
            
            {(openUpdateModal) ?
                (<ReactModal isOpen={openUpdateModal} style={customStyles}>
                    <form onSubmit={submitUpdateHandler}>
                        <h3>Update This Employee</h3>
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
                        <div className="mb-3">
                            <label>ID</label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter ID"
                                name='id'
                                onChange={idChangeHandler}
                                value={currentInfo.id}
                            />
                        </div>
                        <div className="d-grid">
                            <button className="btn btn-primary">
                                Submit
                            </button>
                            <button onClick={hideUpdateModal} className='btn btn-secondary'>
                                Cancel
                            </button>
                        </div>
                    </form>
                </ReactModal>)
                : <></>}
            {
                (openDeleteModal) ? (
                    <ReactModal isOpen={openDeleteModal} style={Styles}>
                        <h3>Are you sure to delete this record? This action cannot be undone.</h3>
                        <div className="d-grid">
                            <button className="btn btn-danger" onClick={submitDeleteHandler}>
                                Delete
                            </button>
                            <button onClick={hideDeleteModal} className='btn btn-secondary'>
                                Cancel
                            </button>
                        </div>
                    </ReactModal>
                )
                    :
                    (<></>)
            }
            {
                (showAddModal) ? (
                    <ReactModal isOpen={showAddModal} style={customStyles}>
                    <form onSubmit={addEmployeeHandler}>
                        <h3>Add New Employee</h3>
                        <div className="mb-3">
                            <label>Name</label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Name"
                                name='name'
                                onChange={nameChangeHandler}
                                    value={currentInfo.name}
                                    required
                            />
                            </div>
                            <div className="mb-3">
                            <label>User Name</label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="username"
                                name='username'
                                onChange={usernameChangeHandler}
                                    value={currentInfo.username}
                                    required
                            />
                            </div>
                            <div className="mb-3">
                            <label>Password</label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter Password"
                                name='password'
                                onChange={passwordChangeHandler}
                                value={currentInfo.password}
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
                                    required
                            />
                        </div>
                        <div className="mb-3">
                            <label>ID</label>
                            <input
                                type="text"
                                className="form-control"
                                placeholder="Enter ID"
                                name='id'
                                onChange={idChangeHandler}
                                    value={currentInfo.id}
                                    required
                            />
                        </div>
                        <div className="d-grid">
                            <button className="btn btn-primary">
                                Submit
                            </button>
                            <button onClick={hideAddModal} className='btn btn-secondary'>
                                Cancel
                            </button>
                        </div>
                    </form>
                </ReactModal>
                    
                ) : (<></>)
            }
            {
                (showPositionModal) ?
                    (<ReactModal isOpen={showPositionModal} style={customStyles}>
                        <h3>Assing Position(s) for this Employee</h3>
                        <form onSubmit={(e) => {
                            submitRoleHandler(e)
                        }}>
                            <div className="mb-3">
                            <input type="checkbox" defaultChecked={traineeRole} id="Trainee" name="Trainee" value="Trainee" onClick={traineeRoleHandler}/>
                                <label>Trainee</label>
                            </div>
                            <div className="mb-3">
                            <input type="checkbox" defaultChecked={trainerRole} id="Trainer" name="Trainer" value="Trainer" onClick={trainerRoleHandler} />
                                <label>Trainer</label>
                            </div>
                            <div className="mb-3">
                            <input type="checkbox" defaultChecked={adminRole} id="Admin" name="Admin" value="Admin" onClick={adminRoleHandler} />
                                <label>Admin</label>
                            </div>
                            <div className="d-grid">
                                <button className="btn btn-primary">Submit</button>
                                <button className="btn btn-secondary" onClick={hidePositionModal}>Cancel</button>
                            </div>
                            
                        </form>
                    </ReactModal>)
                    :
                    (<></>)
            }

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

            {
                (alreadyAdded) ?
                    (<ReactModal isOpen={alreadyAdded} style={customStyles}>
                         <h3 className="loginh3">This employee already exists in the database. Cannot add it.</h3>
                            <button type="button" className="btn btn-info" id="loginButton"  onClick={hideAlreadyAddModal}>
                                OK
                            </button>

                    </ReactModal>)
                    :
                    (<></>)
            }
            {
                (showProfile) ?
                    (<ReactModal id="modalCard" isOpen={showProfile} style={customStyles}>
                         
                            <h1>Employee details for { currentInfo.name}</h1>      
                    <p className='profile-text'>Email : {currentInfo.email}</p>
                            <p className='profile-text'>ID : {currentInfo.id}</p>
                            {adminRole && trainerRole && (<p className='profile-text'>Designation : Admin and Trainer</p>)}
                            {!adminRole && trainerRole && (<p className='profile-text'>Designation : Trainer</p>)}
                            {traineeRole && (<p className="profile-text">Designation : Trainee</p>)}
                     <p className='buttons'>
                     <button onClick={hideShowProfile}>Close</button>
              </p>

                    </ReactModal>)
                    :
                    (<></>)
            }
            {
                (invalidSelection) ?
                    (<ReactModal isOpen={invalidSelection} style={customStyles}>
                         <h3 className="loginh3">A Trainee Cannot be upgraded to Admin or Trainer or a Trainer or Admin cannot be Trainee at the same time.</h3>
                            <button type="button" className="btn btn-info" id="loginButton"  onClick={hideInvalidModal}>
                                OK
                            </button>

                    </ReactModal>)
                    :
                    (<></>)
            }
        

        </>
    )
}
            

