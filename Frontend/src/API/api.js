import axios from "axios";

const getToken=()=>{
    return localStorage.getItem('token');
}

export const employeeLogin = (authRequest) => {
    return axios({
        'method':'POST',
        'url':'http://localhost:8080/home/login',
        'data':authRequest
    })
}
export const getEmployee = (username) =>{
    return axios({
        'method':'GET',
        'url': 'http://localhost:8080/home/employee/' + username,
        'headers':{
            'Authorization': 'Bearer ' + getToken(),
            
        }
    })
}
export const getEmployeeData = () =>{
    return axios({
        'method':'GET',
        'url':'http://localhost:8080/home/employees',
        'headers':{
            'Authorization':'Bearer '+ getToken()
        }
    })
}

export const updateCurrentEmployee = (username,employee) => {
    return axios({
        'method': 'PUT',
        'url': 'http://localhost:8080/home/employee/'+username+'/update',
        'data':employee,
        'headers': {
            'Authorization': 'Bearer ' + getToken(),
            'Content-Type': 'application/json'
        }
    })
}

export const addEmployee = (employee) => {
    return axios({
        'method': 'POST',
        'url': 'http://localhost:8080/home/employees/save',
        'data':employee,
        'headers': {
            'Authorization': 'Bearer ' + getToken(),
            'Content-Type': 'application/json'
        }
    })
}

export const updateEmployee = (employee) => {
    // console.log(employee)
    return axios({
        'method': 'PUT',
        'url': 'http://localhost:8080/home/employees/update',
        'data':employee,
        'headers': {
            'Authorization': 'Bearer ' + getToken(),
            'Content-Type': 'application/json'
        }
    })
}

export const deleteEmployee = (employee) => {
    console.log(employee)
    return axios({
        'method': 'DELETE',
        'url': 'http://localhost:8080/home/employees/delete',
        'data':employee,
        'headers': {
            'Authorization': 'Bearer ' + getToken(),
            'Content-Type': 'application/json'
        }
    })
}

export const addRoletoEmployee = (roleToUserForm) => {
    console.log(roleToUserForm)
    return axios({
        'method': 'PUT',
        'url': 'http://localhost:8080/home/role/addtoEmployee',
        'data':roleToUserForm,
        'headers': {
            'Authorization': 'Bearer ' + getToken(),
            'Content-Type': 'application/json'
        }
    })  

}

